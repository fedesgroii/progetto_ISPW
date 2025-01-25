package debug;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Paziente;
import storage_liste.ListaPazienti;

import java.time.LocalDate;

public class GestionePazientiApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ObservableList<Paziente> listaPazienti = ListaPazienti.getIstanzaListaPazienti().getObservableListaPazienti();

        TableView<Paziente> tableView = new TableView<>(listaPazienti);

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

        tableView.getColumns().addAll(nomeCol, cognomeCol, dataNascitaCol, telefonoCol, emailCol, codiceFiscaleCol, condizioniMedicheCol);

        TextField nomeField = new TextField();
        TextField cognomeField = new TextField();
        DatePicker dataNascitaField = new DatePicker();
        TextField telefonoField = new TextField();
        TextField emailField = new TextField();
        TextField codiceFiscaleField = new TextField();
        TextField condizioniMedicheField = new TextField();

        Button aggiungiButton = new Button("Aggiungi");
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
                    Paziente nuovoPaziente = new Paziente.Builder()
                            .nome(nome)
                            .cognome(cognome)
                            .dataDiNascita(LocalDate.parse(dataNascita.toString()))
                            .numeroTelefonico(telefono)
                            .email(email)
                            .codiceFiscalePaziente(codiceFiscale)
                            .condizioniMediche(condizioniMediche)
                            .build();

                    listaPazienti.add(nuovoPaziente);
                    clearFields(nomeField, cognomeField, telefonoField, emailField, codiceFiscaleField, condizioniMedicheField);
                    dataNascitaField.setValue(null);
                } else {
                    showAlert("Errore", "Tutti i campi devono essere compilati.");
                }
            } catch (Exception ex) {
                showAlert("Errore", "Errore nell'aggiunta del paziente: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10, tableView, nomeField, cognomeField, dataNascitaField, telefonoField, emailField, codiceFiscaleField, condizioniMedicheField, aggiungiButton);
        layout.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(layout, 800, 600));
        primaryStage.show();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
