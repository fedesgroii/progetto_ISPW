package login_inserimento_dati;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.LoginView;
import startupconfig.StartupSettingsEntity;

import java.util.Objects;

public abstract class LoginViewBase {
    private Text errorText;
    private TextField emailField;
    private PasswordField passwordField;
    private final LoginAppController controllerApplicativo = new LoginAppController(this);

    protected abstract String getTipo();
    protected abstract String getTitleText();
    protected abstract String getSubtitleText();

    public void start(Stage primaryStage) {
        StartupSettingsEntity config = StartupSettingsEntity.getInstance();

        Text title = new Text(getTitleText());
        title.setId("title");

        Text subtitle = new Text(getSubtitleText());
        subtitle.setId("subtitle");

        errorText = new Text("Credenziali errate, riprova.");
        errorText.setVisible(false);

        emailField = new TextField();
        emailField.setPromptText("Inserisci la tua email");
        emailField.setId("inputField");

        passwordField = new PasswordField();
        passwordField.setPromptText("Inserisci la tua password");
        passwordField.setId("inputField");

        Scene scene = getScene(primaryStage, title, subtitle);

        if (config.isColorMode()) {
            String colorStyle = Objects.requireNonNull(getClass().getResource("/style/style_login_insert_specialist_a_colori.css")).toExternalForm();
            scene.getStylesheets().add(colorStyle);
        } else {
            String bnStyle = Objects.requireNonNull(getClass().getResource("/style/style_login_insert_specialist_bn.css")).toExternalForm();
            scene.getStylesheets().add(bnStyle);
        }

        primaryStage.setTitle(getTitleText());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    private Scene getScene(Stage primaryStage, Text title, Text subtitle) {
        Button loginButton = new Button("Accedi");
        loginButton.setId("specialistButton");
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showError(); // Mostra errore se i campi sono vuoti
            } else {
                boolean esito = controllerApplicativo.checkCredentials(getTipo(), email, password);
                if (!esito) {
                    showError(); // Mostra errore se le credenziali non sono valide
                } else {
                 System.out.println("LOGGATO");
                }
            }
        });

        Button backButton = new Button("Scegli come accedere");
        backButton.setId("backButton");
        backButton.setOnAction( event -> {
            // Avvia la finestra di login
            LoginView loginView = new LoginView();
            Stage loginStage = new Stage();
            loginView.start(loginStage); // Avvia correttamente il metodo start chiamando la pagina di login
            primaryStage.close(); // Ora chiudi la finestra principale solo dopo aver avviato la finestra di login

        });

        VBox vbox = new VBox(20, title, subtitle, errorText, emailField, passwordField, loginButton, backButton);
        vbox.setId("vbox");
        Scene scene = new Scene(vbox);
        return scene;
    }

    public void showError() {
        errorText.setVisible(true);
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getEmailField() {
        return emailField;
    }
}
