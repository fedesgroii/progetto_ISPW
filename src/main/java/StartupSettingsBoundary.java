import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Screen;

import java.util.Objects;

public class StartupSettingsBoundary extends Application {
    private StartupSettingsController controller; // Riferimento al controller

    @Override
    public void start(Stage primaryStage) {
        // Inizializza il controller
        controller = new StartupSettingsController();

        // Imposta il titolo della finestra
        primaryStage.setTitle("Configurazione Avvio");

        // Contenitore principale
        VBox container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("root");

        // Titolo
        Text title = new Text("Configurazione Avvio");
        title.getStyleClass().add("title");

        // Opzione colore
        ToggleGroup colorGroup = new ToggleGroup();
        RadioButton colorMode = new RadioButton("Modalità a Colori");
        colorMode.setToggleGroup(colorGroup);
        colorMode.setSelected(true);
        RadioButton bwMode = new RadioButton("Modalità Bianco e Nero");
        bwMode.setToggleGroup(colorGroup);
        HBox colorBox = new HBox(10, colorMode, bwMode);
        colorBox.setAlignment(Pos.CENTER);
        colorBox.getStyleClass().add("option-box");

        // Opzione salvataggio
        CheckBox saveToDatabase = new CheckBox("Salva dati nel database");
        saveToDatabase.getStyleClass().add("checkbox");
        VBox saveBox = new VBox(saveToDatabase);
        saveBox.setAlignment(Pos.CENTER_LEFT);
        saveBox.getStyleClass().add("option-box");

        // Pulsante di conferma
        Button confirmButton = new Button("Conferma");
        confirmButton.getStyleClass().add("button");
        confirmButton.setOnAction(event -> {
            // Invia i dati al controller per l'elaborazione
            controller.processSettings(colorMode.isSelected(), saveToDatabase.isSelected());
            primaryStage.close();
        });

        // Aggiungi tutti i componenti al contenitore
        container.getChildren().addAll(title, colorBox, saveBox, confirmButton);

        // Creare e impostare la scena
        Scene scene = new Scene(container, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/style_avvio.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
