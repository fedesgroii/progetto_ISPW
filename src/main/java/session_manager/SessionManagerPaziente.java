package session_manager;

import model.Paziente; // classe Paziente

public class SessionManagerPaziente {
    private static SessionManagerPaziente instance; // Istanza unica
    private Paziente pazienteLoggato;       // Paziente attualmente loggato

    // Costruttore privato per evitare istanziazioni dirette
    private SessionManagerPaziente() { }

    // Metodo per ottenere l'unica istanza di SessionManagerPaziente
    public static SessionManagerPaziente getInstance() {
        if (instance == null) {
            instance = new SessionManagerPaziente();
        }
        return instance;
    }

    // Imposta il paziente loggato
    public void setPazienteLoggato(Paziente paziente) {
        this.pazienteLoggato = paziente;
    }

    // Ottieni il paziente loggato
    public Paziente getPazienteLoggato() {
        return pazienteLoggato;
    }

    // Metodo per resettare la sessione (logout)
    public void resetSession() {
        this.pazienteLoggato = null;
    }
}
