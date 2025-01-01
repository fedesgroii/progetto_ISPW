package login_insert;


public class LoginSpecialistControllerGrafico {
    private final LoginSpecialistView view;

    public LoginSpecialistControllerGrafico(LoginSpecialistView view) {
        this.view = view;
    }

    public void showError() {
        view.showError();
    }
}
