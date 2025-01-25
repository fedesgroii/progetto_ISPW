package startupconfig;

import javafx.application.Application; // Importa la classe base per un'applicazione JavaFX
import javafx.geometry.Insets; // Importa per gestire i margini interni (padding)
import javafx.geometry.Pos; // Importa per gestire l'allineamento dei componenti
import javafx.scene.Scene; // Importa la classe per gestire la scena
import javafx.scene.control.Button; // Importa il pulsante
import javafx.scene.control.CheckBox; // Importa la casella di controllo
import javafx.scene.control.RadioButton; // Importa il pulsante di scelta
import javafx.scene.control.ToggleGroup; // Importa il gruppo di pulsanti di scelta (esclusivi)
import javafx.scene.image.Image; // Importa la classe per gestire immagini
import javafx.scene.image.ImageView; // Importa per visualizzare le immagini
import javafx.scene.layout.HBox; // Importa per creare un contenitore orizzontale
import javafx.scene.layout.VBox; // Importa per creare un contenitore verticale
import javafx.scene.text.Text; // Importa per visualizzare testo
import javafx.stage.Stage; // Importa per rappresentare la finestra principale
import javafx.stage.Screen; // Importa per ottenere informazioni sullo schermo
import login.LoginView;
import work_in_progress.FunctionalityUnavailableView;

import java.util.Objects; // Importa per confronti e manipolazioni di oggetti null-safe

// Classe che rappresenta la boundary del pattern Singleton
public class StartupSettingsBoundary extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Metodo principale chiamato da JavaFX per avviare l'interfaccia

         StartupSettingsController controller = new StartupSettingsController(); // Crea un'istanza del controller per gestire le azioni dell'utente

        primaryStage.setTitle("MindLab");
        // **Aggiunta dell'icona dell'applicazione**
        // Percorso relativo all'icona dell'applicazione
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

        // Crea l'opzione di salvataggio
        CheckBox saveToDatabase = new CheckBox("Salva dati nel database"); // Casella di controllo per il salvataggio
        saveToDatabase.getStyleClass().add("checkbox"); // Aggiunge una classe di stile CSS
        VBox saveBox = new VBox(saveToDatabase); // Contenitore verticale per la casella di controllo
        saveBox.setAlignment(Pos.CENTER_LEFT); // Allinea l'opzione a sinistra
        saveBox.getStyleClass().add("option-box"); // Aggiunge una classe di stile CSS

        // Crea il pulsante di conferma
        Button confirmButton = new Button("Conferma"); // Pulsante per confermare le scelte
        confirmButton.getStyleClass().add("button"); // Aggiunge una classe di stile CSS

        confirmButton.setOnAction(event -> {
        // Azione da eseguire al clic del pulsante
        controller.processSettings(colorMode.isSelected(), saveToDatabase.isSelected()); // Passa i dati al controller

        // Avvia la finestra di login
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();
        loginView.start(loginStage); // Avvia correttamente il metodo start chiamando la pagina di login
        primaryStage.close(); // Ora chiudi la finestra principale solo dopo aver avviato la finestra di login

});

        // Aggiunge tutti gli elementi al contenitore principale
        container.getChildren().addAll(iconaPower, title, colorBox, saveBox, confirmButton);

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
        //launch(args); // Metodo JavaFX per avviare l'interfaccia
       //Home.launch(Home.class, args);
        FunctionalityUnavailableView.launch(FunctionalityUnavailableView.class,args);
    }
}
