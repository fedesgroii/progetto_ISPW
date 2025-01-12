package storage_db;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Classe che gestisce le operazioni sul database.
 * Si appoggia a DatabaseConnection per ottenere la connessione.
 */
public class DatabaseOperations {
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
   //costruttore privato per nascondere il costruttore pubblico implicito
    private DatabaseOperations(){
        logger.info("DatabaseConnection constructor called.");
    }
    /**
     * Esegue una query SELECT e restituisce i risultati come una lista di stringhe.
     *
     * @param query La query SQL da eseguire.
     * @return Una lista di righe, ciascuna rappresentata come stringa.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */

    public static List<String> executeQuery(String query) throws SQLException {
        // Lista per contenere i risultati della query
        List<String> risultati = new ArrayList<>();

        // Ottieni la connessione tramite la classe DatabaseConnection
        try (Connection conn = DatabaseConnection.getConnection();
             // Crea uno Statement per eseguire la query
             Statement stmt = conn.createStatement();
             // Esegui la query e ottieni un ResultSet con i risultati
             ResultSet rs = stmt.executeQuery(query)) {

            // Ottieni il numero di colonne nel risultato
            int columnCount = rs.getMetaData().getColumnCount();

            // Itera su ogni riga del ResultSet
            while (rs.next()) {
                // Costruisci una stringa con i valori delle colonne
                StringBuilder riga = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    // Aggiungi il valore della colonna corrente
                    riga.append(rs.getString(i));
                    if (i < columnCount) {
                        // Aggiungi un separatore tra le colonne
                        riga.append(" | ");
                    }
                }
                // Aggiungi la riga alla lista dei risultati
                risultati.add(riga.toString());
            }
        }

        // Restituisci la lista con tutti i risultati
        return risultati;
    }

    /**
     * Esegue un'operazione di modifica dei dati (INSERT, UPDATE, DELETE).
     *
     * @param query La query SQL da eseguire.
     * @return Il numero di righe interessate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    public static int executeUpdate(String query) throws SQLException {
        // Ottieni la connessione tramite la classe DatabaseConnection
        try (Connection conn = DatabaseConnection.getConnection();
             // Crea uno Statement per eseguire la query
             Statement stmt = conn.createStatement()) {

            // Esegui l'operazione e restituisci il numero di righe interessate
            return stmt.executeUpdate(query);
        }
    }
}
