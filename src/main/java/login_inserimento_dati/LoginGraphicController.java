// Graphic Controller
package login_inserimento_dati;

public class LoginGraphicController {
    private final LoginViewBase view;

    public LoginGraphicController(LoginViewBase view) {
        this.view = view;
    }

    protected void showError() {
        view.showError();
    }
    }

