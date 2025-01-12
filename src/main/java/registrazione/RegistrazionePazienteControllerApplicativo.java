package registrazione;

import storage_db.DatabaseStorageStrategyPaziente;  // Importa la strategia di salvataggio su database per i pazienti
import storage_liste.ListaPazienti;
import startupconfig.StartupSettingsEntity;  // Importa la classe per gestire le configurazioni iniziali
import model.Paziente;  // Importa la classe Paziente
import model.PazienteInfo;  // Importa la classe PazienteInfo
import java.time.LocalDate;  // Importa la classe LocalDate per la gestione delle date
import java.time.format.DateTimeParseException;  // Importa la classe per gestire eccezioni nel parsing delle date

public class RegistrazionePazienteControllerApplicativo {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();  // Ottiene l'istanza della configurazione globale
    private final RegistrazionePazienteView view;  // Dichiarazione della vista per la registrazione del paziente
    private final DatabaseStorageStrategyPaziente pazienteDatabase = new DatabaseStorageStrategyPaziente();  // Crea un'istanza per il salvataggio su database

    public RegistrazionePazienteControllerApplicativo(RegistrazionePazienteView registrazionePazienteView) {
        this.view = registrazionePazienteView;  // Inizializza la vista per la registrazione
    }

    public boolean isValidInput() {
        boolean result = true; // Variabile per verificare se ci sono errori nei dati immessi

        // Esegue i controlli di validazione chiamando metodi helper
        if (!isValidNome()) result = false;
        if (!isValidCognome()) result = false;
        if (!isValidDataDiNascita()) result = false;
        if (!isValidNumeroTelefonico()) result = false;
        if (!isValidEmail()) result = false;
        if (!isValidCodiceFiscale()) result = false;
        if (!isValidPassword()) result = false;

        // Se tutti i dati sono validi, crea e salva il paziente
        if (result) {
            Paziente paziente = createPaziente(); // Crea un nuovo paziente
            if (config.isSaveToDatabase()) {
                return pazienteDatabase.salva(paziente);
            } else {
                ListaPazienti pazienteLista = ListaPazienti.getIstanzaListaPazienti();
                pazienteLista.aggiungiPaziente(paziente);
                return true;
            }
        }
        return false; // Restituisce false se c'è stato un errore
    }

    private boolean isValidNome() {
        if (view.nomeField.getText() == null || view.nomeField.getText().isEmpty()) {
            view.showErrorNome();
            return false;
        }
        view.hideErrorNome();
        return true;
    }

    private boolean isValidCognome() {
        if (view.cognomeField.getText() == null || view.cognomeField.getText().isEmpty()) {
            view.showErrorCognome();
            return false;
        }
        view.hideErrorCognome();
        return true;
    }

    private boolean isValidDataDiNascita() {
        String dataDiNascita = view.dataDiNascitaField.getText();
        try {
            LocalDate data = LocalDate.parse(dataDiNascita, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int annoCorrente = LocalDate.now().getYear();
            if (data.getYear() < 1940 || data.getYear() > annoCorrente) {
                view.showErrorDataDiNascita();
                return false;
            }
            view.hideErrorDataDiNascita();
            return true;
        } catch (DateTimeParseException e) {
            view.showErrorDataDiNascita();
            return false;
        }
    }

    private boolean isValidNumeroTelefonico() {
        String numeroTelefonico = view.numeroTelefonicoField.getText();
        String regexTelefono = "^(3[1-9]\\d{2}\\d{6})$";
        if (numeroTelefonico == null || numeroTelefonico.isEmpty() || !numeroTelefonico.matches(regexTelefono)) {
            view.showErrorNumeroTelefonico();
            return false;
        }
        view.hideErrorNumeroTelefonico();
        return true;
    }

    private boolean isValidEmail() {
        String email = view.emailField.getText();
        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty() || !email.matches(regexEmail)) {
            view.showErrorEmail();
            return false;
        }
        view.hideErrorEmail();
        return true;
    }

    private boolean isValidCodiceFiscale() {
        String codiceFiscale = view.codiceFiscaleField.getText();
        String regexCodiceFiscale = "^[A-Z0-9]{16}$";
        if (codiceFiscale == null || codiceFiscale.isEmpty() || !codiceFiscale.matches(regexCodiceFiscale)) {
            view.showErrorCodiceFiscale();
            return false;
        }
        view.hideErrorCodiceFiscale();
        return true;
    }

    private boolean isValidPassword() {
        String password = view.passwordField.getText();
        if (password == null || password.isEmpty() || password.length() < 8) {
            view.showErrorPassword();
            return false;
        }
        view.hideErrorPassword();
        return true;
    }

    private Paziente createPaziente() {
        PazienteInfo pazienteInfo = new PazienteInfo(
                view.nomeField.getText(),
                view.cognomeField.getText(),
                LocalDate.parse(view.dataDiNascitaField.getText(), java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                view.numeroTelefonicoField.getText(),
                view.emailField.getText(),
                view.passwordField.getText(),
                view.codiceFiscaleField.getText(),
                "DA SPECIFICARE"
        );
        return new Paziente(pazienteInfo);
    }
}
