package login_inserimento_dati;

public class LoginGraphicController {
    private final LoginViewBase view;

    public LoginGraphicController(LoginViewBase view) {
        this.view = view;
    }

    public void showError() {
        view.showError();
    }
}