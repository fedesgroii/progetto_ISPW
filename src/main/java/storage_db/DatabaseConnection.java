package storage_db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String CONFIG_FILE = "dbconfig.properties"; // Percorso del file di configurazione
    private static String url;
    private static String user;
    private static String password;

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            // Carica il file di configurazione
            Properties properties = new Properties();
            properties.load(fis);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            // Gestione degli errori di lettura del file di configurazione
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare il file di configurazione: " + CONFIG_FILE, e);
        }
    }

    // Metodo per ottenere la connessione
    public static Connection getConnection() throws SQLException {
        try {
            // Carica il driver JDBC per MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Restituisci la connessione
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            // Gestione degli errori relativi al driver
            e.printStackTrace();
            throw new SQLException("JDBC driver non trovato.");
        }
    }
}
