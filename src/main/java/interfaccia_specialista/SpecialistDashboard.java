package interfaccia_specialista;

import Storage_DB.DatabaseOperations;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class SpecialistDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        final DatabaseOperations dbo = new DatabaseOperations();
        // Creazione pulsanti
        Button btnVisualizzaDatiPazienti = new Button("Visualizza Dati Pazienti");
        Button btnVisualizzaVisiteSettimana = new Button("Visualizza Visite della Settimana");
        Button btnVisualizzaTutteLeVisite = new Button("Visualizza Tutte le Visite");
        Button btnNuovaVisita = new Button("Registra Nuova Visita");
        Button btnLogout = new Button("Log Out");

        // Impostazione degli ID per lo stile CSS
        btnVisualizzaDatiPazienti.setId("button");
        btnVisualizzaVisiteSettimana.setId("button");
        btnVisualizzaTutteLeVisite.setId("button");
        btnNuovaVisita.setId("button");
        btnLogout.setId("logoutButton");

        // Header
        Text headerText = new Text("Interfaccia Specialista");
        headerText.getStyleClass().add("header-text");

        HBox header = new HBox(headerText);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setId("header");

        // Creazione HBox per ogni coppia di bottoni
        HBox row1 = new HBox(20, btnVisualizzaDatiPazienti);
        HBox row2 = new HBox(20, btnVisualizzaTutteLeVisite, btnVisualizzaVisiteSettimana);
        HBox row3 = new HBox(20, btnNuovaVisita);

        // Allineamento e padding per le righe
        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        row1.setPadding(new Insets(10));
        row2.setPadding(new Insets(10));
        row3.setPadding(new Insets(10));

        // Contenitore per le righe di bottoni
        VBox buttonContainer = new VBox(20, row1, row2, row3);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));
        buttonContainer.setId("button-container");

        // Footer con il bottone di logout
        HBox footer = new HBox(btnLogout);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.setId("footer");

        // Layout principale
        BorderPane layout = new BorderPane();
        layout.setTop(header);
        layout.setCenter(buttonContainer);
        layout.setBottom(footer);
        layout.setId("main-container");

        // Scena
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/style_doctor_dashboard.css")).toExternalForm());

        primaryStage.setTitle("Dashboard Specialista");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        // Azioni pulsanti

        btnVisualizzaDatiPazienti.setOnAction(e -> {
            List<String> risultati = null;
            try {
                risultati = dbo.executeQuery("SELECT * FROM pazienti");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            DynamicQueryView.show(
                    new Stage(),
                    "Dati Pazienti",
                    "Elenco dei pazienti registrati nel sistema:",
                    risultati,
                    () -> primaryStage.show() // Ritorno alla dashboard
            );

            primaryStage.hide(); // Nasconde la finestra principale mentre mostra quella nuova
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
