package registrazione;

import storage_db.DatabaseConnection;
import storage_db.DatabaseStorageStrategyPaziente;  // Importa la strategia di salvataggio su database per i pazienti
import storage_file.FileManagerPazienti;  // Importa la classe per il salvataggio su file system
import storage_liste.ListaPazienti;
import startupconfig.StartupSettingsEntity;  // Importa la classe per gestire le configurazioni iniziali
import model.Paziente;  // Importa la classe Paziente
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegistrazionePazienteControllerApplicativo {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();  // Ottiene l'istanza della configurazione globale
    private final RegistrazionePazienteView view;  // Dichiarazione della vista per la registrazione del paziente
    private final DatabaseStorageStrategyPaziente pazienteDatabase = new DatabaseStorageStrategyPaziente();  // Crea un'istanza per il salvataggio su database
    private final RegistrazionePazienteControllerGrafico controlloreGrafico;

    public RegistrazionePazienteControllerApplicativo(RegistrazionePazienteView view) {
        this.view = view;
        this.controlloreGrafico = new RegistrazionePazienteControllerGrafico(view);
    }

    public boolean isValidInput() {
        boolean result = true; // Variabile per verificare se ci sono errori nei dati immessi

        // Esegue i controlli di validazione chiamando metodi helper
        if (!controlloreGrafico.isValidNome()) result = false;
        if (!controlloreGrafico.isValidCognome()) result = false;
        if (!controlloreGrafico.isValidDataDiNascita()) result = false;
        if (!controlloreGrafico.isValidNumeroTelefonico()) result = false;
        if (!controlloreGrafico.isValidEmail()) result = false;
        if (!controlloreGrafico.isValidCodiceFiscale()) result = false;
        if (!controlloreGrafico.isValidPassword()) result = false;

        // Se tutti i dati sono validi, crea e salva il paziente
        if (result) {
            Paziente paziente = createPaziente(); // Crea un nuovo paziente
            if (paziente == null) {
                return false; // Restituisce false se la creazione del paziente non è riuscita
            }

            int storageOption = config.getStorageOption(); // Ottiene l'opzione di salvataggio
            switch (storageOption) {
                case 1: // Salvataggio su Database
                    if (isDuplicateInDatabase(paziente)) {
                        controlloreGrafico.duplicatedDatas(); // Mostra errore sull'interfaccia per dati duplicati
                        return false;
                    }
                    view.hideGenericError();
                    return pazienteDatabase.salva(paziente);

                case 2: // Salvataggio su File System
                    FileManagerPazienti fileManager = new FileManagerPazienti(); // Gestione file system
                    if (FileManagerPazienti.trovaCodiceFiscaleNellaCartellaPazienti(paziente.getCodiceFiscalePaziente())) {
                        controlloreGrafico.duplicatedDatas(); // Mostra errore sull'interfaccia per dati duplicati
                        return false;
                    }
                    view.hideGenericError();
                    return fileManager.salva(paziente);

                default: // Salvataggio in Lista (RAM)
                    ListaPazienti pazienteLista = ListaPazienti.getIstanzaListaPazienti();
                    if (isDuplicateInList(paziente, pazienteLista)) {
                        controlloreGrafico.duplicatedDatas(); // Mostra errore sull'interfaccia per dati duplicati
                        return false;
                    }
                    view.hideGenericError();
                    pazienteLista.aggiungiPaziente(paziente);
                    return true;
            }
        }

        view.showGenericError("Errore nell'inserimento dei dati.");
        return false; // Restituisce false se c'è stato un errore
    }

    private Paziente createPaziente() {
        try {
            LocalDate dataDiNascita = LocalDate.parse(
                    view.dataDiNascitaField.getText(),
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
            return new Paziente.Builder()
                    .nome(view.nomeField.getText())
                    .cognome(view.cognomeField.getText())
                    .dataDiNascita(dataDiNascita) // Passa la data come LocalDate
                    .numeroTelefonico(view.numeroTelefonicoField.getText())
                    .email(view.emailField.getText())
                    .password(view.passwordField.getText())
                    .codiceFiscalePaziente(view.codiceFiscaleField.getText())
                    .condizioniMediche("DA SPECIFICARE")
                    .build();
        } catch (DateTimeParseException e) {
            view.showErrorDataDiNascita();
            return null;
        }
    }

    private boolean isDuplicateInDatabase(Paziente paziente) {
        String query = "SELECT 1 FROM pazienti WHERE email = ? OR numeroTelefonico = ? OR numeroTesseraSanitaria = ? LIMIT 1";
        try (var conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                view.showGenericError("Errore: Connessione al database nulla.");
                return false;
            }
            try (var stmt = conn.prepareStatement(query)) {
                stmt.setString(1, paziente.getEmail() != null ? paziente.getEmail() : "");
                stmt.setString(2, paziente.getNumeroTelefonico() != null ? paziente.getNumeroTelefonico() : "");
                stmt.setString(3, paziente.getCodiceFiscalePaziente() != null ? paziente.getCodiceFiscalePaziente() : "");
                try (var rs = stmt.executeQuery()) {
                    return rs.next(); // Se trova almeno un risultato, c'è un duplicato
                }
            }
        } catch (Exception e) {
            view.showGenericError("Errore di connessione al Database");
            e.printStackTrace(); // Stampa l'errore reale in console
        }
        return false;
    }

    private boolean isDuplicateInList(Paziente paziente, ListaPazienti pazienteLista) {
        // Controlla duplicati nella lista
        for (Paziente p : pazienteLista.getObservableListaPazienti()) {
            if (p.getEmail().equalsIgnoreCase(paziente.getEmail()) ||
                    p.getNumeroTelefonico().equals(paziente.getNumeroTelefonico()) ||
                    p.getCodiceFiscalePaziente().equalsIgnoreCase(paziente.getCodiceFiscalePaziente())) {
                return true; // Restituisce true se ci sono duplicati
            }
        }
        return false;
    }
}