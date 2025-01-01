package login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login_insert.LoginSpecialistView;
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
            LoginSpecialistView loginviewspec = new LoginSpecialistView();
            Stage loginStage = new Stage();
            loginviewspec.start(loginStage); // Avvia correttamente il metodo start chiamando la pagina di login

            primaryStage.close(); // Ora chiudi la finestra principale solo dopo aver avviato la finestra di login
        });



        patientButton.setOnAction(event -> {
            controller.handlePatientLogin();
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
        primaryStage.show();
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
    }
}
