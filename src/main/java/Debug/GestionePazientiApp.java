package Debug;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utenti.Paziente;
import utenti.PazienteInfo;
import storage_liste.ListaPazienti;

import java.time.LocalDate;

public class GestionePazientiApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        String errore = "Errore";
        // Ottenere la lista osservabile
        ObservableList<Paziente> listaPazienti = ListaPazienti.getIstanzaListaPazienti().getObservableListaPazienti();

        // TableView per visualizzare i pazienti
        TableView<Paziente> tableView = new TableView<>();
        tableView.setItems(listaPazienti);

        // Colonne della tabella
        TableColumn<Paziente, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Paziente, String> cognomeCol = new TableColumn<>("Cognome");
        cognomeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCognome()));

        TableColumn<Paziente, String> dataNascitaCol = new TableColumn<>("Data di Nascita");
        dataNascitaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataDiNascita().toString()));

        TableColumn<Paziente, String> telefonoCol = new TableColumn<>("Telefono");
        telefonoCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNumeroTelefonico()));

        TableColumn<Paziente, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Paziente, String> codiceFiscaleCol = new TableColumn<>("Codice Fiscale");
        codiceFiscaleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodiceFiscalePaziente()));

        TableColumn<Paziente, String> condizioniMedicheCol = new TableColumn<>("Condizioni Mediche");
        condizioniMedicheCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCondizioniMediche()));

        // Aggiungi le colonne alla tabella
        tableView.getColumns().addAll(nomeCol, cognomeCol, dataNascitaCol, telefonoCol, emailCol, codiceFiscaleCol, condizioniMedicheCol);

        // Ascolta i cambiamenti della larghezza della TableView
        tableView.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double columnWidth = newWidth.doubleValue() / tableView.getColumns().size();
            for (TableColumn<Paziente, ?> col : tableView.getColumns()) {
                col.setPrefWidth(columnWidth);
            }
        });

        // Campi di input per aggiungere/aggiornare pazienti
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField cognomeField = new TextField();
        cognomeField.setPromptText("Cognome");

        DatePicker dataNascitaField = new DatePicker();
        dataNascitaField.setPromptText("Data di Nascita");

        TextField telefonoField = new TextField();
        telefonoField.setPromptText("Telefono");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField codiceFiscaleField = new TextField();
        codiceFiscaleField.setPromptText("Codice Fiscale");

        TextField condizioniMedicheField = new TextField();
        condizioniMedicheField.setPromptText("Condizioni Mediche");

        // Pulsanti
        Button aggiungiButton = new Button("Aggiungi");
        Button aggiornaButton = new Button("Aggiorna");
        Button rimuoviButton = new Button("Rimuovi");

        // Eventi dei pulsanti
        aggiungiButton.setOnAction(e -> {
            try {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                LocalDate dataNascita = dataNascitaField.getValue();
                String telefono = telefonoField.getText();
                String email = emailField.getText();
                String codiceFiscale = codiceFiscaleField.getText();
                String condizioniMediche = condizioniMedicheField.getText();

                if (!nome.isEmpty() && !cognome.isEmpty() && dataNascita != null && !telefono.isEmpty()
                        && !email.isEmpty() && !codiceFiscale.isEmpty() && !condizioniMediche.isEmpty()) {
                    PazienteInfo pazienteInfo = new PazienteInfo(nome, cognome, dataNascita, telefono, email, "",
                            codiceFiscale, condizioniMediche);
                    Paziente nuovoPaziente = new Paziente(pazienteInfo);
                    ListaPazienti.getIstanzaListaPazienti().aggiungiPaziente(nuovoPaziente);
                    clearFields(nomeField, cognomeField, telefonoField, emailField, codiceFiscaleField, condizioniMedicheField);
                    dataNascitaField.setValue(null);
                } else {
                    showAlert(errore, "Tutti i campi devono essere compilati.");
                }
            } catch (Exception ex) {
                showAlert(errore, "Errore nell'aggiunta del paziente: " + ex.getMessage());
            }
        });

        aggiornaButton.setOnAction(e -> {
            Paziente selezionato = tableView.getSelectionModel().getSelectedItem();
            if (selezionato != null) {
                selezionato.setNome(nomeField.getText());
                selezionato.setCognome(cognomeField.getText());
                selezionato.setDataDiNascita(dataNascitaField.getValue());
                selezionato.setNumeroTelefonico(telefonoField.getText());
                selezionato.setEmail(emailField.getText());
                selezionato.setCodiceFiscalePaziente(codiceFiscaleField.getText());
                selezionato.setCondizioniMediche(condizioniMedicheField.getText());
                tableView.refresh();
                clearFields(nomeField, cognomeField, telefonoField, emailField, codiceFiscaleField, condizioniMedicheField);
                dataNascitaField.setValue(null);
            } else {
                showAlert(errore, "Seleziona un paziente per aggiornare.");
            }
        });

        rimuoviButton.setOnAction(e -> {
            Paziente selezionato = tableView.getSelectionModel().getSelectedItem();
            if (selezionato != null) {
                ListaPazienti.getIstanzaListaPazienti().rimuoviPaziente(selezionato.getCodiceFiscalePaziente());
            } else {
                showAlert(errore, "Seleziona un paziente per rimuovere.");
            }
        });

        // Layout
        GridPane inputPane = new GridPane();
        inputPane.setPadding(new Insets(10));
        inputPane.setHgap(10);
        inputPane.setVgap(10);
        inputPane.add(new Label("Nome:"), 0, 0);
        inputPane.add(nomeField, 1, 0);
        inputPane.add(new Label("Cognome:"), 0, 1);
        inputPane.add(cognomeField, 1, 1);
        inputPane.add(new Label("Data di Nascita:"), 0, 2);
        inputPane.add(dataNascitaField, 1, 2);
        inputPane.add(new Label("Telefono:"), 0, 3);
        inputPane.add(telefonoField, 1, 3);
        inputPane.add(new Label("Email:"), 0, 4);
        inputPane.add(emailField, 1, 4);
        inputPane.add(new Label("Codice Fiscale:"), 0, 5);
        inputPane.add(codiceFiscaleField, 1, 5);
        inputPane.add(new Label("Condizioni Mediche:"), 0, 6);
        inputPane.add(condizioniMedicheField, 1, 6);
        inputPane.add(aggiungiButton, 0, 7);
        inputPane.add(aggiornaButton, 1, 7);
        inputPane.add(rimuoviButton, 2, 7);

        VBox root = new VBox(10, tableView, inputPane);
        root.setPadding(new Insets(10));

        // Configurazione della scena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestione Pazienti");
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/style/style_lista_visualizzazione_pazienti.css").toExternalForm());
        primaryStage.show();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String title, String message) {
        // Creazione dell'alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        // Mostra l'alert
        alert.showAndWait();
    }



    public static void main(String[] args) {
        launch(args);
    }
}