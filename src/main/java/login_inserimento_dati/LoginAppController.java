package login_inserimento_dati;
import startupconfig.StartupSettingsEntity;

public class LoginAppController {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();
    private final LoginGraphicController controlloreGrafico;

    public LoginAppController(LoginViewBase view) {
        this.controlloreGrafico = new LoginGraphicController(view);
    }

    public boolean checkCredentials(String userType, String username, String password) {
        if (config.isSaveToDatabase()) {
            return checkDatabaseCredentials(userType, username, password);
        } else {
            return checkListPointCredentials(userType, username, password);
        }
    }

    private boolean checkDatabaseCredentials(String userType, String username, String password) {
        int a = 0;
        controlloreGrafico.showError();
        if (a == 0) {
            return false; // Dummy
        }else{
            return true;
        }

    }

    private boolean checkListPointCredentials(String userType, String username, String password) {
        controlloreGrafico.showError();
        int a = 0;
        controlloreGrafico.showError();
        if (a == 0) {
            return false; // Dummy
        }else{
            return true;
        }
    }
}
