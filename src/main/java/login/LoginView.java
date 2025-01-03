package login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login_inserimento_dati.LoginViewPatient;
import login_inserimento_dati.LoginViewSpecialist;
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
        specialistButton.setId("specialistButton");
        specialistButton.setPrefWidth(400);

        // Pulsante Paziente
        Button patientButton = new Button("Paziente");
        patientButton.setId("patientButton");
        patientButton.setPrefWidth(400);

        // Pulsante Prenota un appuntamento senza registrarti
        Button appointmentButton = new Button("Prenota un appuntamento senza registrarti");
        appointmentButton.setId("appointmentButton");
        appointmentButton.setPrefWidth(400);

        // Layout
        VBox vbox = new VBox(30, title, subtitle, specialistButton, patientButton, appointmentButton);
        vbox.setId("vbox");
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40));

        // Scena
        Scene scene = new Scene(vbox);
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);

        // Controller setup
        LoginController controller = new LoginController();



        // Aggiungi eventi personalizzati
        specialistButton.setOnAction(event -> {
            controller.handleSpecialistLogin();
            // Avvia la finestra di login per lo specialista
            LoginViewSpecialist specialistView = new LoginViewSpecialist();
            specialistView.start(new Stage());
            primaryStage.close(); // Ora chiudi la finestra principale solo dopo aver avviato la finestra di login
        });



        patientButton.setOnAction(event -> {
            controller.handlePatientLogin();
            // Avvia la finestra di login per lo specialista
            LoginViewPatient patientView = new LoginViewPatient();
            patientView.start(new Stage());
            primaryStage.close(); // Ora chiudi la finestra principale solo dopo aver avviato la finestra di login
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
