// Login View for Specialist
package login_inserimento_dati;

public class LoginViewSpecialist extends LoginViewBase {
    @Override
    protected String getTitleText() {
        return "Login Specialista";
    }

    @Override
    protected String getSubtitleText() {
        return "Inserisci le tue credenziali da specialista per accedere.";
    }

    @Override
    protected String getTipo() {
        return "Specialist";
    }
}
