package storage_liste;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Specialista;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class ListaSpecialisti {
    private static final Logger logger = Logger.getLogger(ListaSpecialisti.class.getName());
    // Lista osservabile di specialisti (utile per JavaFX)
    private final ObservableList<Specialista> observableListaSpecialisti;

    // Variabile statica per contenere l'unica istanza della classe (Singleton)
    private static ListaSpecialisti istanzaListaSpecialisti;

    // Costruttore privato per evitare istanziazioni multiple
    private ListaSpecialisti() {
        observableListaSpecialisti = FXCollections.observableArrayList(); // Usando ObservableList
    }

    // Metodo statico per ottenere l'unica istanza della classe
    public static ListaSpecialisti getIstanzaListaSpecialisti() {
        if (istanzaListaSpecialisti == null) {
            istanzaListaSpecialisti = new ListaSpecialisti(); // Crea l'istanza solo se non esiste già
        }
        return istanzaListaSpecialisti;
    }

    // Metodo per aggiungere uno specialista alla lista
    public void aggiungiSpecialista(Specialista specialista) {
        observableListaSpecialisti.add(specialista);
    }

    // Metodo per rimuovere uno specialista dalla lista (per email)
    public boolean rimuoviSpecialista(String email) {
        for (Specialista specialista : observableListaSpecialisti) {
            if (specialista.getEmail().equalsIgnoreCase(email)) {
                observableListaSpecialisti.remove(specialista);
                return true;
            }
        }
        return false; // Specialista non trovato
    }

    // Metodo per visualizzare la lista di specialisti
    public void visualizzaSpecialisti() {
        if (observableListaSpecialisti.isEmpty()) {
            logger.info("Nessuno specialista registrato.");
        } else {
            for (Specialista specialista : observableListaSpecialisti) {
                logger.info((Supplier<String>) specialista);
            }
        }
    }

    // Metodo per trovare uno specialista per email
    public Specialista trovaSpecialista(String email) {
        for (Specialista specialista : observableListaSpecialisti) {
            if (specialista.getEmail().equalsIgnoreCase(email)) {
                return specialista;
            }
        }
        return null; // Specialista non trovato
    }

    // Metodo per ottenere la lista osservabile di specialisti
    public ObservableList<Specialista> getObservableListaSpecialisti() {
        return observableListaSpecialisti;
    }
}
