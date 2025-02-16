package storage_db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static String url;
    private static String user;
    private static String password;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    // Blocco di inizializzazione statico per caricare la configurazione
    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
            if (input == null) {
                throw new IOException("File dbconfig.properties non trovato nel classpath.");
            }
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");

            logger.info("Configurazione database caricata con successo.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Errore nel caricamento del file di configurazione: dbconfig.properties", e);
            throw new ConfigurationLoadException("Impossibile caricare il file di configurazione: dbconfig.properties", e);
        }
    }

    // Costruttore privato per impedire l'istanziazione
    private DatabaseConnection() {
        logger.fine("Costruttore di DatabaseConnection chiamato.");
    }

    /**
     * Restituisce una connessione al database.
     *
     * @return Connessione al database
     * @throws SQLException in caso di errore durante la connessione
     */
    public static Connection getConnection() throws SQLException {
        try {
            logger.fine("Tentativo di caricamento del driver JDBC...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.fine("Driver JDBC caricato correttamente.");

            logger.info("Tentativo di connessione al database...");
            Connection conn = DriverManager.getConnection(url, user, password);
            logger.info("Connessione al database riuscita.");
            return conn;
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Driver JDBC non trovato", e);
            throw new SQLException("Driver JDBC non trovato", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la connessione al database", e);
            throw e;
        }
    }

    // Classe eccezione custom per errori di caricamento della configurazione
    public static class ConfigurationLoadException extends RuntimeException {
        public ConfigurationLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
