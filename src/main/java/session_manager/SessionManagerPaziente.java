package session_manager;
import java.util.concurrent.atomic.AtomicReference;
import model.Paziente;

/**
 * Gestisce la sessione del paziente
 * Tiene traccia di chi è loggato.
 */
public class SessionManagerPaziente {
    private static final AtomicReference<Paziente> pazienteLoggato = new AtomicReference<>(); // paziente loggato
    // Il lucchetto per evitare che due thread facciano casino insieme
    private static final Object lock = new Object();
    // Costruttore
    private SessionManagerPaziente() {
        throw new UnsupportedOperationException("Classe statica, creazione istanza abortita");
    }
    /**
     * Fa entrare il paziente nel sistema. Inizializza la sessione
     * @param paziente Il paziente da loggare
     * @throws IllegalStateException evitare doppi login
     */
    public static void setPazienteLoggato(Paziente paziente) {
        synchronized (lock) { // blocchiamo il lavoro di più thread in contemporanea
            if (pazienteLoggato.get() != null) {
                throw new IllegalStateException("Ehi, abbiamo già un paziente qui! Prima fai logout al vecchio.");
            }
            pazienteLoggato.set(paziente);
        }
    }
    /**
     * Restituisce il paziente attualmente in registrato nella sessione.
     * @return Il paziente loggato
     * @throws IllegalStateException Se la stanza è vuota (nessuno loggato)
     */
    public static Paziente getPazienteLoggato() {
        Paziente paziente = pazienteLoggato.get();
        if (paziente == null) {
            throw new IllegalStateException("Non c'è nessuno qui, effettua il login Prima");
        }
        return paziente;
    }
    /**
     * Elimina dalla sessione il paziente attuale (logout).
     * Dopo questa chiamata la sessione è pulita come una sala operatoria.
     */
    public static void resetSession() {
        synchronized (lock) { // Chiudiamo la porta mentre puliamo
            pazienteLoggato.set(null); // Via i vecchi dati!
        }
    }
    /**
     * Controlla se la sessione è attiva e registra un paziente.
     * @return true se c'è un paziente loggato, false altrimenti
     */
    public static boolean isLoggedIn() {
        return pazienteLoggato.get() != null;
    }
}