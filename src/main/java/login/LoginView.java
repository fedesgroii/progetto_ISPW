package login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login_inserimento_dati.LoginViewPatient;
import login_inserimento_dati.LoginViewSpecialist;
import registrazione.RegistrazionePazienteView;
import startupconfig.StartupSettingsEntity;

public class LoginView {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Portale MindLab");

        // Titolo
        Text title = new Text("Portale MindLab");
        title.setId("title");

        // Sottotitolo
        Text subtitle = new Text("Accedi come:");
        subtitle.setId("subtitle");

        // Pulsante Specialista
        Button specialistButton = new Button("Specialista");
        specialistButton.setId("login");
        specialistButton.setPrefWidth(200);

        // Pulsante Paziente
        Button patientButton = new Button("Paziente");
        patientButton.setId("login");
        patientButton.setPrefWidth(200);

        // Sottotitolo
        Text subtitle2 = new Text("oppure");
        subtitle2.setId("subtitle");

        // Pulsante Registrati
        Button registerButton = new Button("Registrati");
        registerButton.setId("registrazione");
        registerButton.setPrefWidth(200);

        // Sottotitolo
        Text subtitle3 = new Text("altrimenti");
        subtitle3.setId("subtitle");

        // Pulsante Prenota un appuntamento senza registrarti
        Button appointmentButton = new Button("Prenota un appuntamento senza registrarti");
        appointmentButton.setId("appointmentButton");
        appointmentButton.setPrefWidth(400);

        // Layout per pulsanti Specialista e Paziente affiancati
        HBox hbox = new HBox(20, specialistButton, patientButton);
        hbox.setAlignment(Pos.CENTER);

        // Layout principale (contenuto interno)
        VBox contentBox = new VBox(30, title, subtitle, hbox, subtitle2, registerButton, subtitle3, appointmentButton);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(40));

        // Contenitore esterno con sfondo bianco e bordi arrotondati
        VBox container = new VBox(contentBox);
        container.setId("container"); // Imposta l'ID per lo stile CSS
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));

        // StackPane per centrare il container nella scena
        StackPane root = new StackPane(container);
        root.setId("root");

        // Scena
        Scene scene = new Scene(root, 800, 600);

        // Controller setup
        LoginController controller = new LoginController();

        // Aggiungi eventi personalizzati
        specialistButton.setOnAction(event -> {
            controller.handleSpecialistLogin();
            LoginViewSpecialist specialistView = new LoginViewSpecialist();
            specialistView.start(new Stage());
            primaryStage.close();
        });

        patientButton.setOnAction(event -> {
            controller.handlePatientLogin();
            LoginViewPatient patientView = new LoginViewPatient();
            patientView.start(new Stage());
            primaryStage.close();
        });

        registerButton.setOnAction(event -> {
        RegistrazionePazienteView regView = new RegistrazionePazienteView();
            regView.start(new Stage());
            primaryStage.close();
        });


        appointmentButton.setOnAction(event -> {
            controller.handleAppointmentWithoutLogin();
        });

        // Carica il file CSS
        if (config.isColorMode()) {
            String colorStyle = getClass().getResource("/style/style_login_a_colori.css").toExternalForm();
            scene.getStylesheets().add(colorStyle);
        } else {
            String bnStyle = getClass().getResource("/style/style_login_bn.css").toExternalForm();
            scene.getStylesheets().add(bnStyle);
        }

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
