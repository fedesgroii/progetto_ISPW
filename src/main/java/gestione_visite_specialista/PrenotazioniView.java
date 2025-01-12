package gestione_visite_specialista;

import Storage_DB.DatabaseConnection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrenotazioniView extends Application {

    private TableView<Visita> tabellaVisite;

    @Override
    public void start(Stage primaryStage) {
        // Creazione pulsante "Torna Indietro"
        Button btnTornaIndietro = new Button("Torna Indietro");
        btnTornaIndietro.setId("logoutButton"); // Riutilizzo lo stile per coerenza

        // Header
        Text headerText = new Text("Visite Prenotate");
        headerText.getStyleClass().add("header-text");

        HBox header = new HBox(headerText);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setId("header");

        // Creazione della tabella per visualizzare i dati
        tabellaVisite = new TableView<>();
        tabellaVisite.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Creazione delle colonne
        TableColumn<Visita, String> colNome = new TableColumn<>("Nome Paziente");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Visita, String> colCognome = new TableColumn<>("Cognome Paziente");
        colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));

        TableColumn<Visita, String> colData = new TableColumn<>("Data Visita");
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<Visita, String> colOra = new TableColumn<>("Ora Visita");
        colOra.setCellValueFactory(new PropertyValueFactory<>("ora"));

        TableColumn<Visita, Button> colElimina = new TableColumn<>("Azioni");
        colElimina.setCellValueFactory(new PropertyValueFactory<>("bottoneElimina"));

        // Aggiunta delle colonne alla tabella
        tabellaVisite.getColumns().addAll(colNome, colCognome, colData, colOra, colElimina);

        // Footer con il pulsante "Torna Indietro"
        HBox footer = new HBox(btnTornaIndietro);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.setId("footer");

        // Layout principale
        BorderPane layout = new BorderPane();
        layout.setTop(header);
        layout.setCenter(tabellaVisite);
        layout.setBottom(footer);
        layout.setId("main-container");

        // Scena
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/style_doctor_dashboard.css")).toExternalForm());

        primaryStage.setTitle("Visite Prenotate");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        // Azione pulsante "Torna Indietro"
        btnTornaIndietro.setOnAction(e -> primaryStage.close());

        // Caricamento dei dati dal database
        caricaDatiVisite();
    }

    private void caricaDatiVisite() {
        List<Visita> visite = new ArrayList<>();
        try {
            // Connessione al database e recupero dati
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT nome, cognome, data_visita, ora_visita FROM visite";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String data = rs.getString("data_visita");
                String ora = rs.getString("ora_visita");

                // Creazione di una visita con bottone elimina
                Visita visita = new Visita(nome, cognome, data, ora);
                visite.add(visita);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Aggiornamento tabella o visualizzazione messaggio se vuota
        if (visite.isEmpty()) {
            tabellaVisite.setPlaceholder(new Label("Nessuna visita prenotata"));
        } else {
            tabellaVisite.getItems().addAll(visite);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Classe per rappresentare una visita
    public static class Visita {
        private String nome;
        private String cognome;
        private String data;
        private String ora;
        private Button bottoneElimina;

        public Visita(String nome, String cognome, String data, String ora) {
            this.nome = nome;
            this.cognome = cognome;
            this.data = data;
            this.ora = ora;
            this.bottoneElimina = new Button("Elimina");
            this.bottoneElimina.setOnAction(e -> {
                // Logica per eliminare la visita
                System.out.println("Eliminata visita di " + nome + " " + cognome);
            });
        }

        // Getter e Setter
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getCognome() { return cognome; }
        public void setCognome(String cognome) { this.cognome = cognome; }

        public String getData() { return data; }
        public void setData(String data) { this.data = data; }

        public String getOra() { return ora; }
        public void setOra(String ora) { this.ora = ora; }

        public Button getBottoneElimina() { return bottoneElimina; }
        public void setBottoneElimina(Button bottoneElimina) { this.bottoneElimina = bottoneElimina; }
    }
}
