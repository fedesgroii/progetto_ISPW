package storage_liste;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Visita;

import java.util.Optional;
import java.util.logging.Logger;

public class ListaVisite {
    private static final Logger logger = Logger.getLogger(ListaVisite.class.getName());
    private final ObservableList<Visita> observableListaVisite;

    // Variabile volatile per garantire la visibilità tra i thread
    private static volatile ListaVisite istanzaListaVisite;

    // Costruttore privato per impedire istanziazioni esterne
    private ListaVisite() {
        observableListaVisite = FXCollections.observableArrayList();
    }

    // Metodo per ottenere l'istanza con "double-checked locking"
    public static ListaVisite getIstanzaListaVisite() {
        if (istanzaListaVisite == null) {
            synchronized (ListaVisite.class) {
                if (istanzaListaVisite == null) {
                    istanzaListaVisite = new ListaVisite();
                }
            }
        }
        return istanzaListaVisite;
    }

    // Metodo per aggiungere una visita alla lista
    public void aggiungiVisita(Visita visita) {
        if (visita == null) {
            logger.warning("Tentativo di aggiungere una visita nulla.");
            return;
        }
        observableListaVisite.add(visita);
    }

    // Metodo per rimuovere una visita (identificata da codice fiscale, data e orario)
    public boolean rimuoviVisita(String codiceFiscale, String data, String orario) {
        if (codiceFiscale == null || data == null || orario == null) {
            logger.warning("Dati non validi per la rimozione della visita.");
            return false;
        }
        return observableListaVisite.removeIf(visita ->
                visita.getPaziente().getCodiceFiscalePaziente().equalsIgnoreCase(codiceFiscale) &&
                        visita.getData().toString().equals(data) &&
                        visita.getOrario().toString().equals(orario));
    }

    // Metodo per visualizzare la lista di visite
    public void visualizzaVisite() {
        if (observableListaVisite.isEmpty()) {
            logger.info("Nessuna visita registrata.");
        } else {
            for (Visita visita : observableListaVisite) {
                logger.info(visita.toString());
            }
        }
    }

    // Metodo per trovare una visita per codice fiscale, data e orario
    public Optional<Visita> trovaVisita(String codiceFiscale, String data, String orario) {
        if (codiceFiscale == null || data == null || orario == null) {
            logger.warning("Dati non validi per la ricerca della visita.");
            return Optional.empty();
        }
        return observableListaVisite.stream()
                .filter(visita -> visita.getPaziente().getCodiceFiscalePaziente().equalsIgnoreCase(codiceFiscale) &&
                        visita.getData().toString().equals(data) &&
                        visita.getOrario().toString().equals(orario))
                .findFirst();
    }

    // Metodo per ottenere la lista osservabile di visite
    public ObservableList<Visita> getObservableListaVisite() {
        return observableListaVisite;
    }
}
