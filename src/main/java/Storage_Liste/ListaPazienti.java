package Storage_Liste;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utenti.Paziente;

/*
TableView<Paziente> tableView = new TableView<>();
tableView.setItems(ListaPazienti.getIstanzaListaPazienti().getListaPazienti());
 */
public class ListaPazienti {

    // Lista osservabile di pazienti (utile per JavaFX)
    private ObservableList<Paziente> listaPazienti;

    // Variabile statica per contenere l'unica istanza della classe (Singleton)
    private static ListaPazienti istanza_lista_pazienti;

    // Costruttore privato per evitare istanziazioni multiple
    private ListaPazienti() {
        listaPazienti = FXCollections.observableArrayList(); // Usando ObservableList
    }

    // Metodo statico per ottenere l'unica istanza della classe
    public static ListaPazienti getIstanzaListaPazienti() {
        if (istanza_lista_pazienti == null) {
            istanza_lista_pazienti = new ListaPazienti(); // Crea l'istanza solo se non esiste già
        }
        return istanza_lista_pazienti;
    }

    // Metodo per aggiungere un paziente alla lista
    public void aggiungiPaziente(Paziente paziente) {
        listaPazienti.add(paziente);
    }

    // Metodo per rimuovere un paziente dalla lista (per codice fiscale)
    public boolean rimuoviPaziente(String codiceFiscale) {
        for (Paziente paziente : listaPazienti) {
            if (paziente.getCodiceFiscalePaziente().equals(codiceFiscale)) {
                listaPazienti.remove(paziente);
                return true;
            }
        }
        return false; // Paziente non trovato
    }


    // Metodo per visualizzare la lista di pazienti
    public void visualizzaPazienti() {
        if (listaPazienti.isEmpty()) {
            System.out.println("Nessun paziente registrato.");
        } else {
            for (Paziente paziente : listaPazienti) {
                System.out.println(paziente);
            }
        }
    }


    // Metodo per trovare un paziente per codice fiscale
    public Paziente trovaPaziente(String codiceFiscale) {
        for (Paziente paziente : listaPazienti) {
            if (paziente.getCodiceFiscalePaziente().equals(codiceFiscale)) {
                return paziente;
            }
        }
        return null; // Paziente non trovato
    }
// **Metodo per ottenere la lista osservabile di pazienti**
    public ObservableList<Paziente> getListaPazienti() {
        return listaPazienti;
    }
}
