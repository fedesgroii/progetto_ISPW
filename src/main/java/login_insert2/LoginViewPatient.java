// Login View for Patient
package login_insert2;

public class LoginViewPatient extends LoginViewBase {
    @Override
    protected String getTitleText() {
        return "Login Paziente";
    }

    @Override
    protected String getSubtitleText() {
        return "Inserisci le tue credenziali per accedere.";
    }

    @Override
    protected String getTipo() {
        return "Patient";
    }
}

