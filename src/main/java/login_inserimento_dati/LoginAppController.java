package login_inserimento_dati;

import model.Paziente;
import model.Specialista;
import session_manager.SessionManagerPaziente;
import session_manager.SessionManagerSpecialista;
import startupconfig.StartupSettingsEntity;
import storage_db.DatabaseConnection;
import storage_file.FileManagerSpecialisti;
import storage_liste.ListaPazienti;
import storage_liste.ListaSpecialisti;
import storage_file.FileManagerPazienti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginAppController {
    private static final Logger logger = Logger.getLogger(LoginAppController.class.getName());
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();
    private final LoginGraphicController controlloreGrafico;
    private final LoginViewBase view;
    SessionManagerSpecialista gestore_sessione_specialista = SessionManagerSpecialista.getInstance();

    public LoginAppController(LoginViewBase view) {
        this.view = view;
        this.controlloreGrafico = new LoginGraphicController(view);
    }

    public boolean checkCredentials(String userType, String username, String password) {
        boolean risultato = false;
        int storageOption = config.getStorageOption();

        switch (storageOption) {
            case 0: // Salvataggio in memoria RAM (Liste)
                risultato = checkListPointCredentials(userType, username, password);
                break;
            case 1: // Salvataggio su Database
                risultato = checkDatabaseCredentials(userType, username, password);
                break;
            case 2: // Salvataggio su File System
                risultato = checkFileStorageCredentials(userType, username, password);
                break;
            default:
                logger.log(Level.SEVERE, "Opzione di salvataggio non valida: " + storageOption);
                break;
        }

        return risultato;
    }

    public boolean checkDatabaseCredentials(String userType, String email, String password) {
        if (!"Patient".equalsIgnoreCase(userType) && !"Specialist".equalsIgnoreCase(userType)) {
            logger.log(Level.SEVERE, "Tipo di utente non valido: " + userType);
            return false;
        }

        String tableName = userType.equalsIgnoreCase("Patient") ? "pazienti" : "specialista";
        String SELECT_QUERY = "SELECT email, password FROM " + tableName + " WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_QUERY)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la verifica delle credenziali per " + userType, e);
        }

        return false;
    }

    private boolean checkListPointCredentials(String userType, String username, String password) {
        if (userType.equalsIgnoreCase("Patient") || userType.equalsIgnoreCase("Paziente")) {
            ListaPazienti listaPazienti = ListaPazienti.getIstanzaListaPazienti();
            for (Paziente paziente : listaPazienti.getObservableListaPazienti()) {
                if (paziente.getEmail().equalsIgnoreCase(username) && paziente.getPassword().equals(password)) {
                    SessionManagerPaziente.setPazienteLoggato(paziente);
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
        return false;
    }

    private boolean checkFileStorageCredentials(String userType, String username, String password) {
        if (userType.equalsIgnoreCase("Patient") || userType.equalsIgnoreCase("Paziente")) {
            FileManagerPazienti fileManager = new FileManagerPazienti();
            return verifyPatientCredentials(fileManager, username, password);
        } else if (userType.equalsIgnoreCase("Specialist") || userType.equalsIgnoreCase("Specialista")) {
            FileManagerSpecialisti fileManager = new FileManagerSpecialisti();
            return verifySpecialistCredentials(fileManager, username, password);
        }
        return false;
    }

    /**
     * Verifica le credenziali del paziente iterando su tutti i file salvati e confrontando email e password.
     */
    private boolean verifyPatientCredentials(FileManagerPazienti fileManager, String username, String password) {
        List<Paziente> listaPazienti = fileManager.trovaTutti();
        for (Paziente paziente : listaPazienti) {
            if (paziente.getEmail().equalsIgnoreCase(username) && paziente.getPassword().equals(password)) {
                SessionManagerPaziente.setPazienteLoggato(paziente);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica le credenziali dello specialista iterando su tutti i file salvati e confrontando email e password.
     */
    private boolean verifySpecialistCredentials(FileManagerSpecialisti fileManager, String username, String password) {
        List<Specialista> listaSpecialisti = fileManager.trovaTutti();
        for (Specialista specialista : listaSpecialisti) {
            if (specialista.getEmail().equalsIgnoreCase(username) && specialista.getPassword().equals(password)) {
                gestore_sessione_specialista.setSpecialistaLoggato(specialista);
                return true;
            }
        }
        return false;
    }
}
