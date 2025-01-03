package login_inserimento_dati;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import startupconfig.StartupSettingsEntity;

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

        Button loginButton = new Button("Accedi");
        loginButton.setId("specialistButton");
        loginButton.setOnAction(event -> {
            if (controllerApplicativo != null) {
                controllerApplicativo.checkCredentials(getTipo(), emailField.getText(), passwordField.getText());
            }
        });

        Button backButton = new Button("Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(event -> primaryStage.close());

        VBox vbox = new VBox(20, title, subtitle, errorText, emailField, passwordField, loginButton, backButton);
        vbox.setId("vbox");
        Scene scene = new Scene(vbox);

        if (config.isColorMode()) {
            String colorStyle = getClass().getResource("/style/style_login_insert_specialist_a_colori.css").toExternalForm();
            scene.getStylesheets().add(colorStyle);
        } else {
            String bnStyle = getClass().getResource("/style/style_login_insert_specialist_bn.css").toExternalForm();
            scene.getStylesheets().add(bnStyle);
        }

        primaryStage.setTitle(getTitleText());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public void showError() {
        errorText.setVisible(true);
    }
}
