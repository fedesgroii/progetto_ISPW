package storage_db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String CONFIG_FILE = "dbconfig.properties";
    private static String url;
    private static String user;
    private static String password;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    // Static initialization block for loading configuration
    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            Properties properties = new Properties();
            properties.load(fis);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            logger.severe("Failed to load configuration file: " + CONFIG_FILE);
            throw new ConfigurationLoadException("Failed to load configuration file: " + CONFIG_FILE, e);
        }
    }

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        logger.info("DatabaseConnection constructor called.");
    }

    // Method to get a connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            String errorMessage = "JDBC driver not found: " + e.getMessage();
            logger.severe(errorMessage);
            throw new SQLException(errorMessage, e);
        }
    }
}

// Custom exception class for configuration loading errors
class ConfigurationLoadException extends RuntimeException {
    public ConfigurationLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
