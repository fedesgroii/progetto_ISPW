package session_manager;
import java.util.concurrent.atomic.AtomicReference;
import model.Specialista;

/**
 * Gestisce la sessione dello specialista usando il pattern Singleton.
 * Tiene traccia dello specialista loggato in modo thread-safe.
 */
public class SessionManagerSpecialista {
    private static final AtomicReference<SessionManagerSpecialista> instance = new AtomicReference<>(); // Istanza Singleton thread-safe
    private final AtomicReference<Specialista> specialistaLoggato = new AtomicReference<>(); // Specialista loggato (thread-safe)
    private final Object lock = new Object(); // Lucchetto per sincronizzazione

    // Costruttore privato
    private SessionManagerSpecialista() {}

    /**
     * Restituisce l'unica istanza del SessionManager (Singleton thread-safe con double-checked locking).
     */
    public static SessionManagerSpecialista getInstance() {
        if (instance.get() == null) {
            synchronized (SessionManagerSpecialista.class) {
                if (instance.get() == null) {
                    instance.set(new SessionManagerSpecialista());
                }
            }
        }
        return instance.get();
    }

    /**
     * Imposta lo specialista loggato, previene login multipli.
     * @param specialista Lo specialista da loggare
     * @throws IllegalStateException Se c'è già uno specialista loggato
     */
    public void setSpecialistaLoggato(Specialista specialista) {
        synchronized (lock) {
            if (specialistaLoggato.get() != null) {
                throw new IllegalStateException("Login fallito: uno specialista è già loggato!");
            }
            specialistaLoggato.set(specialista);
        }
    }

    /**
     * Restituisce lo specialista attualmente loggato.
     * @return Lo specialista loggato
     * @throws IllegalStateException Se nessuno è loggato
     */
    public Specialista getSpecialistaLoggato() {
        Specialista loggedSpecialista = specialistaLoggato.get();
        if (loggedSpecialista == null) {
            throw new IllegalStateException("Nessuno specialista loggato. Effettua il login.");
        }
        return loggedSpecialista;
    }

    /**
     * Resetta la sessione corrente (logout).
     */
    public void resetSession() {
        synchronized (lock) {
            specialistaLoggato.set(null);
        }
    }

    /**
     * Verifica se c'è uno specialista loggato.
     * @return true se loggato, false altrimenti
     */
    public boolean isLoggedIn() {
        return specialistaLoggato.get() != null;
    }
}