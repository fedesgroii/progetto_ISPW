package login_insert_DUMMY;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.LoginView;
import startupconfig.StartupSettingsEntity;

public class LoginSpecialistView {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();

    // Dichiarazione di errorText come variabile di istanza
    private Text errorText;

    public void start(Stage primaryStage) {
        // Inizializza il controller grafico con la view
        LoginSpecialistControllerGrafico CG = new LoginSpecialistControllerGrafico(this);

        // Inizializza il controller applicativo con il controller grafico
        LoginSpecialistControllerApplicativo controllerApplicativo = new LoginSpecialistControllerApplicativo(CG);

        // Titolo della pagina
        Text title = new Text("Login Specialista");
        title.setId("title");

        // Sottotitolo della pagina
        Text subtitle = new Text("Inserisci le tue credenziali per accedere.");
        subtitle.setId("subtitle");

        // Inizializza errorText come variabile di istanza
        errorText = new Text("Credenziali errate, riprova.");
        //errorText.setId("errorText");
        errorText.setVisible(false); // Impostato inizialmente come invisibile

        // Campo di testo per l'email
        TextField emailField = new TextField();
        emailField.setPromptText("Inserisci la tua email");
        emailField.setId("inputField");

        // Campo di testo per la password
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Inserisci la tua password");
        passwordField.setId("inputField");

        // Pulsante per il login
        Button loginButton = new Button("Accedi");
        loginButton.setId("specialistButton");

        // Pulsante "Torna indietro"
        Button backButton = new Button("Torna indietro");
        backButton.setId("backButton");

        // Layout principale
        VBox vbox = new VBox(20, title, subtitle,errorText, emailField, passwordField, loginButton, backButton);
        vbox.setId("vbox");

        // Configurazione della scena
        Scene scene = new Scene(vbox);
        if (config.isColorMode()) {
            String colorStyle = getClass().getResource("/style/style_login_insert_specialist_a_colori.css").toExternalForm();
            scene.getStylesheets().add(colorStyle);
        } else {
            String bnStyle = getClass().getResource("/style/style_login_insert_specialist_bn.css").toExternalForm();
            scene.getStylesheets().add(bnStyle);
        }

        // Imposta la finestra principale
        primaryStage.setTitle("Login Specialista");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Event handler per il login
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            boolean isValid = controllerApplicativo.validateCredentials(email, password);

        });

        // Event handler per il pulsante "Torna indietro"
        backButton.setOnAction(event -> {
            // Avvia la finestra di login
            LoginView loginView = new LoginView();
            Stage loginStage = new Stage();
            loginView.start(loginStage); // Avvia correttamente il metodo start chiamando la pagina di login
            primaryStage.close(); // Ora chiudi la finestra principale solo dopo aver avviato la finestra di login
        });

    }

    public void showError() {
        errorText.setVisible(true); // Ora `errorText` è accessibile
    }

}
