package session_manager;

import model.Specialista; // Classe Specialista

public class SessionManagerSpecialista {

    private static SessionManagerSpecialista instance; // Istanza unica
    private Specialista specialistaLoggato; // Specialista attualmente loggato

    // Costruttore privato per evitare istanziazioni dirette
    private SessionManagerSpecialista() { }

    // Metodo per ottenere l'unica istanza di SessionManagerSpecialista
    public static SessionManagerSpecialista getInstance() {
        if (instance == null) {
            instance = new SessionManagerSpecialista();
        }
        return instance;
    }

    // Imposta lo specialista loggato
    public void setSpecialistaLoggato(Specialista specialista) {
        this.specialistaLoggato = specialista;
    }

    // Ottieni lo specialista loggato
    public Specialista getSpecialistaLoggato() {
        return specialistaLoggato;
    }

    // Metodo per resettare la sessione (logout)
    public void resetSession() {
        this.specialistaLoggato = null;
    }
}
