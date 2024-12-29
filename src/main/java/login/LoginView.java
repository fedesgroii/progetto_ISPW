package login;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import startupconfig.StartupSettingsEntity;

// Boundary Class
public class LoginView extends Application {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Inizio dell'applicazione");

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
        specialistButton.setPrefWidth(400); // Larghezza aumentata

        // Pulsante Paziente
        Button patientButton = new Button("Paziente");
        patientButton.setId("patientButton");
        patientButton.setPrefWidth(400); // Larghezza aumentata

        // Link Prenota un appuntamento senza registrarti
        Hyperlink appointmentLink = new Hyperlink("Prenota un appuntamento senza registrarti");
        appointmentLink.setId("appointmentLink");
        appointmentLink.setMinWidth(400); // Larghezza minima per il link

        // Layout
        VBox vbox = new VBox(30, title, subtitle, specialistButton, patientButton, appointmentLink); // Spaziatura aumentata
        vbox.setId("vbox");
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40)); // Padding aumentato

        // Imposta la scena con dimensioni iniziali più grandi
        Scene scene = new Scene(vbox); // Dimensioni iniziali
        primaryStage.setResizable(false); // Consente il ridimensionamento
        primaryStage.setMaximized(true); // Avvia a schermo intero

        // Controller setup
        LoginController controller = new LoginController();
        specialistButton.setOnAction(event -> controller.handleSpecialistLogin());
        patientButton.setOnAction(event -> controller.handlePatientLogin());
        appointmentLink.setOnAction(event -> controller.handleAppointmentWithoutLogin());

        // Carica il file CSS in base alla configurazione
        System.out.println("Caricamento CSS in corso...");
        if (config.isColorMode()) {
            String colorStyle = getClass().getResource("/style/style_login_a_colori.css").toExternalForm();
            System.out.println("Caricamento CSS a colori: " + colorStyle);
            scene.getStylesheets().add(colorStyle);
        } else {
            String bnStyle = getClass().getResource("/style/style_login_bn.css").toExternalForm();
            System.out.println("Caricamento CSS bianco e nero: " + bnStyle);
            scene.getStylesheets().add(bnStyle);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("Fine dell'applicazione");
    }

    public static void main(String[] args) {
        launch(args); // Avvia l'applicazione
    }
}
