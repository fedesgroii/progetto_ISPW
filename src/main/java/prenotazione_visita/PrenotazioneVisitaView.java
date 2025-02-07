package prenotazione_visita;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PrenotazioneVisitaView extends Application {
    private DatePicker datePicker;
    private ComboBox<LocalTime> timeComboBox;
    private ComboBox<String> specialistComboBox;
    private ComboBox<String> tipoVisitaComboBox;
    private TextField motivoTextField;

    // Variabili per i messaggi di errore
    private Text errorTextDataVisita;
    private Text errorTextOrarioVisita;
    private Text errorTextSpecialista;
    private Text errorTextTipoVisita;
    private Text errorTextMotivoVisita;

    private final String inputField = "inputField"; // ID comune assegnato agli input del form

    @Override
    public void start(Stage primaryStage) {
        // Layout principale (VBox) con spaziatura verticale
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        // Titolo principale
        Label titleLabel = new Label("Prenotazione Visita");
        titleLabel.setId("title");

        // Sottotitolo per il form
        Label subtitleLabel = new Label("Completa tutti i campi");
        subtitleLabel.setId("subtitle");

        // Layout a griglia per organizzare il form
        GridPane gridPane = createFormGridPane();

        // Inizializza il controller con il layout della griglia
        PrenotazioneVisitaControllerApp controller = new PrenotazioneVisitaControllerApp(gridPane, this);

        // Bottone per confermare la prenotazione
        Button submitButton = createSubmitButton(controller);

        // Bottone per tornare indietro
        Button backButton = createBackButton(primaryStage);

        // Aggiunge gli elementi al layout principale
        mainLayout.getChildren().addAll(titleLabel, subtitleLabel, gridPane, submitButton, backButton);

        // Configura la scena e mostra la finestra
        Scene scene = new Scene(mainLayout, 600, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/style_prenotazione_visita_view_a_colori.css")).toExternalForm());
        primaryStage.setTitle("Prenotazione Visita");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setFullScreen(true);
        resetErrorMessages();
        /**
         * Aggiungi configurazione stile
         */
    }

    private GridPane createFormGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Elenco degli orari disponibili come LocalTime
        /**
         * Da rimuovere per integrare con DB
         */
        List<LocalTime> orariDisponibili = Arrays.asList(
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0)
        );
        // Creazione degli elementi del form
        datePicker = createDatePicker();
        timeComboBox = createTimeComboBox(orariDisponibili, datePicker);
        specialistComboBox = createComboBox("Specialista", Arrays.asList("Dr. Rossi", "Dr.ssa Bianchi", "Dr. Verdi"));
        tipoVisitaComboBox = createComboBox("Tipo di visita", Arrays.asList("Logopedica", "Psicologica"));
        motivoTextField = createTextField("Motivo della visita");

        // Creazione dei messaggi di errore per ogni campo
        errorTextDataVisita = createErrorText("Errore nella selezione della data, correggi");
        errorTextOrarioVisita = createErrorText("Errore nell'inserimento dell'orario, correggi");
        errorTextSpecialista = createErrorText("Errore nella selezione dello specialista, correggi");
        errorTextTipoVisita = createErrorText("Errore nella selezione del tipo di visita, correggi");
        errorTextMotivoVisita = createErrorText("Errore nell'inserimento del motivo della visita, correggi");

        // Aggiunta dei componenti al layout
        gridPane.add(createFieldWithError(datePicker, "Data della visita:", errorTextDataVisita), 0, 0);
        gridPane.add(createFieldWithError(timeComboBox, "Orario della visita:", errorTextOrarioVisita), 0, 1);
        gridPane.add(createFieldWithError(specialistComboBox, "Specialista:", errorTextSpecialista), 0, 2);
        gridPane.add(createFieldWithError(tipoVisitaComboBox, "Tipo di visita:", errorTextTipoVisita), 0, 3);
        gridPane.add(createFieldWithError(motivoTextField, "Motivo della visita:", errorTextMotivoVisita), 0, 4);

        return gridPane;
    }

    // Metodo per creare un gruppo di campo e messaggio di errore
    private VBox createFieldWithError(Control field, String labelText, Text errorText) {
        VBox vbox = new VBox(5);  // Spaziatura verticale tra il messaggio di errore e il campo
        Label label = new Label(labelText);
        vbox.getChildren().addAll(errorText, label, field);
        return vbox;
    }

    private DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setId(inputField);
        return datePicker;
    }

    private ComboBox<LocalTime> createTimeComboBox(List<LocalTime> orariDisponibili, DatePicker datePicker) {
        ComboBox<LocalTime> timeComboBox = new ComboBox<>();
        timeComboBox.setPromptText("Seleziona un orario");
        timeComboBox.setDisable(true);
        timeComboBox.setId(inputField);

        // Abilita la selezione dell'orario solo dopo aver scelto una data
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                timeComboBox.setDisable(false);
                timeComboBox.getItems().setAll(orariDisponibili);
            } else {
                timeComboBox.setDisable(true);
                timeComboBox.getItems().clear();
            }
        });

        // Imposta il rendering per mostrare gli orari in formato leggibile
        timeComboBox.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(LocalTime time) {
                return (time != null) ? time.toString() : "";
            }

            @Override
            public LocalTime fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalTime.parse(string) : null;
            }
        });

        return timeComboBox;
    }

    private ComboBox<String> createComboBox(String label, List<String> items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Seleziona " + label);
        comboBox.getItems().addAll(items);
        comboBox.setId(inputField);
        return comboBox;
    }

    private TextField createTextField(String label) {
        TextField textField = new TextField();
        textField.setId(inputField);
        return textField;
    }

    // Crea il testo di errore
    private Text createErrorText(String errorMessage) {
        Text errorText = new Text(errorMessage);
        errorText.setId("ERRORE");
        errorText.setVisible(false);  // inizialmente invisibile
        return errorText;
    }

    // Metodi per visualizzare o nascondere gli errori

    public void showErrorDataVisita() {
        errorTextDataVisita.setVisible(true);
    }

    public void hideErrorDataVisita() {
        errorTextDataVisita.setVisible(false);
    }

    public void showErrorOrarioVisita() {
        errorTextOrarioVisita.setVisible(true);
    }

    public void hideErrorOrarioVisita() {
        errorTextOrarioVisita.setVisible(false);
    }

    public void showErrorSpecialista() {
        errorTextSpecialista.setVisible(true);
    }

    public void hideErrorSpecialista() {
        errorTextSpecialista.setVisible(false);
    }

    public void showErrorTipoVisita() {
        errorTextTipoVisita.setVisible(true);
    }

    public void hideErrorTipoVisita() {
        errorTextTipoVisita.setVisible(false);
    }

    public void showErrorMotivoVisita() {
        errorTextMotivoVisita.setVisible(true);
    }

    public void hideErrorMotivoVisita() {
        errorTextMotivoVisita.setVisible(false);
    }

    private void resetErrorMessages() {
        hideErrorDataVisita();
        hideErrorOrarioVisita();
        hideErrorSpecialista();
        hideErrorTipoVisita();
        hideErrorMotivoVisita();
    }


    private Button createSubmitButton(PrenotazioneVisitaControllerApp controller) {
        Button submitButton = new Button("Prenota");
        submitButton.setId("specialistButton");

        // Collega il bottone al metodo del controller
        submitButton.setOnAction(event -> {
            PrenotazioneVisitaBean bean = createPrenotazioneBean();
            controller.isValidInputPrenotazione(bean); // Chiama il metodo del controller
        });

        return submitButton;
    }

    private Button createBackButton(Stage primaryStage) {
        Button backButton = new Button("Torna indietro");
        backButton.setOnAction(event -> primaryStage.close());
        return backButton;
    }

    private PrenotazioneVisitaBean createPrenotazioneBean() {
        PrenotazioneVisitaBean bean = new PrenotazioneVisitaBean();
        bean.setDataVisita(datePicker.getValue());
        bean.setOrarioVisita(timeComboBox.getValue());
        bean.setSpecialista(specialistComboBox.getValue());
        bean.setTipoVisita(tipoVisitaComboBox.getValue());
        bean.setMotivoVisita(motivoTextField.getText());
        return bean;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
