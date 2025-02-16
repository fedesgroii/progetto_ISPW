package login_inserimento_dati;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.LoginView;
import startupconfig.StartupSettingsEntity;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class LoginViewBase {
    private static final Logger LOGGER = Logger.getLogger(LoginViewBase.class.getName());
    private static final String DEFAULT_CSS_PATH = "/style/style_login_insert_specialist_";
    private static final String COLOR_CSS_SUFFIX = "a_colori.css";
    private static final String BN_CSS_SUFFIX = "bn.css";
    private static final int ERROR_TIMEOUT_SECONDS = 5;
    private final LoginGraphicController grafCon = new LoginGraphicController(this);
    private Text errorText;
    private TextField emailField;
    private PasswordField passwordField;
    private Stage primaryStage;

    protected abstract String getTipo();

    protected abstract String getTitleText();

    protected abstract String getSubtitleText();

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
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

        loadStyleSheet(scene, config.isColorMode());

        primaryStage.setTitle(getTitleText());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    private void loadStyleSheet(Scene scene, boolean colorMode) {
        String stylePath = DEFAULT_CSS_PATH + (colorMode ? COLOR_CSS_SUFFIX : BN_CSS_SUFFIX);
        String styleSheet = Objects.requireNonNull(
                getClass().getResource(stylePath),
                "Resource non trovata: " + stylePath
        ).toExternalForm();
        scene.getStylesheets().add(styleSheet);
    }

    private Scene getScene(Stage primaryStage, Text title, Text subtitle) {
        Button loginButton = new Button("Accedi");
        loginButton.setId("specialistButton");
        loginButton.setOnAction(event -> grafCon.handleLoginAttempt(getTipo()));

        /*
        Button backButton = new Button("Scegli come accedere");
        backButton.setId("backButton");
        backButton.setOnAction(event -> handleBackAction(primaryStage));*/

        VBox vbox = new VBox(20, title, subtitle, errorText, emailField, passwordField, loginButton);
        vbox.setId("vbox");
        return new Scene(vbox);
    }

    public void showError() {
        errorText.setVisible(true);
        PauseTransition delay = new PauseTransition(Duration.seconds(ERROR_TIMEOUT_SECONDS));
        delay.setOnFinished(event -> errorText.setVisible(false));
        delay.play();
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    /**
     * Chiude la finestra corrente.
     */
    public void closeView() {
        if (primaryStage != null) {
            primaryStage.close();
            LOGGER.log(Level.INFO, "Finestra di login chiusa con successo.");
        } else {
            LOGGER.log(Level.WARNING, "Impossibile chiudere la finestra: primaryStage non inizializzata.");
        }
    }
}