package storage_liste;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Visita;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class ListaVisite {
    private static final Logger logger = Logger.getLogger(ListaVisite.class.getName());
    // Lista osservabile di visite (utile per JavaFX)
    private final ObservableList<Visita> observableListaVisite;

    // Variabile statica per contenere l'unica istanza della classe (Singleton)
    private static ListaVisite istanzaListaVisite;

    // Costruttore privato per evitare istanziazioni multiple
    private ListaVisite() {
        observableListaVisite = FXCollections.observableArrayList(); // Usando ObservableList
    }

    // Metodo statico per ottenere l'unica istanza della classe
    public static ListaVisite getIstanzaListaVisite() {
        if (istanzaListaVisite == null) {
            istanzaListaVisite = new ListaVisite(); // Crea l'istanza solo se non esiste già
        }
        return istanzaListaVisite;
    }

    // Metodo per aggiungere una visita alla lista
    public void aggiungiVisita(Visita visita) {
        observableListaVisite.add(visita);
    }

    // Metodo per rimuovere una visita dalla lista (per codice fiscale del paziente e data)
    public boolean rimuoviVisita(String codiceFiscale, String data, String orario) {
        for (Visita visita : observableListaVisite) {
            if (visita.getPaziente().getCodiceFiscalePaziente().equals(codiceFiscale) &&
                    visita.getData().toString().equals(data) && visita.getOrario().toString().equals(orario)) {
                observableListaVisite.remove(visita);
                return true;
            }
        }
        return false; // Visita non trovata
    }

    // Metodo per visualizzare la lista di visite
    public void visualizzaVisite() {
        if (observableListaVisite.isEmpty()) {
            logger.info("Nessuna visita registrata.");
        } else {
            for (Visita visita : observableListaVisite) {
                logger.info((Supplier<String>) visita);
            }
        }
    }

    // Metodo per trovare una visita per codice fiscale, data e orario
    public Visita trovaVisita(String codiceFiscale, String data, String orario) {
        for (Visita visita : observableListaVisite) {
            if (visita.getPaziente().getCodiceFiscalePaziente().equals(codiceFiscale) &&
                    visita.getData().toString().equals(data) && visita.getOrario().toString().equals(orario)) {
                return visita;
            }
        }
        return null; // Visita non trovata
    }

    // Metodo per ottenere la lista osservabile di visite
    public ObservableList<Visita> getObservableListaVisite() {
        return observableListaVisite;
    }
}
