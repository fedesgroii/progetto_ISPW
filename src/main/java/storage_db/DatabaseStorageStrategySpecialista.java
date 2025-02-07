package storage_db;

import model.Specialista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseStorageStrategySpecialista implements DataStorageStrategy<Specialista> {
    private static final Logger logger = Logger.getLogger(DatabaseStorageStrategySpecialista.class.getName());

    // Query SQL con nomi di colonne corretti
    private static final String INSERT_QUERY = "INSERT INTO specialista (nome, cognome, dataDiNascita, numeroTelefonico, email, specializzazione, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT nome, cognome, dataDiNascita, numeroTelefonico, email, specializzazione, password FROM specialista WHERE nome = ? AND cognome = ? AND email = ?";
    private static final String UPDATE_QUERY = "UPDATE specialista SET dataDiNascita = ?, numeroTelefonico = ?, specializzazione = ?, password = ? WHERE nome = ? AND cognome = ? AND email = ?";
    private static final String DELETE_QUERY = "DELETE FROM specialista WHERE nome = ? AND cognome = ? AND email = ?";
    private static final String SELECT_ALL_QUERY = "SELECT nome, cognome, dataDiNascita, numeroTelefonico, email, specializzazione, password FROM specialisti";

    @Override
    public boolean salva(Specialista specialista) {
        Objects.requireNonNull(specialista, "Specialista non può essere null");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {
            setSpecialistaParameters(stmt, specialista, false);
            logger.info("Tentativo di inserimento specialista: " + specialista);
            return stmt.executeUpdate() > 0; // Controlla se almeno una riga è stata inserita
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'inserimento dello specialista: " + specialista.getNome(), e);
            return false;
        }
    }

    @Override
    public Optional<Specialista> trova(Specialista specialista) {
        Objects.requireNonNull(specialista, "Specialista non può essere null");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_QUERY)) {
            stmt.setString(1, specialista.getNome());
            stmt.setString(2, specialista.getCognome());
            stmt.setString(3, specialista.getEmail());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToSpecialista(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la ricerca dello specialista: {0}", specialista.getNome());
        }
        return Optional.empty();
    }

    @Override
    public boolean aggiorna(Specialista specialista) {
        Objects.requireNonNull(specialista, "Specialista non può essere null");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY)) {
            setSpecialistaParametersForUpdate(stmt, specialista);
            stmt.setString(5, specialista.getNome());
            stmt.setString(6, specialista.getCognome());
            stmt.setString(7, specialista.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'aggiornamento dello specialista: {0}", specialista.getNome());
            return false;
        }
    }

    @Override
    public boolean elimina(Specialista specialista) {
        Objects.requireNonNull(specialista, "Specialista non può essere null");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {
            stmt.setString(1, specialista.getNome());
            stmt.setString(2, specialista.getCognome());
            stmt.setString(3, specialista.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante l'eliminazione dello specialista: {0}", specialista.getNome());
            return false;
        }
    }

    /**
     * Recupera tutti gli specialisti dal database.
     */
    public List<Specialista> getAllSpecialisti() {
        List<Specialista> specialisti = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                specialisti.add(mapResultSetToSpecialista(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante il recupero degli specialisti", e);
        }
        return specialisti;
    }

    /**
     * Imposta i parametri per lo specialista nel PreparedStatement durante l'inserimento.
     */
    private void setSpecialistaParameters(PreparedStatement stmt, Specialista specialista, boolean isUpdate) throws SQLException {
        stmt.setString(1, specialista.getNome());
        stmt.setString(2, specialista.getCognome());
        stmt.setDate(3, Date.valueOf(specialista.getDataDiNascita()));
        stmt.setString(4, specialista.getNumeroTelefonico());
        stmt.setString(5, specialista.getEmail());
        stmt.setString(6, specialista.getSpecializzazione());
        stmt.setString(7, specialista.getPassword());
    }

    /**
     * Imposta i parametri per lo specialista nel PreparedStatement durante l'aggiornamento.
     */
    private void setSpecialistaParametersForUpdate(PreparedStatement stmt, Specialista specialista) throws SQLException {
        stmt.setDate(1, Date.valueOf(specialista.getDataDiNascita()));
        stmt.setString(2, specialista.getNumeroTelefonico());
        stmt.setString(3, specialista.getSpecializzazione());
        stmt.setString(4, specialista.getPassword());
    }

    /**
     * Mappa un ResultSet a un oggetto Specialista.
     */
    private Specialista mapResultSetToSpecialista(ResultSet rs) throws SQLException {
        return new Specialista(
                rs.getString("nome"),
                rs.getString("cognome"),
                rs.getDate("dataDiNascita").toLocalDate(),
                rs.getString("numeroTelefonico"),
                rs.getString("email"),
                rs.getString("specializzazione"),
                rs.getString("password")
        );
    }
}