package login_insert2;

import javafx.stage.Stage;
import startupconfig.StartupSettingsEntity;

public class LoginAppController {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();
    private final LoginViewBase view;
    private final LoginGraphicController Cgr;

    public LoginAppController(LoginViewBase view) {
        this.view = view;
        this.Cgr = new LoginGraphicController(view);
    }

    public boolean checkCredentials(String userType, String username, String password) {
        if (config.isSaveToDatabase()) {
            return checkDatabaseCredentials(userType, username, password);
        } else {
            return checkListPointCredentials(userType, username, password);
        }
    }

    private boolean checkDatabaseCredentials(String userType, String username, String password) {
        Cgr.showError();
        return false; // Dummy
    }

    private boolean checkListPointCredentials(String userType, String username, String password) {
        Cgr.showError();
        return false; // Dummy
    }
}
