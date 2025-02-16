package startupconfig;

import interfaccia_specialista.SpecialistDashboardView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import login.LoginView;
import java.util.Objects;

public class StartupSettingsBoundary extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Metodo principale chiamato da JavaFX per avviare l'interfaccia
        StartupSettingsController controller = new StartupSettingsController(); // Crea un'istanza del controller per gestire le azioni dell'utente
        primaryStage.setTitle("MindLab");

        // **Aggiunta dell'icona dell'applicazione**
        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/logo_ML.png"))); // Carica l'immagine dell'icona
        primaryStage.getIcons().add(appIcon); // Aggiunge l'icona alla finestra principale (Stage)

        VBox container = new VBox(); // Crea un contenitore verticale per organizzare gli elementi
        container.setSpacing(20); // Spaziatura tra gli elementi
        container.setPadding(new Insets(20)); // Margini interni del contenitore
        container.setAlignment(Pos.CENTER); // Allinea gli elementi al centro
        container.getStyleClass().add("root"); // Aggiunge una classe di stile CSS al contenitore

        // Carica e visualizza l'icona principale nella UI
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/power-off.png"))); // Carica l'immagine
        ImageView iconaPower = new ImageView(icon); // Crea un visualizzatore per l'immagine
        iconaPower.setFitHeight(50); // Imposta l'altezza dell'immagine
        iconaPower.setFitWidth(50); // Imposta la larghezza dell'immagine

        // Crea il titolo
        Text title = new Text("Configurazione Avvio di MindLab"); // Testo del titolo
        title.getStyleClass().add("title"); // Aggiunge una classe di stile CSS al testo

        // Crea i pulsanti di scelta per la modalità colore
        ToggleGroup colorGroup = new ToggleGroup(); // Gruppo di pulsanti di scelta
        RadioButton colorMode = new RadioButton("Modalità a Colori"); // Opzione per la modalità a colori
        colorMode.setToggleGroup(colorGroup); // Aggiunge il pulsante al gruppo
        colorMode.setSelected(true); // Imposta l'opzione come predefinita
        RadioButton bwMode = new RadioButton("Modalità Bianco e Nero"); // Opzione per la modalità bianco e nero
        bwMode.setToggleGroup(colorGroup); // Aggiunge il pulsante al gruppo
        HBox colorBox = new HBox(10, colorMode, bwMode); // Contenitore orizzontale per le opzioni
        colorBox.setAlignment(Pos.CENTER); // Allinea le opzioni al centro
        colorBox.getStyleClass().add("option-box"); // Aggiunge una classe di stile CSS

        // Crea il gruppo di pulsanti di scelta per "Memoria RAM", "Database", "File System"
        ToggleGroup storageGroup = new ToggleGroup(); // Gruppo di pulsanti di scelta
        RadioButton memoryOption = new RadioButton("Memoria RAM"); // Opzione per Memoria RAM
        memoryOption.setToggleGroup(storageGroup); // Aggiunge il pulsante al gruppo
        memoryOption.setSelected(true); // Imposta l'opzione come predefinita
        RadioButton databaseOption = new RadioButton("Database"); // Opzione per Database
        databaseOption.setToggleGroup(storageGroup); // Aggiunge il pulsante al gruppo
        RadioButton fileSystemOption = new RadioButton("File System"); // Opzione per File System
        fileSystemOption.setToggleGroup(storageGroup); // Aggiunge il pulsante al gruppo
        HBox storageBox = new HBox(10, memoryOption, databaseOption, fileSystemOption); // Contenitore orizzontale per le opzioni
        storageBox.setAlignment(Pos.CENTER); // Allinea le opzioni al centro
        storageBox.getStyleClass().add("option-box"); // Aggiunge una classe di stile CSS

        // Crea il pulsante di conferma
        Button confirmButton = new Button("Conferma"); // Pulsante per confermare le scelte
        confirmButton.getStyleClass().add("button"); // Aggiunge una classe di stile CSS
        confirmButton.setOnAction(event -> {
            // Determina l'opzione di salvataggio selezionata
            int selectedStorageOption = 0; // Default: RAM
            if (databaseOption.isSelected()) {
                selectedStorageOption = 1; // Database
            } else if (fileSystemOption.isSelected()) {
                selectedStorageOption = 2; // File System
            }

            // Passa le impostazioni al controller
            controller.processSettings(colorMode.isSelected(), selectedStorageOption); // Passa i dati al controller

            // Avvia la finestra di login
            LoginView loginView = new LoginView();
            Stage loginStage = new Stage();
            loginView.start(loginStage); // Avvia correttamente il metodo start chiamando la pagina di login
            primaryStage.close(); // Ora chiudi la finestra principale solo dopo aver avviato la finestra di login
        });

        // Aggiunge tutti gli elementi al contenitore principale
        container.getChildren().addAll(iconaPower, title, colorBox, storageBox, confirmButton);

        // Configura e mostra la scena
        Scene scene = new Scene(container, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()); // Crea una scena a schermo intero
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/style_avvio.css")).toExternalForm()); // Aggiunge lo stile CSS
        primaryStage.setScene(scene); // Imposta la scena sulla finestra principale
        primaryStage.setFullScreen(true); // Imposta la finestra a schermo intero
        primaryStage.setResizable(false);
        primaryStage.show(); // Mostra la finestra
    }

    public static void main(String[] args) {
        // Metodo main per avviare l'applicazione
        launch(args); // Metodo JavaFX per avviare l'interfaccia
    }
}