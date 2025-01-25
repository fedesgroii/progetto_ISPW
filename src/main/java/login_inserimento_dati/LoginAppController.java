package login_inserimento_dati;

import model.Paziente;
import model.Specialista;
import session_manager.SessionManagerPaziente;
import session_manager.SessionManagerSpecialista;
import startupconfig.StartupSettingsEntity;
import storage_liste.ListaPazienti;
import storage_liste.ListaSpecialisti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static storage_db.DatabaseConnection.getConnection;

public class LoginAppController {
    private static final Logger logger = Logger.getLogger(LoginAppController.class.getName());
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();
    private final LoginGraphicController controlloreGrafico;
    private final LoginViewBase view;
    SessionManagerPaziente gestore_sessione_pazienti = SessionManagerPaziente.getInstance();
    SessionManagerSpecialista gestore_sessione_specialista = SessionManagerSpecialista.getInstance();

    public LoginAppController(LoginViewBase view) {
        this.view = view;
        this.controlloreGrafico = new LoginGraphicController(view);
    }

    public boolean checkCredentials(String userType, String username, String password) {
        boolean risultato;
        if (config.isSaveToDatabase()) {
            risultato = checkDatabaseCredentials(userType, username, password);
        } else {
            risultato = checkListPointCredentials(userType, username, password);
        }
        return risultato;
    }

    public boolean checkDatabaseCredentials(String email, String password, String userType) {
        // Verifica se il nome della tabella è valido (evita SQL injection)
        if (!"paziente".equalsIgnoreCase(userType) && !"specialista".equalsIgnoreCase(userType)) {
            logger.log(Level.SEVERE, "Tipo di utente non valido: " + userType);
            return false;
        }

        // Costruzione dinamica della query
        String SELECT_QUERY = "SELECT email, password FROM " + userType + " WHERE email = "+email+" AND password = "+password;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_QUERY)) {

            // Imposta i parametri
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Restituisce true se le credenziali corrispondono
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la verifica delle credenziali per " + userType, e);
        }

        return false; // Credenziali non valide
    }

    private boolean checkListPointCredentials(String userType, String username, String password) {
        if (userType.equalsIgnoreCase("Patient") || userType.equalsIgnoreCase("Paziente")) {
            ListaPazienti listaPazienti = ListaPazienti.getIstanzaListaPazienti();

            for (Paziente paziente : listaPazienti.getObservableListaPazienti()) {
                if (paziente.getEmail().equalsIgnoreCase(username) && paziente.getPassword().equals(password)) {
                    gestore_sessione_pazienti.setPazienteLoggato(paziente);
                    return true;
                }
            }

        }

        if (userType.equalsIgnoreCase("Specialista") || userType.equalsIgnoreCase("Specialist")) {
            ListaSpecialisti listaSpecialisti = ListaSpecialisti.getIstanzaListaSpecialisti();

            for (Specialista specialista : listaSpecialisti.getObservableListaSpecialisti()) {
                if (specialista.getEmail().equalsIgnoreCase(username) && specialista.getPassword().equals(password)) {
                    gestore_sessione_specialista.setSpecialistaLoggato(specialista);
                    return true;
                }
            }
        }
        // Nessuna corrispondenza trovata
        return false;
    }

}


