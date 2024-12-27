import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class login_as extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Portale MindLab");

        // Titolo
        Text title = new Text("Portale MindLab");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2F4F4F;");

        // Sottotitolo
        Text subtitle = new Text("Accedi come:");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-fill: #2F4F4F;");

        // Pulsante Specialista
        Button specialistButton = new Button("Specialista");
        specialistButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-size: 16px;");
        specialistButton.setMinWidth(200);

        // Pulsante Paziente
        Button patientButton = new Button("Paziente");
        patientButton.setStyle("-fx-background-color: #32CD32; -fx-text-fill: white; -fx-font-size: 16px;");
        patientButton.setMinWidth(200);

        // Link Prenota un appuntamento senza registrarti
        Hyperlink appointmentLink = new Hyperlink("Prenota un appuntamento senza registrarti");
        appointmentLink.setStyle("-fx-text-fill: #2F4F4F; -fx-font-size: 14px;");
        appointmentLink.setGraphic(new Text("\uD83D\uDCC5")); // icona del calendario

        // Layout
        VBox vbox = new VBox(10, title, subtitle, specialistButton, patientButton, appointmentLink);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #D3D3D3; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}