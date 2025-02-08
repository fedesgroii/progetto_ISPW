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

    // Static initialization block for loading configuration
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

            // Verifica se il logging a livello INFO è abilitato prima di formattare la stringa
            if (logger.isLoggable(Level.INFO)) {
                logger.info(String.format("Configurazione database caricata: url=%s, user=%s", url, user));
            }

        } catch (IOException e) {
            logger.severe("Impossibile caricare il file di configurazione: dbconfig.properties");
            throw new ConfigurationLoadException("Impossibile caricare il file di configurazione: dbconfig.properties", e);
        }
    }

    private DatabaseConnection() {
        logger.info("Costruttore di DatabaseConnection chiamato.");
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("Driver JDBC caricato correttamente.");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            String errorMessage = "Driver JDBC non trovato: " + e.getMessage();
            logger.severe(errorMessage);
            throw new SQLException(errorMessage, e);
        }
    }
}

// Classe eccezione custom per errori di caricamento della configurazione
class ConfigurationLoadException extends RuntimeException {
    public ConfigurationLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}