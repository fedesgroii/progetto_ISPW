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

    // Istanza Singleton dichiarata volatile per garantire visibilità tra thread
    private static volatile ListaSpecialisti istanzaListaSpecialisti;

    // Costruttore privato per evitare istanziazioni multiple
    private ListaSpecialisti() {
        observableListaSpecialisti = FXCollections.observableArrayList();
    }

    // Metodo per ottenere l'istanza Singleton con double-checked locking
    public static ListaSpecialisti getIstanzaListaSpecialisti() {
        if (istanzaListaSpecialisti == null) {
            synchronized (ListaSpecialisti.class) {
                if (istanzaListaSpecialisti == null) {
                    istanzaListaSpecialisti = new ListaSpecialisti();
                }
            }
        }
        return istanzaListaSpecialisti;
    }

    // Metodo per aggiungere uno specialista alla lista
    public void aggiungiSpecialista(Specialista specialista) {
        observableListaSpecialisti.add(specialista);
    }

    // Metodo per rimuovere uno specialista dalla lista (per email)
    public boolean rimuoviSpecialista(String email) {
        return observableListaSpecialisti.removeIf(s -> s.getEmail().equalsIgnoreCase(email));
    }

    // Metodo per visualizzare la lista di specialisti
    public void visualizzaSpecialisti() {
        if (observableListaSpecialisti.isEmpty()) {
            logger.info("Nessuno specialista registrato.");
        } else {
            observableListaSpecialisti.forEach(specialista -> logger.info((Supplier<String>) specialista));
        }
    }

    // Metodo per trovare uno specialista per email
    public Specialista trovaSpecialista(String email) {
        return observableListaSpecialisti.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // Metodo per ottenere la lista osservabile di specialisti
    public ObservableList<Specialista> getObservableListaSpecialisti() {
        return observableListaSpecialisti;
    }
}
