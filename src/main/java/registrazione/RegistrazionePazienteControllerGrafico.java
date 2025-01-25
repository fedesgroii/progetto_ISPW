package registrazione;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegistrazionePazienteControllerGrafico {

    private final RegistrazionePazienteView view;

    public RegistrazionePazienteControllerGrafico(RegistrazionePazienteView view) {
        this.view = view;
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

    protected boolean isValidNumeroTelefonico() {
        String numeroTelefonico = view.numeroTelefonicoField.getText();
        if (numeroTelefonico == null || numeroTelefonico.length() < 10 || !numeroTelefonico.matches("[0-9]+")) {
            view.showErrorNumeroTelefonico();
            return false;
        }
        view.hideErrorNumeroTelefonico();
        return true;
    }

    protected boolean isValidEmail() {
        String email = view.emailField.getText();
        if (email == null || !email.contains("@")) {
            view.showErrorEmail();
            return false;
        }
        view.hideErrorEmail();
        return true;
    }

    protected boolean isValidCodiceFiscale() {
        String codiceFiscale = view.codiceFiscaleField.getText();
        if (codiceFiscale == null || codiceFiscale.length() != 16) {
            view.showErrorCodiceFiscale();
            return false;
        }
        view.hideErrorCodiceFiscale();
        return true;
    }

    protected boolean isValidPassword() {
        String password = view.passwordField.getText();
        if (password == null || password.length() < 8) {
            view.showErrorPassword();
            return false;
        }
        view.hideErrorPassword();
        return true;
    }
}
