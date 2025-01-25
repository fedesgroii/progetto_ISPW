package prenotazione_visita;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PrenotazioneVisitaView extends Application {
    String inputField = "inputField";
    @Override
    public void start(Stage primaryStage) {
        // Layout principale
        VBox mainLayout = new VBox(20); // Spaziatura verticale
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        // Titolo principale
        Label titleLabel = new Label("Prenotazione Visita");
        titleLabel.setId("title");

        // Sottotitolo sopra il form
        Label subtitleLabel = new Label("Completa tutti i campi");
        subtitleLabel.setId("subtitle");

        // Layout per il form
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Etichetta e campo per la data della visita
        Label dateLabel = new Label("Data della visita:");
        DatePicker datePicker = new DatePicker();
        datePicker.setId(inputField);
        gridPane.add(dateLabel, 0, 0);
        gridPane.add(datePicker, 1, 0);

        // Etichetta e menu a discesa per l'orario della visita
        Label timeLabel = new Label("Orario della visita:");
        ComboBox<String> timeComboBox = new ComboBox<>();
        timeComboBox.setPromptText("Seleziona un orario");
        timeComboBox.setDisable(true); // Disabilitato inizialmente
        timeComboBox.setId(inputField);
        gridPane.add(timeLabel, 0, 1);
        gridPane.add(timeComboBox, 1, 1);

        // Popolamento degli orari disponibili (da aggiornare dinamicamente in base al database)
        List<String> orariDisponibili = Arrays.asList("09:00", "10:00", "11:00", "14:00", "15:00", "16:00");

        // Abilita la selezione dell'orario solo dopo aver selezionato una data
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                timeComboBox.setDisable(false);
                timeComboBox.getItems().clear();
                timeComboBox.getItems().addAll(orariDisponibili);
            } else {
                timeComboBox.setDisable(true);
                timeComboBox.getItems().clear();
            }
        });

        // Etichetta e menu a discesa per lo specialista
        Label specialistLabel = new Label("Specialista:");
        ComboBox<String> specialistComboBox = new ComboBox<>();
        specialistComboBox.setPromptText("Seleziona uno specialista");
        specialistComboBox.setId(inputField);
        // Placeholder per gli specialisti (da popolare dinamicamente)
        List<String> specialisti = Arrays.asList("Dr. Rossi", "Dr.ssa Bianchi", "Dr. Verdi");
        specialistComboBox.getItems().addAll(specialisti);
        gridPane.add(specialistLabel, 0, 2);
        gridPane.add(specialistComboBox, 1, 2);

        // Etichetta e menu a discesa per il tipo di visita
        Label tipoVisitaLabel = new Label("Tipo di visita:");
        ComboBox<String> tipoVisitaComboBox = new ComboBox<>();
        tipoVisitaComboBox.setPromptText("Seleziona il tipo di visita");
        tipoVisitaComboBox.getItems().addAll("Logopedica", "Psicologica");
        tipoVisitaComboBox.setId(inputField);
        gridPane.add(tipoVisitaLabel, 0, 3);
        gridPane.add(tipoVisitaComboBox, 1, 3);

        // Etichetta e campo di testo per il motivo della visita
        Label motivoLabel = new Label("Motivo della visita:");
        TextField motivoTextField = new TextField();
        motivoTextField.setId(inputField);
        gridPane.add(motivoLabel, 0, 4);
        gridPane.add(motivoTextField, 1, 4);

        // Bottone per confermare la prenotazione
        Button submitButton = new Button("Prenota");
        submitButton.setId("specialistButton");
        gridPane.add(submitButton, 1, 5);

        // Bottone "Torna indietro"
        Button backButton = new Button("Torna indietro");
        backButton.setOnAction(event -> {

            primaryStage.close(); // Chiudi la finestra corrente
        });

        // Azione sul click del bottone
        submitButton.setOnAction(event -> {
            LocalDate data = datePicker.getValue();
            String orario = timeComboBox.getValue();
            String specialista = specialistComboBox.getValue();
            String tipoVisita = tipoVisitaComboBox.getValue();
            String motivo = motivoTextField.getText();

            if (data == null || orario == null || specialista == null || tipoVisita == null || motivo.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Tutti i campi devono essere compilati.");
                alert.showAndWait();
            } else {
                LocalDateTime dataVisita = LocalDateTime.parse(data + "T" + orario + ":00");
                System.out.println("Prenotazione confermata:\n" +
                        "Data: " + dataVisita + "\n" +
                        "Specialista: " + specialista + "\n" +
                        "Tipo Visita: " + tipoVisita + "\n" +
                        "Motivo: " + motivo);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Conferma");
                alert.setHeaderText(null);
                alert.setContentText("Prenotazione effettuata con successo!");
                alert.showAndWait();
            }
        });

        // Aggiunta degli elementi al layout principale
        mainLayout.getChildren().addAll(titleLabel, subtitleLabel, gridPane, backButton);

        // Configurazione della scena e avvio
        Scene scene = new Scene(mainLayout, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/style/style_prenotazione_visita_view_a_colori.css").toExternalForm());
        primaryStage.setTitle("Prenotazione Visita");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
