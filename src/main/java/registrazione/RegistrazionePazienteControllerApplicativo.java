package registrazione;

import storage_db.DatabaseConnection;
import storage_db.DatabaseStorageStrategyPaziente;  // Importa la strategia di salvataggio su database per i pazienti
import storage_liste.ListaPazienti;
import startupconfig.StartupSettingsEntity;  // Importa la classe per gestire le configurazioni iniziali
import model.Paziente;  // Importa la classe Paziente

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegistrazionePazienteControllerApplicativo {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();  // Ottiene l'istanza della configurazione globale
    private RegistrazionePazienteView view;  // Dichiarazione della vista per la registrazione del paziente
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

            if (config.isSaveToDatabase()) {
                if (isDuplicateInDatabase(paziente)) {
                    view.showGenericError("Questi dati risultano già registrati nel DataBase"); // Mostra un messaggio di errore per duplicati
                    return false;
                }
                view.hideGenericError();
                return pazienteDatabase.salva(paziente);
            } else {
                ListaPazienti pazienteLista = ListaPazienti.getIstanzaListaPazienti();
                if (isDuplicateInList(paziente, pazienteLista)) {
                    view.showGenericError("Questi dati risultano già registrati nella lista utenti"); // Mostra un messaggio di errore per duplicati
                    return false;
                }
                view.hideGenericError();
                pazienteLista.aggiungiPaziente(paziente);
                return true;
            }
        }
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
        // Controlla duplicati in base a email, numero di telefono o codice fiscale
        String query = "SELECT COUNT(*) FROM paziente WHERE email = ? OR numero_telefonico = ? OR codice_fiscale = ?";
        try (var conn = DatabaseConnection.getConnection();
             var stmt = conn.prepareStatement(query)) {
            stmt.setString(1, paziente.getEmail());
            stmt.setString(2, paziente.getNumeroTelefonico());
            stmt.setString(3, paziente.getCodiceFiscalePaziente());

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Restituisce true se ci sono duplicati
                }
            }
        } catch (Exception e) {
            view.showGenericError("Errore di connessione al DataBase"); // Mostra un messaggio di errore per duplicati
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
