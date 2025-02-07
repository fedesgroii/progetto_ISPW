package prenotazione_visita;

import model.Paziente;
import model.Visita;
import session_manager.SessionManagerPaziente;

import java.time.LocalDate;
import java.time.LocalTime;

public class PrenotazioneVisitaBean {
    private LocalDate dataVisita;  // Tipo LocalDate per la data
    private LocalTime orarioVisita; // Tipo LocalTime per l'orario
    private String specialista;     // Tipo String per lo specialista
    private String tipoVisita;      // Tipo String per il tipo di visita
    private String motivoVisita;    // Tipo String per il motivo della visita

    // Getter e Setter
    public LocalDate getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(LocalDate dataVisita) {
        this.dataVisita = dataVisita;
    }

    public LocalTime getOrarioVisita() {
        return orarioVisita;
    }

    public void setOrarioVisita(LocalTime orarioVisita) {
        this.orarioVisita = orarioVisita;
    }

    public String getSpecialista() {
        return specialista;
    }

    public void setSpecialista(String specialista) {
        this.specialista = specialista;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getMotivoVisita() {
        return motivoVisita;
    }

    public void setMotivoVisita(String motivoVisita) {
        this.motivoVisita = motivoVisita;
    }

    public static Visita toVisita(PrenotazioneVisitaBean bean) {
        if (!SessionManagerPaziente.isLoggedIn()) {
            throw new IllegalStateException("Nessun paziente loggato. Effettua il login prima di prenotare.");
        }

        return new Visita(
                SessionManagerPaziente.getPazienteLoggato(),
                bean.getDataVisita(),
                bean.getOrarioVisita(),
                bean.getSpecialista(),
                bean.getTipoVisita(),
                bean.getMotivoVisita(),
                "Prenotata"
        );
    }
}

