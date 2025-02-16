package registrazione;
import model.Paziente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.logging.Logger;

public class RegistrazionePazienteControllerGrafico {
    private final RegistrazionePazienteView view;
    private final RegistrazionePazienteControllerApplicativo appController = new RegistrazionePazienteControllerApplicativo();
    public RegistrazionePazienteControllerGrafico(RegistrazionePazienteView view) {
        this.view = view;
    }

    public boolean isValidInput() {
        // Verifica la validità dei campi di ingresso chiamando un metodo dedicato
        if (!validateFields()) {
            view.showGenericError("Errore nell'inserimento dei dati.");
            return false; // Restituisce false se ci sono errori nei campi
        }

        try {
            // Creazione di un oggetto RegistrazionePazienteBean con i dati inseriti dall'utente
            RegistrazionePazienteBean registrazionePazienteBean = new RegistrazionePazienteBean(
                    view.nomeField.getText(), // Nome
                    view.cognomeField.getText(), // Cognome
                    LocalDate.parse(view.dataDiNascitaField.getText(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")), // Data di nascita già validata
                    view.numeroTelefonicoField.getText(), // Numero telefonico
                    view.emailField.getText(), // Email
                    view.codiceFiscaleField.getText(), // Codice fiscale
                    view.passwordField.getText() // Password
            );

            // Passa l'oggetto RegistrazionePazienteBean a un metodo per creare il Paziente
            Paziente paziente = appController.createPaziente(registrazionePazienteBean);

            // Verifica se il paziente è stato creato correttamente
            if (paziente == null) {
                view.showGenericError("Errore nella creazione del paziente.");
                return false;
            }

            return appController.savePaziente(paziente);

        } catch (Exception e) {
            // Gestione di eventuali errori imprevisti
            view.showGenericError("Errore imprevisto durante la registrazione.");
            return false;
        }
    }

    // Metodo dedicato per validare i campi di input
    private boolean validateFields() {
        // Variabile per accumulare il risultato delle verifiche
        boolean isValid = isValidNome();

        // Esegue i controlli di validazione chiamando metodi helper
        if (!isValidCognome()) isValid = false;
        if (!isValidDataDiNascita()) isValid = false;
        if (!isValidNumeroTelefonico()) isValid = false;
        if (!isValidEmail()) isValid = false;
        if (!isValidCodiceFiscale()) isValid = false;
        if (!isValidPassword()) isValid = false;

        return isValid; // Restituisce il risultato finale
    }
    protected boolean isValidNome() {
        if (view.nomeField.getText() == null || view.nomeField.getText().isEmpty()) {
            view.showErrorNome();
            return false;
        }
        view.hideErrorNome();
        return true;
    }
    protected boolean isValidCognome() {
        if (view.cognomeField.getText() == null || view.cognomeField.getText().isEmpty()) {
            view.showErrorCognome();
            return false;
        }
        view.hideErrorCognome();
        return true;
    }
    protected boolean isValidDataDiNascita() {
        // Logger per il logging
        Logger logger = Logger.getLogger(this.getClass().getName());
        // Ottieni la data di nascita dall'input dell'utente e rimuovi spazi vuoti
        String dataDiNascita = view.dataDiNascitaField.getText().trim();
        // Controlla se l'input è vuoto o null
        if (dataDiNascita.isEmpty()) {
            logger.warning("Errore: La data di nascita non può essere vuota.");
            view.showErrorDataDiNascita();
            return false;
        }
        // Definisci il formato della data atteso (es. dd/MM/yyyy) con ResolverStyle.SMART e Locale.ITALIAN
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN)
                .withResolverStyle(ResolverStyle.SMART);
        try {
            // Prova a convertire la stringa in una data
            LocalDate data = LocalDate.parse(dataDiNascita, formatter);
            LocalDate oggi = LocalDate.now();
            LocalDate dataMinima = LocalDate.of(1940, 1, 1);

            // Controlla se la data è fuori dai limiti accettabili
            if (data.isBefore(dataMinima)) {
                logger.warning("Errore: La data di nascita è precedente al 01/01/1940.");
                view.showErrorDataDiNascita();
                return false;
            }
            if (data.isAfter(oggi)) {
                logger.warning("Errore: La data di nascita è nel futuro.");
                view.showErrorDataDiNascita();
                return false;
            }
            // Se tutti i controlli passano, nascondi eventuali messaggi di errore
            logger.info("Data di nascita valida.");
            view.hideErrorDataDiNascita();
            return true;
        } catch (DateTimeParseException e) {
            // Gestisci il caso in cui la data non è nel formato corretto
            logger.severe("Errore: Formato della data non valido. Dettagli: " + e.getMessage());
            view.showErrorDataDiNascita();
            return false;
        }
    }

    protected boolean isValidNumeroTelefonico() {
        String numeroTelefonico = view.numeroTelefonicoField.getText();
        if (numeroTelefonico == null || numeroTelefonico.length() < 10 || !numeroTelefonico.matches("^(32\\d|33\\d|34\\d|366|378|38\\d|39[0-3])\\d{7}$")) {
            view.showErrorNumeroTelefonico();
            return false;
        }
        view.hideErrorNumeroTelefonico();
        return true;
    }
    protected boolean isValidEmail() {
        String email = view.emailField.getText();
        if (email == null || !(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) {
            view.showErrorEmail();
            return false;
        }
        view.hideErrorEmail();
        return true;
    }
    protected boolean isValidCodiceFiscale() {
        // Ottieni il valore del campo di input
        String codiceFiscale = view.codiceFiscaleField.getText();
        // Verifica se il campo è vuoto o nullo
        if (codiceFiscale == null || codiceFiscale.trim().isEmpty()) {
            view.showErrorCodiceFiscale();
            return false;
        }
        // Converte il codice fiscale in maiuscolo per uniformità
        codiceFiscale = codiceFiscale.toUpperCase();
        // Verifica il formato del codice fiscale (16 caratteri alfanumerici)
        if (!codiceFiscale.matches("^[A-Z0-9]{16}$")) {
            view.showErrorCodiceFiscale();
            return false;
        }
        // Se tutto è valido, nascondi eventuali messaggi di errore
        view.hideErrorCodiceFiscale();
        return true;
    }
    protected boolean isValidPassword() {
        // Regex: Verifica che la password abbia almeno 8 caratteri con almeno una lettera e un numero
        String password = view.passwordField.getText();
        if (password == null || !(password.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$") ) ) {
            view.showErrorPassword();
            return false;
        }
        view.hideErrorPassword();
        return true;
    }
    protected void duplicatedDatasErrorMessage(){
        view.showGenericError("Alcuni di questi dati risultano già registrati"); // Mostra un messaggio di errore per duplicati
    }
}