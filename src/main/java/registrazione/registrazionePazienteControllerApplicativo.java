package registrazione;

import utenti.Paziente;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class registrazionePazienteControllerApplicativo {
    private final registrazionePazienteView view;

    public registrazionePazienteControllerApplicativo(registrazionePazienteView registrazionePazienteView) {
        this.view = registrazionePazienteView;
    }

    private boolean isValidInput(Paziente paziente) {
        // Verifica che il nome non sia vuoto
        if(view.nomeField.getText() == null || view.nomeField.getText().isEmpty()) {
            view.showErrorNome();
            return false;
        }

        // Verifica che il cognome non sia vuoto
        if(view.cognomeField.getText() == null || view.cognomeField.getText().isEmpty()) {
            view.showErrorCognome();
            return false;
        }

        // Verifica che la data di nascita sia valida
        String dataDiNascita = view.dataDiNascitaField.getText();
        try {
            // Controlla se la data è nel formato corretto (DD/MM/YYYY)
            LocalDate data = LocalDate.parse(dataDiNascita, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Verifica che l'anno sia tra 1940 e l'anno corrente

            // Ottieni l'anno corrente
            int annoCorrente = LocalDate.now().getYear();

            if (data.getYear() < 1940 || data.getYear() > annoCorrente) {
                view.showErrorDataDiNascita();  // Mostra errore se l'anno non è valido
                return false;
            }
        } catch (DateTimeParseException e) {
            // Se la data è invalida (come 31 febbraio), mostra l'errore
            view.showErrorDataDiNascita();
            return false;
        }

        // Verifica che il numero telefonico sia valido (formato italiano)
        String numeroTelefonico = view.numeroTelefonicoField.getText();
        String regexTelefono = "/^((3[0-9][0-9])\\d{7})$/"; // Regex per numeri telefonici italiani

        if (numeroTelefonico == null || numeroTelefonico.isEmpty() || !numeroTelefonico.matches(regexTelefono)) {
            view.showErrorNumeroTelefonico();  // Mostra errore se il numero non è valido
            return false;
        }

        // Verifica che l'email sia valida (formato corretto)
        String email = view.emailField.getText();
        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Regex per email

        if (email == null || email.isEmpty() || !email.matches(regexEmail)) {
            view.showErrorEmail();  // Mostra errore se l'email non è valida
            return false;
        }
        return true; //se ha passato tutti i controlli, gli input sono validi
    }




}
