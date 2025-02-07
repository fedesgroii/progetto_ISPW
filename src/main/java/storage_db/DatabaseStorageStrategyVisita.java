package storage_db;

import model.Paziente;
import model.Visita;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseStorageStrategyVisita implements DataStorageStrategy<Visita> {
    private static final Logger logger = Logger.getLogger(DatabaseStorageStrategyVisita.class.getName());

    // Query SQL come costanti
    private static final String INSERT_QUERY = "INSERT INTO visite (paziente, data, orario, specialista, tipoVisita, motivoVisita, stato) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT paziente, data, orario, specialista, tipoVisita, motivoVisita, stato FROM visite WHERE paziente=? AND data=? AND orario=?";
    private static final String UPDATE_QUERY = "UPDATE visite SET specialista = ?, tipoVisita = ?, motivoVisita = ?, stato = ? WHERE paziente = ? AND data = ? AND orario = ?";
    private static final String DELETE_QUERY = "DELETE FROM visite WHERE paziente = ? AND data = ? AND orario = ?";
    private static final String SELECT_ALL_QUERY = "SELECT paziente, data, orario, specialista, tipoVisita, motivoVisita, stato FROM visite";

    @Override
    public boolean salva(Visita visita) {
        Objects.requireNonNull(visita, "Visita non può essere null");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {

            stmt.setString(1, visita.getPaziente().getCodiceFiscalePaziente());
            stmt.setObject(2, visita.getData());
            stmt.setObject(3, visita.getOrario());
            stmt.setString(4, visita.getSpecialista());
            stmt.setString(5, visita.getTipoVisita());
            stmt.setString(6, visita.getMotivoVisita());
            stmt.setString(7, visita.getStato());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'inserimento della visita per paziente: {0}",
                    visita.getPaziente().getCodiceFiscalePaziente());
            return false;
        }
    }

    @Override
    public Optional<Visita> trova(Visita visita) {
        Objects.requireNonNull(visita, "Visita non può essere null");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_QUERY)) {

            setKeyParameters(stmt, 1, visita);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToVisita(rs, visita.getPaziente()));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la ricerca della visita per paziente: {0}",
                    visita.getPaziente().getCodiceFiscalePaziente());
        }
        return Optional.empty();
    }

    @Override
    public boolean aggiorna(Visita visita) {
        Objects.requireNonNull(visita, "Visita non può essere null");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY)) {

            stmt.setString(1, visita.getSpecialista());
            stmt.setString(2, visita.getTipoVisita());
            stmt.setString(3, visita.getMotivoVisita());
            stmt.setString(4, visita.getStato());
            setKeyParameters(stmt, 5, visita);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'aggiornamento della visita per paziente: {0}",
                    visita.getPaziente().getCodiceFiscalePaziente());
            return false;
        }
    }

    @Override
    public boolean elimina(Visita visita) {
        Objects.requireNonNull(visita, "Visita non può essere null");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {

            setKeyParameters(stmt, 1, visita);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'eliminazione della visita per paziente: {0}",
                    visita.getPaziente().getCodiceFiscalePaziente());
            return false;
        }
    }

    public List<Visita> getAllVisite() {
        List<Visita> visite = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                visite.add(mapResultSetToVisita(rs, null));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante il recupero delle visite", e);
        }
        return visite;
    }

    // Metodi helper
    private void setKeyParameters(PreparedStatement stmt, int startIndex, Visita visita) throws SQLException {
        stmt.setString(startIndex, visita.getPaziente().getCodiceFiscalePaziente());
        stmt.setObject(startIndex + 1, visita.getData());
        stmt.setObject(startIndex + 2, visita.getOrario());
    }

    private Visita mapResultSetToVisita(ResultSet rs, Paziente paziente) throws SQLException {
        return new Visita(
                (paziente != null) ? paziente : new Paziente.Builder().codiceFiscalePaziente(rs.getString("paziente")).build(),
                rs.getObject("data", LocalDate.class),
                rs.getObject("orario", LocalTime.class),
                rs.getString("specialista"),
                rs.getString("tipoVisita"),
                rs.getString("motivoVisita"),
                rs.getString("stato")
        );
    }
}