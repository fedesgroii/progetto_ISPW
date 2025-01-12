/*

Spiegazione del codice
Costruzione del design:
La classe utilizza i metodi della tua DatabaseConnection per ottenere
la connessione.
Contiene metodi statici per semplificare l'accesso
(puoi chiamarli direttamente senza creare un'istanza della classe).
Esecuzione delle query SELECT:
Il metodo executeQuery esegue una query SQL e restituisce
i risultati sotto forma di lista di stringhe, con ogni riga come
stringa separata.

Il metodo executeUpdate esegue query SQL di tipo INSERT, UPDATE, o DELETE.
Restituisce il numero di righe che sono state modificate o interessate dalla query.

Grazie al costrutto try-with-resources, la connessione,
lo statement e il result set vengono chiusi automaticamente,
anche in caso di errore. Questo migliora la gestione
delle risorse ed evita problemi come il leak delle connessioni.


La classe è pensata per essere generica e riutilizzabile in ogni
parte del progetto, separando chiaramente le operazioni sul database
dalla logica dell'applicazione.
 */

package storage_db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce le operazioni sul database.
 * Si appoggia a DatabaseConnection per ottenere la connessione.
 */
public class DatabaseOperations {

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
