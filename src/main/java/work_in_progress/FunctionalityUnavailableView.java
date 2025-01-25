package work_in_progress;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class FunctionalityUnavailableView extends Application {

    // Attributo per salvare il riferimento allo stage precedente
    private final Stage previousStage;

    // Costruttore che accetta lo stage precedente
    public FunctionalityUnavailableView(Stage previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout principale
        VBox root = new VBox();
        root.setId("root");

        // Immagine di avviso
        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/icone/clock.png")).toExternalForm()));
        icon.setFitWidth(125);
        icon.setFitHeight(125);
        icon.setId("icon");

        // Testo di avviso principale
        Text message = new Text("Ci scusiamo, ma questa funzionalità è in fase di costruzione.");
        message.setId("message");

        // Testo descrittivo aggiuntivo
        Text description = new Text("Stiamo lavorando per offrirti questa funzionalità al più presto. Torna a trovarci!");
        description.setId("description");

        // Bottone per tornare indietro
        Button backButton = new Button("Torna alla pagina principale");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            // Riapre lo stage precedente
            if (previousStage != null) {
                previousStage.show(); // Mostra la finestra precedente
            }
            primaryStage.close(); // Chiude la finestra corrente
        });

        // Aggiunta degli elementi al layout
        root.getChildren().addAll(icon, message, description, backButton);
        root.setAlignment(Pos.CENTER);

        // Scena e styling
        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/style_work_in_progress.css")).toExternalForm());

        // Configurazione dello Stage
        primaryStage.setTitle("Pagina in costruzione");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
