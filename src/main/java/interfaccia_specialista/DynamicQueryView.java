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

    // Il costruttore è privato per impedire l'istanza della classe, poiché è una classe di utilità
    private DynamicQueryView() {
        throw new UnsupportedOperationException("Classe di utilità, non istanziabile");
    }

    /**
     * Metodo statico per visualizzare una finestra con i risultati di una query dinamica.
     *
     * @param stage     Lo stage in cui verrà mostrata la finestra.
     * @param titolo    Il titolo della finestra.
     * @param messaggio Un messaggio descrittivo per l'utente.
     * @param risultati Una lista di stringhe contenenti i risultati della query.
     * @param onBack    Una funzione da eseguire quando l'utente preme il pulsante "Torna Indietro".
     */
    public static void show(Stage stage, String titolo, String messaggio, List<String> risultati, Runnable onBack) {

        // Creazione dell'header con il titolo
        Text headerText = new Text(titolo);
        HBox header = new HBox(headerText);
        header.setAlignment(Pos.CENTER);  // Centra il titolo orizzontalmente
        header.setPadding(new Insets(20)); // Imposta un padding di 20px intorno all'header

        // Creazione della descrizione del messaggio per l'utente
        Label description = new Label(messaggio);

        // Contenitore per i risultati della query
        VBox resultContainer = new VBox(10); // Spazio di 10px tra gli elementi della VBox
        if (risultati.isEmpty()) {
            // Se non ci sono risultati, mostra un messaggio di avviso
            resultContainer.getChildren().add(new Label("Nessun risultato trovato."));
        } else {
            // Altrimenti, aggiunge ogni risultato come una nuova Label nella VBox
            risultati.forEach(riga -> resultContainer.getChildren().add(new Label(riga)));
        }

        // Creazione del pulsante per tornare indietro
        Button btnBack = new Button("Torna Indietro");
        btnBack.setOnAction(e -> {
            onBack.run(); // Esegue il metodo di callback passato come parametro
            stage.close(); // Chiude la finestra attuale
        });

        // Footer che contiene il pulsante "Torna Indietro"
        HBox footer = new HBox(btnBack);
        footer.setAlignment(Pos.CENTER);  // Centra il pulsante
        footer.setPadding(new Insets(20)); // Aggiunge un padding di 20px

        // Layout principale con BorderPane:
        // - header in alto
        // - descrizione e risultati al centro
        // - footer con il pulsante in basso
        BorderPane layout = new BorderPane();
        layout.setTop(header);
        layout.setCenter(new VBox(20, description, resultContainer)); // VBox con spaziatura tra descrizione e risultati
        layout.setBottom(footer);

        // Creazione della scena e impostazione nello stage
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.setTitle(titolo); // Imposta il titolo della finestra
        stage.show(); // Mostra la finestra all'utente
    }
}
