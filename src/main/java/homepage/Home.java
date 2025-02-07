package homepage;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Paziente;
import prenotazione_visita.PrenotazioneVisitaView;
import session_manager.SessionManagerPaziente;


public class Home extends Application {
    Paziente gestore_sessione_pazienti = SessionManagerPaziente.getPazienteLoggato();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Home - MindLab");

        // Contenitore principale
        VBox container = new VBox();
        container.getStyleClass().add("container");

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_RIGHT);
        header.getStyleClass().add("header");

        // Icona utente a sinistra
        HBox leftIcons = new HBox();
        Button userButton = createImageButton("/icone/user-large1.png", 32, 32);
        leftIcons.getChildren().add(userButton);
        HBox.setHgrow(leftIcons, Priority.ALWAYS);

        // Icone a destra
        HBox rightIcons = new HBox(10); // Riduci spaziatura
        rightIcons.setAlignment(Pos.CENTER_RIGHT);
        Button cogButton = createImageButton("/icone/user-gear1.png", 32, 40);
        Button lockButton = createImageButton("/icone/shield1.png", 32, 32);
        rightIcons.getChildren().addAll(cogButton, lockButton);

        header.getChildren().addAll(leftIcons, rightIcons);

        // Benvenuto
        Text welcomeText = new Text("Ciao, nomePaziente !");
        welcomeText.getStyleClass().add("welcome-text");

        // Bottoni principali
        VBox buttonContainer = new VBox();
        buttonContainer.setSpacing(30); // Riduci spaziatura
        buttonContainer.setAlignment(Pos.CENTER);

        Button prenotaButton = createButton("Prenota una visita", "prenota-button");
        Button storicoButton = createButton("Storico Visite", "storico-button");
        Button shopButton = createButton("Shop", "shop-button");

        buttonContainer.getChildren().addAll(prenotaButton, storicoButton, shopButton);

        // Footer
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.getStyleClass().add("footer");

        Text bachecaLink = createFooterLink("Bacheca");
        Text homeLink = createFooterLink("Home");
        homeLink.getStyleClass().add("Home-link");
        Text visiteLink = createFooterLink("Visite");

        footer.getChildren().addAll(bachecaLink, homeLink, visiteLink);

        // Aggiungere tutto al contenitore principale
        container.getChildren().addAll(header, welcomeText, buttonContainer, footer);

        // Creare la scena
        Scene scene = new Scene(container, 1200, 900); // Riduci dimensioni scena
        scene.getStylesheets().add(getClass().getResource("/style/style_home.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Comportamenti bottoni presenti
        prenotaButton.setOnAction(event -> {
            PrenotazioneVisitaView prenotazoneView = new PrenotazioneVisitaView();
            prenotazoneView.start(new Stage());
            primaryStage.close();
        });

        shopButton.setOnAction(event -> {
            Paziente paziente = SessionManagerPaziente.getPazienteLoggato();
            System.out.println("Nome "+paziente.getNome());
            System.out.println("Cognome "+paziente.getCognome());
        });
    }

    // Metodo per creare un bottone con immagine
    private Button createImageButton(String imagePath, double fitHeight, double fitWidth) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(fitHeight);
        imageView.setFitWidth(fitWidth);

        Button button = new Button();
        button.setGraphic(imageView);
        button.getStyleClass().add("hidden-button");
        // Aggiungi l'azione desiderata per il pulsante qui
        button.setOnAction(event -> {

        });

        return button;
    }

    // Metodo per creare un bottone
    private Button createButton(String text, String cssClass) {
        Button button = new Button(text);
        button.getStyleClass().add(cssClass);
        button.setMaxWidth(300); // Riduci larghezza massima
        return button;
    }

    // Metodo per creare un collegamento nel footer
    private Text createFooterLink(String label) {
        Text link = new Text(label);
        link.getStyleClass().add("footer-link");
        return link;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
