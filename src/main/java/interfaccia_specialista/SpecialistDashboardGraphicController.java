package interfaccia_specialista;

import javafx.stage.Stage;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.ArrayList;
import startupconfig.StartupSettingsEntity;
import storage_db.DatabaseStorageStrategyPaziente;
import storage_liste.ListaVisite;
import storage_liste.ListaPazienti;
import storage_file.FileManagerPazienti;
import storage_file.FileManagerVisite; // Aggiunto per gestire le visite nel file system.
import storage_db.DatabaseStorageStrategyVisita; // Aggiunto per gestire le visite nel database.
import model.Visita;
import model.Paziente;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/**
 * Controller grafico per la Dashboard dello Specialista.
 */
public class SpecialistDashboardGraphicController {
    private final Stage primaryStage; // Lo stage principale dell'applicazione.
    private final SpecialistDashboardView view; // La view associata a questo controller.
    private final FileManagerPazienti fileManagerPazienti; // Gestore per salvare/caricare pazienti su file.
    private final FileManagerVisite fileManagerVisite; // Gestore per salvare/caricare visite su file.
    private final DatabaseStorageStrategyVisita databaseStorageVisita; // Gestore per salvare/caricare visite nel database.

    /**
     * Costruttore del controller grafico.
     * @param primaryStage Lo stage principale dell'applicazione.
     * @param view La view associata a questo controller.
     */
    public SpecialistDashboardGraphicController(Stage primaryStage, SpecialistDashboardView view) {
        this.primaryStage = primaryStage; // Inizializza lo stage principale.
        this.view = view; // Inizializza la view.
        this.fileManagerPazienti = new FileManagerPazienti(); // Inizializza il gestore dei file per i pazienti.
        this.fileManagerVisite = new FileManagerVisite(); // Inizializza il gestore dei file per le visite.
        this.databaseStorageVisita = new DatabaseStorageStrategyVisita(); // Inizializza il gestore del database per le visite.
    }

    /**
     * Mostra la view principale (rimette in primo piano la finestra principale).
     */
    public void showMainView() {
        primaryStage.show(); // Mostra lo stage principale.
    }

    /**
     * Gestisce la visualizzazione dei dati dei pazienti.
     */
    public void handlePatientDataQuery() {
        int storageOption = StartupSettingsEntity.getInstance().getStorageOption(); // Ottieni l'opzione di storage corrente.
        List<String> results = new ArrayList<>(); // Lista per memorizzare i risultati da mostrare.

        if (storageOption == 0) {
            // Controllo per Lista (RAM)
            ObservableList<Paziente> observablePazienti = ListaPazienti.getIstanzaListaPazienti().getObservableListaPazienti();
            if (observablePazienti.isEmpty()) {
                results.add("Nessun paziente registrato."); // Nessun paziente nella lista.
            } else {
                observablePazienti.forEach(paziente -> results.add(paziente.toString())); // Aggiungi ogni paziente alla lista.
            }
        } else if (storageOption == 1) {
            // Controllo per Database
            // Recupera tutti i pazienti dal database.
            List<Paziente> pazienti = new DatabaseStorageStrategyPaziente().getAllPazienti();
            if (pazienti.isEmpty()) {
                results.add("Nessun paziente trovato nel database.");
            } else {
                pazienti.forEach(paziente -> results.add(paziente.toString()));
            }
        } else if (storageOption == 2) {
            // Controllo per File Manager
            List<Paziente> pazientiSalvati = fileManagerPazienti.trovaTutti(); // Recupera tutti i pazienti salvati su file.
            if (pazientiSalvati.isEmpty()) {
                results.add("Nessun paziente salvato su file."); // Nessun paziente trovato nei file.
            } else {
                pazientiSalvati.forEach(paziente -> results.add(paziente.toString())); // Aggiungi ogni paziente alla lista.
            }
        } else {
            results.add("Nessun dato disponibile."); // Opzione di storage non valida.
        }

        primaryStage.hide(); // Nasconde lo stage principale.
        Stage dynamicStage = new Stage(); // Crea un nuovo stage per la finestra dinamica.
        view.showDynamicView(
                "Dati Pazienti", // Titolo della finestra.
                "Elenco dei pazienti registrati nel sistema:", // Descrizione della query.
                results // Risultati da mostrare.
        );
    }

    /**
     * Gestisce la visualizzazione delle visite della settimana.
     */
    public void handleWeeklyVisits() {
        int storageOption = StartupSettingsEntity.getInstance().getStorageOption(); // Ottieni l'opzione di storage corrente.
        List<String> results = new ArrayList<>(); // Lista per memorizzare i risultati da mostrare.

        // Calcola l'inizio e la fine della settimana corrente.
        LocalDate oggi = LocalDate.now();
        LocalDate inizioSettimana = oggi.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate fineSettimana = oggi.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

        if (storageOption == 0) {
            // Controllo per Lista (RAM)
            ObservableList<Visita> observableVisite = ListaVisite.getIstanzaListaVisite().getObservableListaVisite();
            observableVisite.stream()
                    .filter(visita -> !visita.getData().isBefore(inizioSettimana) && !visita.getData().isAfter(fineSettimana))
                    .forEach(visita -> results.add(visita.toString()));
        } else if (storageOption == 1) {
            // Controllo per Database
            List<Visita> visite = databaseStorageVisita.getAllVisite(); // Recupera tutte le visite dal database.
            visite.stream()
                    .filter(visita -> !visita.getData().isBefore(inizioSettimana) && !visita.getData().isAfter(fineSettimana))
                    .forEach(visita -> results.add(visita.toString()));
        } else if (storageOption == 2) {
            // Controllo per File Manager
            List<Visita> visite = fileManagerVisite.trovaPerPaziente(""); // Recupera tutte le visite dal file system.
            visite.stream()
                    .filter(visita -> !visita.getData().isBefore(inizioSettimana) && !visita.getData().isAfter(fineSettimana))
                    .forEach(visita -> results.add(visita.toString()));
        } else {
            results.add("Nessun dato disponibile."); // Opzione di storage non valida.
        }

        if (results.isEmpty()) {
            results.add("Nessuna visita registrata per questa settimana."); // Nessuna visita trovata.
        }

        primaryStage.hide(); // Nasconde lo stage principale.
        Stage dynamicStage = new Stage(); // Crea un nuovo stage per la finestra dinamica.
        view.showDynamicView(
                "Visite della Settimana", // Titolo della finestra.
                "Elenco delle visite programmate per questa settimana:", // Descrizione della query.
                results // Risultati da mostrare.
        );
    }

    /**
     * Gestisce la visualizzazione di tutte le visite.
     */
    public void handleAllVisits() {
        int storageOption = StartupSettingsEntity.getInstance().getStorageOption(); // Ottieni l'opzione di storage corrente.
        List<String> results = new ArrayList<>(); // Lista per memorizzare i risultati da mostrare.

        if (storageOption == 0) {
            // Controllo per Lista (RAM)
            ObservableList<Visita> observableVisite = ListaVisite.getIstanzaListaVisite().getObservableListaVisite();
            observableVisite.forEach(visita -> results.add(visita.toString())); // Aggiungi ogni visita alla lista.
        } else if (storageOption == 1) {
            // Controllo per Database
            List<Visita> visite = databaseStorageVisita.getAllVisite(); // Recupera tutte le visite dal database.
            visite.forEach(visita -> results.add(visita.toString())); // Aggiungi ogni visita alla lista.
        } else if (storageOption == 2) {
            // Controllo per File Manager
            List<Visita> visite = fileManagerVisite.trovaPerPaziente(""); // Recupera tutte le visite dal file system.
            visite.forEach(visita -> results.add(visita.toString())); // Aggiungi ogni visita alla lista.
        } else {
            results.add("Nessun dato disponibile."); // Opzione di storage non valida.
        }

        if (results.isEmpty()) {
            results.add("Nessuna visita registrata."); // Nessuna visita trovata.
        }

        primaryStage.hide(); // Nasconde lo stage principale.
        Stage dynamicStage = new Stage(); // Crea un nuovo stage per la finestra dinamica.
        view.showDynamicView(
                "Tutte le Visite", // Titolo della finestra.
                "Elenco di tutte le visite registrate nel sistema:", // Descrizione della query.
                results // Risultati da mostrare.
        );
    }

    /**
     * Gestisce la registrazione di una nuova visita.
     */
    public void handleNewVisit() {
        int storageOption = StartupSettingsEntity.getInstance().getStorageOption(); // Ottieni l'opzione di storage corrente.
        List<String> results = new ArrayList<>(); // Lista per memorizzare i risultati da mostrare.

        // Simula l'inserimento di una nuova visita (es. tramite form).
        Paziente paziente = new Paziente.Builder()
                .nome("Mario")
                .cognome("Rossi")
                .dataDiNascita(LocalDate.of(1985, 1, 1))
                .numeroTelefonico("1234567890")
                .email("mario.rossi@example.com")
                .codiceFiscalePaziente("RSSMRA85A01H501Z")
                .condizioniMediche("Nessuna")
                .password("password123")
                .build();

        Visita nuovaVisita = new Visita(
                paziente,
                LocalDate.now(),
                LocalTime.now(),
                "Dr. Mario Rossi",
                "Controllo Generale",
                "Check-up annuale",
                "Prenotata"
        );

        switch (storageOption) {
            case 0:
                // Controllo per Lista (RAM)
                ListaVisite.getIstanzaListaVisite().aggiungiVisita(nuovaVisita);
                results.add("Nuova visita salvata nella lista (RAM).");
                break;
            case 1:
                // Controllo per Database
                boolean successDB = databaseStorageVisita.salva(nuovaVisita);
                if (successDB) {
                    results.add("Nuova visita salvata nel database.");
                } else {
                    results.add("Errore durante il salvataggio della visita nel database.");
                }
                break;
            case 2:
                // Controllo per File Manager
                boolean successFile = fileManagerVisite.salva(nuovaVisita);
                if (successFile) {
                    results.add("Nuova visita salvata nel file system.");
                } else {
                    results.add("Errore durante il salvataggio della visita nel file system.");
                }
                break;
            default:
                results.add("Modulo: Nessun dato disponibile."); // Opzione di storage non valida.
        }

        primaryStage.hide(); // Nasconde lo stage principale.
        Stage dynamicStage = new Stage(); // Crea un nuovo stage per la finestra dinamica.
        view.showDynamicView(
                "Nuova Visita", // Titolo della finestra.
                "Dettagli della nuova visita registrata:", // Descrizione della query.
                results // Risultati da mostrare.
        );
    }

    /**
     * Gestisce il logout dell'utente.
     */
    public void handleLogout() {
        primaryStage.close(); // Chiude lo stage principale, terminando l'applicazione.
    }
}