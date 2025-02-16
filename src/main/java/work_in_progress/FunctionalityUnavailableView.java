package work_in_progress;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Objects;

public class FunctionalityUnavailableView {
    private static final String ICON_PATH = "/icone/clock.png"; // Percorso dell'icona
    private static final String STYLE_PATH = "/style/style_work_in_progress.css"; // Percorso del foglio di stile
    private static final int ICON_SIZE = 125; // Dimensione dell'icona
    private static final int SCENE_WIDTH = 500; // Larghezza della scena
    private static final int SCENE_HEIGHT = 400; // Altezza della scena
    private static final String MESSAGE_TEXT = "Ci scusiamo, ma questa funzionalità è in fase di costruzione.";
    private static final String DESCRIPTION_TEXT = "Stiamo lavorando per offrirti questa funzionalità al più presto. Torna a trovarci!";
    private static final String BACK_BUTTON_TEXT = "Torna alla pagina principale";
    private final Stage primaryStage;
    private final FunctionalityUnavailableController controller;

    public FunctionalityUnavailableView(Stage primaryStage, Stage previousStage) {
        this.primaryStage = primaryStage;
        this.controller = new FunctionalityUnavailableController(previousStage, primaryStage);
    }

    public void start() {
        // Layout principale
        VBox root = new VBox();
        root.getStyleClass().add("root-container"); // Usa classi CSS invece di ID

        // Immagine di avviso
        ImageView icon = new ImageView(new Image(
                Objects.requireNonNull(
                        getClass().getResource(ICON_PATH),
                        "Impossibile caricare l'icona: " + ICON_PATH
                ).toExternalForm()
        ));
        icon.setFitWidth(ICON_SIZE);
        icon.setFitHeight(ICON_SIZE);
        icon.getStyleClass().add("icon");

        // Testo di avviso principale
        Text message = new Text(MESSAGE_TEXT);
        message.getStyleClass().add("message");

        // Testo descrittivo aggiuntivo
        Text description = new Text(DESCRIPTION_TEXT);
        description.getStyleClass().add("description");

        // Bottone per tornare indietro
        Button backButton = new Button(BACK_BUTTON_TEXT);
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.handleBackButton());

        // Aggiunta degli elementi al layout
        root.getChildren().addAll(icon, message, description, backButton);
        root.setAlignment(Pos.CENTER);

        // Scena e styling
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        getClass().getResource(STYLE_PATH),
                        "Impossibile caricare il foglio di stile: " + STYLE_PATH
                ).toExternalForm()
        );

        // Configurazione dello Stage
        primaryStage.setTitle("Pagina in costruzione");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(SCENE_WIDTH); // Imposta larghezza minima
        primaryStage.setMinHeight(SCENE_HEIGHT); // Imposta altezza minima
        primaryStage.setResizable(false); // Permette il ridimensionamento
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}