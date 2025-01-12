package registrazione;

import Storage_DB.DatabaseStorageStrategyPaziente;  // Importa la strategia di salvataggio su database per i pazienti
import Storage_Liste.ListaPazienti;
import startupconfig.StartupSettingsEntity;  // Importa la classe per gestire le configurazioni iniziali
import utenti.Paziente;  // Importa la classe Paziente
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
        boolean result = true;  // Variabile per verificare se ci sono errori nei dati immessi

        // Verifica che il nome non sia vuoto
        if (view.nomeField.getText() == null || view.nomeField.getText().isEmpty()) {  // Controlla se il nome è vuoto
            view.showErrorNome();  // Mostra l'errore se il nome è vuoto
            result = false;  // Imposta il risultato a false se c'è un errore
        } else {
            view.hideErrorNome();  // Nasconde l'errore se il nome è valido
        }

        // Verifica che il cognome non sia vuoto
        if (view.cognomeField.getText() == null || view.cognomeField.getText().isEmpty()) {  // Controlla se il cognome è vuoto
            view.showErrorCognome();  // Mostra l'errore se il cognome è vuoto
            result = false;  // Imposta il risultato a false se c'è un errore
        } else {
            view.hideErrorCognome();  // Nasconde l'errore se il cognome è valido
        }

        // Verifica che la data di nascita sia valida
        String dataDiNascita = view.dataDiNascitaField.getText();  // Ottiene il valore della data di nascita
        try {
            // Controlla se la data è nel formato corretto (DD/MM/YYYY)
            LocalDate data = LocalDate.parse(dataDiNascita, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Verifica che l'anno sia tra 1940 e l'anno corrente
            int annoCorrente = LocalDate.now().getYear();  // Ottiene l'anno corrente

            if (data.getYear() < 1940 || data.getYear() > annoCorrente) {  // Verifica se l'anno di nascita è valido
                view.showErrorDataDiNascita();  // Mostra l'errore se l'anno non è valido
                result = false;  // Imposta il risultato a false
            } else {
                view.hideErrorDataDiNascita();  // Nasconde l'errore se la data è valida
            }
        } catch (DateTimeParseException e) {  // Gestisce l'eccezione nel caso la data sia nel formato sbagliato
            view.showErrorDataDiNascita();  // Mostra l'errore se la data non è valida
            result = false;  // Imposta il risultato a false
        }

        // Verifica che il numero telefonico sia valido (formato italiano)
        String numeroTelefonico = view.numeroTelefonicoField.getText();  // Ottiene il valore del numero telefonico
        String regexTelefono = "^(3[1-9]\\d{2}\\d{6})$";  // Definisce una regex per validare i numeri telefonici italiani

        if (numeroTelefonico == null || numeroTelefonico.isEmpty() || !numeroTelefonico.matches(regexTelefono)) {  // Controlla se il numero è vuoto o non valido
            view.showErrorNumeroTelefonico();  // Mostra l'errore se il numero non è valido
            result = false;  // Imposta il risultato a false
        } else {
            view.hideErrorNumeroTelefonico();  // Nasconde l'errore se il numero è valido
        }

        // Verifica che l'email sia valida (formato corretto)
        String email = view.emailField.getText();  // Ottiene il valore dell'email
        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";  // Definisce una regex per validare l'email

        if (email == null || email.isEmpty() || !email.matches(regexEmail)) {  // Controlla se l'email è vuota o non valida
            view.showErrorEmail();  // Mostra l'errore se l'email non è valida
            result = false;  // Imposta il risultato a false
        } else {
            view.hideErrorEmail();  // Nasconde l'errore se l'email è valida
        }

        // Verifica che il codice fiscale sia valido
        String codiceFiscale = view.codiceFiscaleField.getText();
        String regexCodiceFiscale = "^[A-Z0-9]{16}$"; // Regola per un codice fiscale italiano valido
        if (codiceFiscale == null || codiceFiscale.isEmpty() || !codiceFiscale.matches(regexCodiceFiscale)) {
            view.showErrorCodiceFiscale();
            result = false;
        } else {
            view.hideErrorCodiceFiscale();
        }

        // Verifica che la password sia valida
        String password = view.passwordField.getText();
        if (password == null || password.isEmpty() || password.length() < 8) { // Regola: almeno 8 caratteri
            view.showErrorPassword();
            result = false;
        } else {
            view.hideErrorPassword();
        }

        // se ha passato tutti i controlli, posso salvare.
        if (result == true) {  // Se tutti i dati sono validi
            // Crea un nuovo paziente con i dati validi
            Paziente paziente = new Paziente(
                    view.nomeField.getText(),
                    view.cognomeField.getText(),
                    LocalDate.parse(view.dataDiNascitaField.getText(), java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    view.numeroTelefonicoField.getText(),
                    view.emailField.getText(),
                    view.passwordField.getText(),
                    view.codiceFiscaleField.getText(),
                    "DA SPECIFICARE"  // Campo opzionale, da adattare come necessario
            );
            // Decidi la strategia di salvataggio in base alla configurazione
            if (config.isSaveToDatabase()) {  // Controlla se la configurazione prevede di salvare nel database
                return pazienteDatabase.salva(paziente);  // Salva il paziente nel database
            } else {
                ListaPazienti pazienteLista = ListaPazienti.getIstanzaListaPazienti();
                pazienteLista.aggiungiPaziente(paziente);  // Salva il paziente nella lista in memoria
                return true;
            }
        }
        return false;  // Restituisce false se c'è stato un errore
    }


}
