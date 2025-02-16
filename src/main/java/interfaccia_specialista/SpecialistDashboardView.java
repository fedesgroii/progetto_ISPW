package interfaccia_specialista;

// Importazioni delle classi JavaFX necessarie per creare l'interfaccia utente.
import javafx.geometry.Insets;                    // Gestisce i margini/padding degli elementi.
import javafx.geometry.Pos;                       // Definisce le posizioni di allineamento.
import javafx.scene.Scene;                        // Rappresenta la scena (contenitore principale dell'interfaccia).
import javafx.scene.control.Alert;                // Permette di mostrare finestre di dialogo di tipo Alert.
import javafx.scene.control.Button;               // Classe per creare pulsanti.
import javafx.scene.layout.BorderPane;            // Layout che organizza i nodi in regioni: top, center, bottom, etc.
import javafx.scene.layout.HBox;                  // Layout che dispone i nodi in una riga orizzontale.
import javafx.scene.layout.VBox;                  // Layout che dispone i nodi in una colonna verticale.
import javafx.scene.text.Text;                    // Nodo per mostrare testo.
import javafx.stage.Stage;                        // Rappresenta una finestra (stage) dell'applicazione.

import java.util.List;                           // Per utilizzare le liste.
import java.util.Objects;                        // Per metodi di utilità, come requireNonNull.

public class SpecialistDashboardView  {
    // Componenti UI
    private Button[] mainButtons;                // Array che conterrà i pulsanti principali della dashboard.
    private BorderPane layout;                   // Layout principale della view, organizzato in regioni (top, center, bottom).

    // Controller:
    // Il controller applicativo gestisce la logica (in questo caso, verifica l'autenticazione).
    // Il controller grafico gestisce le interazioni e le transizioni tra le viste.
    private SpecialistDashboardControllerApp appController;
    private SpecialistDashboardGraphicController graphicController;

    // Il metodo start è il punto di ingresso per una applicazione JavaFX.

    public void start(Stage primaryStage) {
        // Inizializzo i controller.
        // Il controller applicativo viene creato e riceve questa view come riferimento.
        appController = new SpecialistDashboardControllerApp(this, primaryStage);
        // Il controller grafico viene creato e riceve lo stage principale e la view.
        graphicController = new SpecialistDashboardGraphicController(primaryStage, this);

        // Creazione dei componenti grafici e organizzazione del layout.
        createUIComponents();    // Crea i pulsanti principali.
        setupLayout();           // Imposta il layout complessivo (header, centro, footer).
        setupEventHandlers();    // Associa gli eventi (click) dei pulsanti ai metodi del controller applicativo.

        // Creazione della scena con il layout e applicazione del file CSS per lo styling.
        Scene scene = new Scene(layout);  // Crea la scena usando il layout come root.
        // Carica il file CSS dal percorso specificato, lancia un'eccezione se non viene trovato.
        String css = Objects.requireNonNull(getClass().getResource("/style/style_doctor_dashboard.css")).toExternalForm();
        scene.getStylesheets().add(css);  // Applica il CSS alla scena.

        // Configurazione dello stage (la finestra principale).
        primaryStage.setScene(scene);         // Imposta la scena sullo stage.
        primaryStage.setFullScreen(true);       // Imposta la finestra in modalità a schermo intero.
        primaryStage.setResizable(false);       // Disabilita il ridimensionamento della finestra.
        primaryStage.setTitle("Interfaccia Specialista"); // Imposta il titolo della finestra.
        primaryStage.show();                    // Mostra la finestra all'utente.
    }

    // Metodo per creare i componenti UI (in questo caso, i pulsanti della dashboard).
    private void createUIComponents() {
        mainButtons = new Button[]{
                createButton("Visualizza Dati Pazienti"),         // Pulsante per visualizzare i dati dei pazienti.
                createButton("Visualizza Visite della Settimana"),  // Pulsante per visualizzare le visite della settimana.
                createButton("Visualizza Tutte le Visite"),         // Pulsante per visualizzare tutte le visite.
                createButton("Registra Nuova Visita")               // Pulsante per registrare una nuova visita.
        };
    }

    // Metodo helper per creare un bottone standard con il testo specificato.
    private Button createButton(String text) {
        Button button = new Button(text);                // Crea un nuovo pulsante con il testo fornito.
        button.getStyleClass().add("button");             // Aggiunge la classe CSS "button" per lo styling.
        return button;                                    // Restituisce il pulsante creato.
    }

    // Configurazione del layout principale usando un BorderPane.
    private void setupLayout() {
        layout = new BorderPane();                        // Crea un nuovo BorderPane.
        layout.setId("main-container");                   // Imposta l'ID CSS per lo styling del contenitore principale.

        // Imposta le diverse sezioni del BorderPane:
        layout.setTop(createHeader());                    // Imposta l'header (parte superiore).
        layout.setCenter(createButtonContainer());        // Imposta il contenitore centrale per i pulsanti.
        layout.setBottom(createFooter());                 // Imposta il footer (parte inferiore), con il pulsante di logout.
    }

    // Creazione dell'header contenente il titolo della dashboard.
    private HBox createHeader() {
        Text headerText = new Text("Interfaccia Specialista"); // Crea un nodo di testo con il titolo.
        headerText.getStyleClass().add("header-text");           // Applica lo stile CSS per il testo dell'header.

        HBox header = new HBox(headerText);              // Crea un HBox per contenere il titolo.
        header.setAlignment(Pos.CENTER);                 // Allinea il titolo al centro orizzontalmente.
        header.setPadding(new Insets(20));               // Imposta un padding di 20px attorno all'header.
        header.setId("header");                          // Imposta l'ID CSS per lo styling dell'header.

        return header;                                   // Restituisce l'header creato.
    }

    // Creazione del contenitore centrale (VBox) che organizza i pulsanti in righe.
    private VBox createButtonContainer() {
        VBox container = new VBox(20);                   // Crea un VBox con uno spazio di 20px tra i figli.
        container.setAlignment(Pos.CENTER);              // Centra gli elementi all'interno del VBox.
        container.setPadding(new Insets(20));            // Imposta un padding di 20px attorno al contenitore.
        container.setId("button-container");             // Imposta l'ID CSS per lo styling del contenitore.

        // Organizza i pulsanti in righe:
        HBox row1 = createButtonRow(mainButtons[0]);     // Prima riga: contiene il primo pulsante.
        HBox row2 = createButtonRow(mainButtons[1], mainButtons[2]); // Seconda riga: contiene il secondo e il terzo pulsante.
        HBox row3 = createButtonRow(mainButtons[3]);     // Terza riga: contiene il quarto pulsante.

        container.getChildren().addAll(row1, row2, row3); // Aggiunge le righe al VBox.
        return container;                                // Restituisce il contenitore dei pulsanti.
    }

    // Metodo helper per creare una riga (HBox) contenente i pulsanti passati come argomenti.
    private HBox createButtonRow(Button... buttons) {
        HBox row = new HBox(20, buttons);                // Crea un HBox con 20px di spaziatura tra i pulsanti.
        row.setAlignment(Pos.CENTER);                    // Allinea i pulsanti al centro orizzontalmente.
        row.setPadding(new Insets(10));                  // Imposta un padding di 10px attorno alla riga.
        return row;                                      // Restituisce la riga creata.
    }

    // Creazione del footer contenente il pulsante di logout.
    private HBox createFooter() {
        Button logoutButton = createButton("Log Out");   // Crea un pulsante per il logout.
        logoutButton.getStyleClass().add("logout-button"); // Aggiunge la classe CSS "logout-button" per lo styling specifico.

        // Imposta l'azione da eseguire al click sul pulsante di logout:
        // L'evento viene delegato al controller applicativo, che gestisce il logout.
// Imposta l'azione da eseguire al click: passa lo Stage corrente ottenuto dal pulsante
        logoutButton.setOnAction(event -> {
            // Recupera lo Stage dalla finestra corrente (dal pulsante)
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            // Chiama il metodo handleLogout passando lo Stage
            appController.handleLogout(currentStage);
        });

        HBox footer = new HBox(logoutButton);            // Crea un HBox contenente il pulsante di logout.
        footer.setAlignment(Pos.CENTER);                 // Centra il pulsante all'interno del footer.
        footer.setPadding(new Insets(20));               // Imposta un padding di 20px attorno al footer.
        footer.setId("footer");                          // Imposta l'ID CSS per lo styling del footer.

        return footer;                                   // Restituisce il footer creato.
    }

    // Associa gli eventi dei pulsanti agli handler definiti nel controller applicativo.
    // Rimuovi questo metodo dalla view
    private void setupEventHandlers() {
        // Imposta l'azione per ciascun pulsante:
        mainButtons[0].setOnAction(event -> graphicController.handlePatientDataQuery());
        mainButtons[1].setOnAction(event -> graphicController.handleWeeklyVisits());
        mainButtons[2].setOnAction(event -> graphicController.handleAllVisits());
        mainButtons[3].setOnAction(event -> graphicController.handleNewVisit());
    }

    // Metodo per mostrare una view dinamica (finestra) con i risultati di una query.
    public void showDynamicView(String title, String description, List<String> content) {
        // Utilizza la classe DynamicQueryView per mostrare una nuova finestra.
        // Il callback passato permette di ripristinare la visibilità della view principale
        // quando la finestra dinamica viene chiusa.
        DynamicQueryView.show(new Stage(), title, description, content, () -> {
            graphicController.showMainView();  // Mostra la view principale dopo la chiusura.
        });
    }

    // Metodo per mostrare un alert di errore all'utente.
    public void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Crea un alert di tipo errore.
        alert.setTitle(title);                           // Imposta il titolo dell'alert.
        alert.setHeaderText(null);                       // Nessun header per l'alert.
        alert.setContentText(message);                   // Imposta il messaggio di errore.
        alert.showAndWait();                             // Mostra l'alert e attende la risposta dell'utente.
    }

}
