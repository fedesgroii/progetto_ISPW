package login_inserimento_dati;

import model.Paziente;
import model.Specialista;
import org.mindrot.jbcrypt.BCrypt;
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
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginAppController {
    private static final Logger logger = Logger.getLogger(LoginAppController.class.getName());
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();
    private final SessionManagerSpecialista gestoreSessioneSpecialista = SessionManagerSpecialista.getInstance();

    // Definizione delle costanti per "Patient" e "Specialist"
    private static final String PATIENT_TYPE = "Patient";
    private static final String SPECIALIST_TYPE = "Specialist";
    public LoginAppController() {
    }

    public Object checkCredentials(String userType, String username, String password) {
        int storageOption = config.getStorageOption();
        switch (storageOption) {
            case 0: // Salvataggio in memoria RAM (Liste)
                return checkListPointCredentials(userType, username, password);
            case 1: // Salvataggio su Database
                return checkDatabaseCredentials(userType, username, password);
            case 2: // Salvataggio su File System
                return checkFileStorageCredentials(userType, username, password);
            default:
                String errorMessage = String.format("Opzione di salvataggio non valida: %d", storageOption);
                logger.log(Level.SEVERE, errorMessage);
                return null;
        }
    }

    public Object checkDatabaseCredentials(String userType, String email, String password) {
        logger.info("Avvio verifica credenziali per userType: " + userType + ", email: " + email);

        if (!PATIENT_TYPE.equalsIgnoreCase(userType) && !SPECIALIST_TYPE.equalsIgnoreCase(userType)) {
            String errorMessage = String.format("Tipo di utente non valido: %s", userType);
            logger.log(Level.SEVERE, errorMessage);
            return null;
        }

        String tableName = userType.equalsIgnoreCase(PATIENT_TYPE) ? "pazienti" : "specialista";
        String selectQuery = "SELECT * FROM " + tableName + " WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

            logger.info("Connessione al database riuscita.");
            logger.info("Query preparata: " + selectQuery);

            stmt.setString(1, email);
            stmt.setString(2, password);
            logger.info("Parametri impostati: email=" + email + ", password=****");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    logger.info("Utente trovato nel database.");

                    if (userType.equalsIgnoreCase(PATIENT_TYPE)) {
                        logger.info("Creazione oggetto Paziente.");
                        Paziente paziente = new Paziente.Builder()
                                .nome(rs.getString("nome"))
                                .cognome(rs.getString("cognome"))
                                .dataDiNascita(LocalDate.parse(rs.getString("dataDiNascita")))
                                .numeroTelefonico(rs.getString("numeroTelefonico"))
                                .email(rs.getString("email"))
                                .password(rs.getString("password"))
                                .codiceFiscalePaziente(rs.getString("numeroTesseraSanitaria"))
                                .condizioniMediche(rs.getString("condizioniMediche"))
                                .build();
                        return paziente;
                    } else {
                        logger.info("Creazione oggetto Specialista.");
                        Specialista specialista = new Specialista(
                                rs.getString("nome"),
                                rs.getString("cognome"),
                                LocalDate.parse(rs.getString("dataDiNascita")),
                                rs.getString("numeroTelefonico"),
                                rs.getString("email"),
                                rs.getString("specializzazione"),
                                rs.getString("password")
                        );
                        return specialista;
                    }
                } else {
                    logger.warning("Nessun utente trovato con le credenziali fornite.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore SQL durante la verifica delle credenziali per {0}: {1}", new Object[]{userType, e.getMessage()});
        }

        logger.info("Verifica credenziali terminata senza successo.");
        return null;
    }


    private Object checkListPointCredentials(String userType, String username, String password) {
        if (isPatientType(userType)) {
            return checkPatientCredentials(username, password);
        } else if (isSpecialistType(userType)) {
            return checkSpecialistCredentials(username, password);
        }
        return null;
    }

    private boolean isPatientType(String userType) {
        return userType.equalsIgnoreCase(PATIENT_TYPE) || userType.equalsIgnoreCase("Paziente");
    }

    private boolean isSpecialistType(String userType) {
        return userType.equalsIgnoreCase(SPECIALIST_TYPE) || userType.equalsIgnoreCase("Specialista");
    }

    private Paziente checkPatientCredentials(String username, String password) {
        ListaPazienti listaPazienti = ListaPazienti.getIstanzaListaPazienti();
        for (Paziente paziente : listaPazienti.getObservableListaPazienti()) {
            if (paziente.getEmail().equalsIgnoreCase(username) && paziente.getPassword().equals(password)) {
                return paziente;
            }
        }
        return null;
    }

    private Specialista checkSpecialistCredentials(String username, String password) {
        ListaSpecialisti listaSpecialisti = ListaSpecialisti.getIstanzaListaSpecialisti();
        for (Specialista specialista : listaSpecialisti.getObservableListaSpecialisti()) {
            if (specialista.getEmail().equalsIgnoreCase(username) && specialista.getPassword().equals(password)) {
                return specialista;
            }
        }
        return null;
    }

    public Object checkFileStorageCredentials(String userType, String username, String password) {
        if (userType.equalsIgnoreCase(PATIENT_TYPE) || userType.equalsIgnoreCase("Paziente")) {
            FileManagerPazienti fileManager = new FileManagerPazienti();
            logger.info("Utilizzo file storage per pazienti nella cartella: " + fileManager.getFolderPath());
            return verifyPatientCredentials(fileManager, username, password);
        } else if (userType.equalsIgnoreCase(SPECIALIST_TYPE) || userType.equalsIgnoreCase("Specialista")) {
            FileManagerSpecialisti fileManager = new FileManagerSpecialisti();
            logger.info("Utilizzo file storage per specialisti nella cartella: " + fileManager.getFolderPath());
            return verifySpecialistCredentials(fileManager, username, password);
        }
        logger.warning("Tipo di utente non valido per il file storage: " + userType);
        return null; // Restituisce null se il tipo di utente non è valido
    }

    /**
     * Verifica le credenziali del paziente iterando su tutti i file JSON salvati nel percorso specificato e confrontando email e password.
     */
    private Paziente verifyPatientCredentials(FileManagerPazienti fileManager, String username, String password) {
        logger.info("Inizio verifica credenziali paziente tramite file storage.");
        List<Paziente> listaPazienti = fileManager.trovaTutti();

        if (listaPazienti == null || listaPazienti.isEmpty()) {
            logger.warning("Nessun file JSON trovato nel percorso: " + fileManager.getFolderPath());
            return null;
        }

        for (Paziente paziente : listaPazienti) {
            logger.fine("Verifica paziente: " + paziente.getEmail());
            // Verifica la password usando BCrypt: la password in chiaro viene confrontata con l'hash memorizzato
            if (paziente.getEmail().equalsIgnoreCase(username) && BCrypt.checkpw(password, paziente.getPassword())) {
                logger.info("Credenziali valide per il paziente: " + username);
                return paziente;
            }
        }
        logger.warning("Nessun paziente trovato con le credenziali fornite: " + username);
        return null;
    }

    /**
     * Verifica le credenziali dello specialista iterando su tutti i file JSON salvati nel percorso specificato e confrontando email e password.
     */
    private Specialista verifySpecialistCredentials(FileManagerSpecialisti fileManager, String username, String password) {
        logger.info("Inizio verifica credenziali specialista tramite file storage.");
        List<Specialista> listaSpecialisti = fileManager.trovaTutti();

        if (listaSpecialisti == null || listaSpecialisti.isEmpty()) {
            logger.warning("Nessun file JSON trovato nel percorso: " + fileManager.getFolderPath());
            return null;
        }

        for (Specialista specialista : listaSpecialisti) {
            logger.fine("Verifica specialista: " + specialista.getEmail());
            if (specialista.getEmail().equalsIgnoreCase(username) && BCrypt.checkpw(password, specialista.getPassword())) {
                logger.info("Credenziali valide per lo specialista: " + username);
                return specialista;
            }
        }
        logger.warning("Nessuno specialista trovato con le credenziali fornite: " + username);
        return null;
    }
}