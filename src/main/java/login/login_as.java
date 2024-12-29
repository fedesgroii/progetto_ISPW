package login;

import javafx.application.Application; // Importa la classe Application di JavaFX
import javafx.geometry.Insets; // Importa la classe Insets per la gestione dei margini
import javafx.geometry.Pos; // Importa la classe Pos per la gestione delle posizioni
import javafx.scene.Scene; // Importa la classe Scene di JavaFX
import javafx.scene.control.Button; // Importa la classe Button di JavaFX
import javafx.scene.control.Hyperlink; // Importa la classe Hyperlink di JavaFX
import javafx.scene.layout.VBox; // Importa la classe VBox per il layout verticale
import javafx.scene.text.Text; // Importa la classe Text di JavaFX
import javafx.stage.Stage; // Importa la classe Stage di JavaFX
import startupconfig.StartupSettingsEntity;

public class login_as extends Application { // Definisce la classe principale che estende Application

    @Override
    public void start(Stage primaryStage) { // Metodo principale che avvia l'applicazione
        primaryStage.setTitle("Portale MindLab"); // Imposta il titolo della finestra principale

        // Ottieni le impostazioni di avvio
        StartupSettingsEntity settings = StartupSettingsEntity.getInstance(); // Ottiene l'istanza delle impostazioni di avvio

        // Titolo
        Text title = new Text("Portale MindLab"); // Crea un elemento di testo per il titolo
        title.setId("title"); // Imposta l'ID dell'elemento per lo stile CSS

        // Sottotitolo
        Text subtitle = new Text("Accedi come:"); // Crea un elemento di testo per il sottotitolo
        subtitle.setId("subtitle"); // Imposta l'ID dell'elemento per lo stile CSS

        // Pulsante Specialista
        Button specialistButton = new Button("Specialista"); // Crea un pulsante per l'opzione Specialista
        specialistButton.setId("specialistButton"); // Imposta l'ID del pulsante per lo stile CSS
        specialistButton.setMinWidth(200); // Imposta la larghezza minima del pulsante

        // Pulsante Paziente
        Button patientButton = new Button("Paziente"); // Crea un pulsante per l'opzione Paziente
        patientButton.setId("patientButton"); // Imposta l'ID del pulsante per lo stile CSS
        patientButton.setMinWidth(200); // Imposta la larghezza minima del pulsante

        // Link Prenota un appuntamento senza registrarti
        Hyperlink appointmentLink = new Hyperlink("Prenota un appuntamento senza registrarti"); // Crea un hyperlink per la prenotazione senza registrazione
        appointmentLink.setId("appointmentLink"); // Imposta l'ID dell'hyperlink per lo stile CSS
        appointmentLink.setGraphic(new Text("\uD83D\uDCC5")); // Imposta un'icona di calendario come grafica dell'hyperlink

        // Layout
        VBox vbox = new VBox(10, title, subtitle, specialistButton, patientButton, appointmentLink); // Crea un layout verticale (VBox) con i componenti
        vbox.setId("vbox"); // Imposta l'ID del layout per lo stile CSS
        vbox.setAlignment(Pos.CENTER); // Imposta l'allineamento dei componenti al centro
        vbox.setPadding(new Insets(20)); // Imposta i margini del layout

        Scene scene = new Scene(vbox, 400, 300); // Crea una scena con il layout VBox, specificando larghezza e altezza

        // Carica il file CSS in base alla modalità selezionata
        if (settings.isColorMode()) { // Controlla se la modalità a colori è attiva
            scene.getStylesheets().add(getClass().getResource("/style/style_login_a_colori.css").toExternalForm()); // Carica il file CSS a colori
        } else {
            scene.getStylesheets().add(getClass().getResource("/style/style_login_bn.css").toExternalForm()); // Carica il file CSS in bianco e nero
        }

        primaryStage.setScene(scene); // Imposta la scena principale dello stage
        primaryStage.show(); // Mostra lo stage principale
    }

    public static void main(String[] args) { // Metodo principale per l'esecuzione dell'applicazione
        launch(args); // Avvia l'applicazione JavaFX
    }
}
