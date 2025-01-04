package registrazione;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login_inserimento_dati.LoginAppController;
import startupconfig.StartupSettingsEntity;

public class registrazionePazienteView {
    //campi nascosti per mostrare eventuali errori nell'inserimento dati
    private Text errorTextNome;
    private Text errorTextCognome;
    private Text errorTextDataDiNascita;
    private Text errorTextNumeroTelefonico;
    private Text errorTextEmail;

    protected TextField nomeField;
    protected TextField cognomeField;
    protected TextField dataDiNascitaField;
    protected TextField numeroTelefonicoField;
    protected TextField emailField;

    private final registrazionePazienteControllerApplicativo controllerApplicativo = new registrazionePazienteControllerApplicativo(this);

    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();

    public void start(Stage primaryStage) {
        Text title = new Text("Registrazione");
        title.setId("title");

        Text subtitle = new Text("Inserisci i tuoi dati");
        subtitle.setId("subtitle");

        errorTextNome = new Text("Errore nell'inserimento del nome, correggi");
        errorTextNome.setId("errorText");
        errorTextNome.setVisible(false);

        nomeField = new TextField();
        nomeField.setPromptText("Inserisci il tuo nome");
        nomeField.setId("inputField");

        errorTextCognome = new Text("Errore nell'inserimento del cognome, correggi");
        errorTextCognome.setId("errorText");
        errorTextCognome.setVisible(false);

        cognomeField = new TextField();
        cognomeField.setPromptText("Inserisci il tuo cognome");
        cognomeField.setId("inputField");

        errorTextDataDiNascita = new Text("Errore nell'inserimento della data, correggi");
        errorTextDataDiNascita.setId("errorText");
        errorTextDataDiNascita.setVisible(false);

        dataDiNascitaField = new TextField();
        dataDiNascitaField.setPromptText("Inserisci la tua data di nascita");
        dataDiNascitaField.setId("inputField");

        errorTextNumeroTelefonico = new Text("Errore nell'inserimento del numero telefonico, correggi");
        errorTextNumeroTelefonico.setId("errorText");
        errorTextNumeroTelefonico.setVisible(false);

        numeroTelefonicoField = new TextField();
        numeroTelefonicoField.setPromptText("Inserisci il tuo numero telefonico");
        numeroTelefonicoField.setId("inputField");

        errorTextEmail = new Text("Errore nell'inserimento della E-mail, correggi");
        errorTextEmail.setId("errorText");
        errorTextEmail.setVisible(false);

        emailField = new TextField();
        emailField.setPromptText("Inserisci la tua email");
        emailField.setId("inputField");

        Button submitButton = new Button("Registrati");
        submitButton.setId("specialistButton");
        submitButton.setOnAction(event -> {


        });

        Button backButton = new Button("Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(event -> primaryStage.close());

        VBox vbox = new VBox(20, title, subtitle, nomeField, cognomeField, errorTextDataDiNascita, dataDiNascitaField, errorTextNumeroTelefonico, numeroTelefonicoField, emailField, submitButton, backButton);
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

    // Metodi di validazione
    private void validateForm() {
        // Reset degli errori
        errorTextNome.setVisible(false);
        errorTextCognome.setVisible(false);
        errorTextDataDiNascita.setVisible(false);
        errorTextNumeroTelefonico.setVisible(false);
        errorTextEmail.setVisible(false);
    }


}
