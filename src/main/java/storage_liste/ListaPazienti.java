package storage_liste;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utenti.Paziente;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class ListaPazienti {
    private static final Logger logger = Logger.getLogger(ListaPazienti.class.getName());
    // Lista osservabile di pazienti (utile per JavaFX)
    private final ObservableList<Paziente> ObservableListaPazienti;

    // Variabile statica per contenere l'unica istanza della classe (Singleton)
    private static ListaPazienti istanzaListaPazienti;

    // Costruttore privato per evitare istanziazioni multiple
    private ListaPazienti() {
        ObservableListaPazienti = FXCollections.observableArrayList(); // Usando ObservableList
    }

    // Metodo statico per ottenere l'unica istanza della classe
    public static ListaPazienti getIstanzaListaPazienti() {
        if (istanzaListaPazienti == null) {
            istanzaListaPazienti = new ListaPazienti(); // Crea l'istanza solo se non esiste già
        }
        return istanzaListaPazienti;
    }

    // Metodo per aggiungere un paziente alla lista
    public void aggiungiPaziente(Paziente paziente) {
        ObservableListaPazienti.add(paziente);
    }

    // Metodo per rimuovere un paziente dalla lista (per codice fiscale)
    public boolean rimuoviPaziente(String codiceFiscale) {
        for (Paziente paziente : ObservableListaPazienti) {
            if (paziente.getCodiceFiscalePaziente().equals(codiceFiscale)) {
                ObservableListaPazienti.remove(paziente);
                return true;
            }
        }
        return false; // Paziente non trovato
    }


    // Metodo per visualizzare la lista di pazienti
    public void visualizzaPazienti() {
        if (ObservableListaPazienti.isEmpty()) {
            logger.info("Nessun paziente registrato.");
        } else {
            for (Paziente paziente : ObservableListaPazienti) {
                logger.info((Supplier<String>) paziente);
            }
        }
    }


    // Metodo per trovare un paziente per codice fiscale
    public Paziente trovaPaziente(String codiceFiscale) {
        for (Paziente paziente : ObservableListaPazienti) {
            if (paziente.getCodiceFiscalePaziente().equals(codiceFiscale)) {
                return paziente;
            }
        }
        return null; // Paziente non trovato
    }
// **Metodo per ottenere la lista osservabile di pazienti**
    public ObservableList<Paziente> getObservableListaPazienti() {
        return ObservableListaPazienti;
    }
}
