package Storage_DB;
import utenti.Paziente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Storage_DB.DatabaseConnection.getConnection;

public class DatabaseStorageStrategyPaziente implements DataStorageStrategy<Paziente> {
    private static final Logger logger = Logger.getLogger(DatabaseStorageStrategyPaziente.class.getName());

    private static final String INSERT_QUERY = "INSERT INTO pazienti (nome, cognome, dataDiNascita, numeroTelefonico, email, numeroTesseraSanitaria, condizioniMediche, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM pazienti WHERE numeroTesseraSanitaria = ?";
    private static final String UPDATE_QUERY = "UPDATE pazienti SET nome = ?, cognome = ?, dataDiNascita = ?, numeroTelefonico = ?, email = ?, numeroTesseraSanitaria = ?, condizioniMediche = ?, password = ? WHERE numeroTesseraSanitaria = ?";
    private static final String DELETE_QUERY = "DELETE FROM pazienti WHERE numeroTesseraSanitaria = ?";

    @Override
    public boolean salva(Paziente paziente) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {

            stmt.setString(1, paziente.getNome());
            stmt.setString(2, paziente.getCognome());
            stmt.setDate(3, Date.valueOf(paziente.getDataDiNascita()));
            stmt.setString(4, paziente.getNumeroTelefonico());
            stmt.setString(5, paziente.getEmail());
            stmt.setString(6, paziente.getCodiceFiscalePaziente());
            stmt.setString(7, paziente.getCondizioniMediche());
            stmt.setString(8, paziente.getPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'inserimento del paziente", e);
            return false;
        }
    }


    @Override
    public Paziente trova(Paziente paziente) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_QUERY)) {

            stmt.setString(1, paziente.getCodiceFiscalePaziente());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate dataDiNascita = rs.getDate("dataDiNascita").toLocalDate();
                    return new Paziente(
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            dataDiNascita,
                            rs.getString("numeroTelefonico"),
                            rs.getString("email"),
                            null, // Passa null come password, non mi serve per trovare un paziente
                            rs.getString("numeroTesseraSanitaria"),
                            rs.getString("condizioniMediche")
                    );
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la ricerca del paziente", e);
        }
        return null;
    }


    @Override
    public boolean aggiorna(Paziente paziente) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY)) {

            stmt.setString(1, paziente.getNome());
            stmt.setString(2, paziente.getCognome());
            stmt.setDate(3, Date.valueOf(paziente.getDataDiNascita()));
            stmt.setString(4, paziente.getNumeroTelefonico());
            stmt.setString(5, paziente.getEmail());
            stmt.setString(6, paziente.getCodiceFiscalePaziente());
            stmt.setString(7, paziente.getCondizioniMediche());
            stmt.setString(8,paziente.getPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'aggiornamento del paziente", e);
            return false;
        }
    }

    @Override
    public boolean elimina(Paziente paziente) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {

            stmt.setString(1, paziente.getCodiceFiscalePaziente());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'eliminazione del paziente", e);
            return false;
        }
    }

    public List<Paziente> getAllPazienti() {
        List<Paziente> pazienti = new ArrayList<>();
        String selectQuery = "SELECT * FROM pazienti";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
                Paziente paziente = new Paziente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getDate("dataDiNascita").toLocalDate(),
                        rs.getString("numeroTelefonico"),
                        rs.getString("email"),
                        null, // Passa null per la password
                        rs.getString("numeroTesseraSanitaria"),
                        rs.getString("condizioniMediche")
                );
                pazienti.add(paziente);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante il recupero dei pazienti", e);
        }

        return pazienti;
    }


}
