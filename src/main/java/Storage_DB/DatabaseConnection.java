package Storage_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Dati per la connessione
    private static final String URL = "jdbc:mysql://localhost:3306/MindLab";  // Nome del database
    private static final String USER = "root";  // Username di MySQL
    private static final String PASSWORD = "ACFSLOVE";  // Password di MySQL

    // Metodo per ottenere la connessione
    public static Connection getConnection() throws SQLException {
        try {
            // Carica il driver JDBC per MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Restituisci la connessione
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Gestione degli errori relativi al driver
            e.printStackTrace();
            throw new SQLException("JDBC driver non trovato.");
        }
    }
}
