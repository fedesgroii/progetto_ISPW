package registrazione;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login_inserimento_dati.LoginViewPatient;
import startupconfig.StartupSettingsEntity;

public class RegistrazionePazienteView {
    // Campi nascosti per mostrare eventuali errori nell'inserimento dati
    private Text errorTextNome;
    private Text errorTextCognome;
    private Text errorTextDataDiNascita;
    private Text errorTextNumeroTelefonico;
    private Text errorTextEmail;
    private Text errorTextCodiceFiscale;
    private Text errorTextPassword;

    protected TextField nomeField;
    protected TextField cognomeField;
    protected TextField dataDiNascitaField;
    protected TextField numeroTelefonicoField;
    protected TextField emailField;
    protected TextField codiceFiscaleField;
    protected TextField passwordField;

    private final RegistrazionePazienteControllerApplicativo controllerApplicativo = new RegistrazionePazienteControllerApplicativo(this);

    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();
    private static final String INPUT_FIELD = "inputField";
    private static final String ERRORE = "errorText";

    public void start(Stage primaryStage) {

        Text title = new Text("Registrazione");
        title.setId("title");

        Text subtitle = new Text("Inserisci i tuoi dati");
        subtitle.setId("subtitle");

        errorTextNome = new Text("Errore nell'inserimento del nome, correggi");
        errorTextNome.setId(ERRORE);
        errorTextNome.setVisible(false);

        nomeField = new TextField();
        nomeField.setPromptText("Inserisci il tuo nome");
        nomeField.setId(INPUT_FIELD);

        errorTextCognome = new Text("Errore nell'inserimento del cognome, correggi");
        errorTextCognome.setId(ERRORE);
        errorTextCognome.setVisible(false);

        cognomeField = new TextField();
        cognomeField.setPromptText("Inserisci il tuo cognome");
        cognomeField.setId(INPUT_FIELD);

        errorTextDataDiNascita = new Text("Errore nell'inserimento della data, correggi");
        errorTextDataDiNascita.setId(ERRORE);
        errorTextDataDiNascita.setVisible(false);

        dataDiNascitaField = new TextField();
        dataDiNascitaField.setPromptText("Inserisci la tua data di nascita (DD/MM/YYYY)");
        dataDiNascitaField.setId(INPUT_FIELD);

        errorTextNumeroTelefonico = new Text("Errore nell'inserimento del numero telefonico, correggi");
        errorTextNumeroTelefonico.setId(ERRORE);
        errorTextNumeroTelefonico.setVisible(false);

        numeroTelefonicoField = new TextField();
        numeroTelefonicoField.setPromptText("Inserisci il tuo numero telefonico");
        numeroTelefonicoField.setId(INPUT_FIELD);

        errorTextEmail = new Text("Errore nell'inserimento della E-mail, correggi");
        errorTextEmail.setId(ERRORE);
        errorTextEmail.setVisible(false);

        emailField = new TextField();
        emailField.setPromptText("Inserisci la tua email");
        emailField.setId(INPUT_FIELD);

        errorTextPassword = new Text("Errore nell'inserimento della password, correggi");
        errorTextPassword.setId(ERRORE);
        errorTextPassword.setVisible(false);

        passwordField = new TextField();
        passwordField.setPromptText("Inserisci la tua password");
        passwordField.setId(INPUT_FIELD);

        errorTextCodiceFiscale = new Text("Errore nell'inserimento del codice fiscale, correggi");
        errorTextCodiceFiscale.setId(ERRORE);
        errorTextCodiceFiscale.setVisible(false);

        codiceFiscaleField = new TextField();
        codiceFiscaleField.setPromptText("Inserisci il tuo codice fiscale");
        codiceFiscaleField.setId(INPUT_FIELD);

        Button submitButton = new Button("Registrati");
        submitButton.setId("specialistButton");
        submitButton.setOnAction(event -> {
            if (controllerApplicativo.isValidInput()) {
                LoginViewPatient patientView = new LoginViewPatient();
                patientView.start(new Stage());
                primaryStage.close();
            }
        });

        Button backButton = new Button("Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(event -> primaryStage.close());

        VBox vbox = new VBox(20, title, subtitle,errorTextNome, nomeField, errorTextCognome, cognomeField,errorTextDataDiNascita, dataDiNascitaField,errorTextNumeroTelefonico,numeroTelefonicoField,errorTextEmail,emailField, errorTextPassword,passwordField,errorTextCodiceFiscale,codiceFiscaleField,submitButton, backButton);
        vbox.setId("vbox");
        Scene scene = new Scene(vbox);
        // Carica il file CSS
        if (config.isColorMode()) {
            String colorStyle = getClass().getResource("/style/style_registrazione_paziente_a_colori.css").toExternalForm();
            scene.getStylesheets().add(colorStyle);
        } else {
            String bnStyle = getClass().getResource("/style/style_registrazione_paziente_bn.css").toExternalForm();
            scene.getStylesheets().add(bnStyle);
        }
        primaryStage.setTitle("Registrazione");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Metodi per mostrare gli errori
    public void showErrorNome() {
        errorTextNome.setVisible(true);
    }

    public void showErrorCognome() {
        errorTextCognome.setVisible(true);
    }

    public void showErrorDataDiNascita() {
        errorTextDataDiNascita.setVisible(true);
    }

    public void showErrorNumeroTelefonico() {
        errorTextNumeroTelefonico.setVisible(true);
    }

    public void showErrorEmail() {
        errorTextEmail.setVisible(true);
    }

    public void showErrorCodiceFiscale() {
        errorTextCodiceFiscale.setVisible(true);
    }

    public void showErrorPassword() {
        errorTextPassword.setVisible(true);
    }

    // Metodi per nascondere gli errori
    public void hideErrorNome() {
        errorTextNome.setVisible(false);
    }

    public void hideErrorCognome() {
        errorTextCognome.setVisible(false);
    }

    public void hideErrorDataDiNascita() {
        errorTextDataDiNascita.setVisible(false);
    }

    public void hideErrorNumeroTelefonico() {
        errorTextNumeroTelefonico.setVisible(false);
    }

    public void hideErrorEmail() {
        errorTextEmail.setVisible(false);
    }

    public void hideErrorCodiceFiscale() {
        errorTextCodiceFiscale.setVisible(false);
    }

    public void hideErrorPassword() {
        errorTextPassword.setVisible(false);
    }

    public void hideAllErrors() {
        errorTextNome.setVisible(false);
        errorTextCognome.setVisible(false);
        errorTextDataDiNascita.setVisible(false);
        errorTextNumeroTelefonico.setVisible(false);
        errorTextEmail.setVisible(false);
    }
}
