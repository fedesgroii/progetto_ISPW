// Graphic Controller
package login_insert2;

import javafx.stage.Stage;

public class LoginGraphicController {
    private final LoginViewBase view;

    public LoginGraphicController(LoginViewBase view) {
        this.view = view;
    }

    protected void showError() {
        view.showError();
    }

    }

