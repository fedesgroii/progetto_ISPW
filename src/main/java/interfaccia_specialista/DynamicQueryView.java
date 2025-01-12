package interfaccia_specialista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class DynamicQueryView {

    /**
     * Mostra una vista dinamica con il risultato della query.
     *
     * @param stage     Il contenitore principale della finestra.
     * @param titolo    Il titolo della finestra e dell'header.
     * @param messaggio Il messaggio descrittivo che introduce i risultati.
     * @param risultati La lista dei risultati ottenuti dalla query.
     * @param onBack    L'azione da eseguire al clic del pulsante "Torna Indietro".
     */
    public static void show(Stage stage, String titolo, String messaggio, List<String> risultati, Runnable onBack) {
        // Header
        Text headerText = new Text(titolo);
        headerText.getStyleClass().add("header-text");

        HBox header = new HBox(headerText);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setId("header");

        // Messaggio descrittivo
        Label description = new Label(messaggio);
        description.getStyleClass().add("description-label");

        // Area centrale per mostrare i risultati
        VBox resultContainer = new VBox(10);
        resultContainer.setAlignment(Pos.CENTER);
        resultContainer.setPadding(new Insets(20));
        resultContainer.setId("result-container");

        if (risultati.isEmpty()) {
            resultContainer.getChildren().add(new Label("Nessun risultato trovato."));
        } else {
            for (String riga : risultati) {
                resultContainer.getChildren().add(new Label(riga));
            }
        }

        // Footer con il pulsante per tornare all'interfaccia precedente
        Button btnBack = new Button("Torna Indietro");
        btnBack.setId("backButton");
        btnBack.setOnAction(e -> {
            if (onBack != null) {
                onBack.run();
            }
            stage.close();
        });

        HBox footer = new HBox(btnBack);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.setId("footer");

        // Layout principale
        BorderPane layout = new BorderPane();
        layout.setTop(header);
        layout.setCenter(new VBox(20, description, resultContainer));
        layout.setBottom(footer);
        layout.setId("main-container");

        // Scena
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(DynamicQueryView.class.getResource("/style/style_query_result.css").toExternalForm());

        stage.setTitle(titolo);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
