package login_insert;


public class LoginSpecialistControllerApplicativo {
    private final LoginSpecialistControllerGrafico cg;

    public LoginSpecialistControllerApplicativo(LoginSpecialistControllerGrafico CG) {
        this.cg = CG;
    }

    // Metodo per validare le credenziali
    public boolean validateCredentials(String email, String password) {

        cg.showError();
          return false;
    }
}
