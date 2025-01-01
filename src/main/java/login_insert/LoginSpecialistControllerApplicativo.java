package login_insert;

import login_insert.LoginSpecialistModel;

public class LoginSpecialistControllerApplicativo {
    private final LoginSpecialistControllerGrafico CG;

    public LoginSpecialistControllerApplicativo(LoginSpecialistControllerGrafico CG) {
        this.CG = CG;
    }

    // Metodo per validare le credenziali
    public boolean validateCredentials(String email, String password) {

        CG.showError();
          return false;
    }
}
