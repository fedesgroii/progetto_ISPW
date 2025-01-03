package login_inserimento_dati;

import startupconfig.StartupSettingsEntity;
import java.util.Map;

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
        return simulateVerification(userType, username, password, true);
    }

    private boolean checkListPointCredentials(String userType, String username, String password) {
        return simulateVerification(userType, username, password, false);
    }

    private boolean simulateVerification(String userType, String username, String password, boolean useDatabase) {
        Map<String, Map<String, String>> dummyData = Map.of(
                "Specialist", Map.of(
                        "specialist1", "password123",
                        "specialist2", "password456"
                ),
                "Patient", Map.of(
                        "patient1", "password789",
                        "patient2", "password012"
                )
        );

        if (!dummyData.containsKey(userType)) {
            controlloreGrafico.showError(); // Tipo di utente non riconosciuto
            return false;
        }

        Map<String, String> userTable = dummyData.get(userType);

        if (useDatabase) {
            // Simula una verifica nel "database" basata sul tipo di utente
            if (userTable.containsKey(username) && userTable.get(username).equals(password)) {
                return true; // Credenziali corrette
            } else {
                controlloreGrafico.showError(); // Credenziali errate
                return false;
            }
        } else {
            // Simula una verifica in una lista alternativa
            if ("Specialist".equals(userType) && "defaultSpecialist".equals(username) && "defaultPass".equals(password)) {
                return true;
            }
            if ("Patient".equals(userType) && "defaultPatient".equals(username) && "defaultPass".equals(password)) {
                return true;
            }

            controlloreGrafico.showError(); // Credenziali errate per lista alternativa
            return false;
        }
    }
}
