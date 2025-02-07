package model;

import prenotazione_visita.PrenotazioneVisitaBean;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visita {
    private Paziente paziente; // Il paziente che sta effettuando la visita
    private LocalDate data;   // Solo la data
    private LocalTime orario; // Solo l'ora
    private String specialista; // Lo specialista che effettua la visita (medico, terapista, etc.)
    private String tipoVisita; // Tipo di visita (logopedica, psicologica ecc..)
    private String motivoVisita; // Motivo della visita
    private String stato; // Stato della visita (prenotata, completata, annullata, etc.)

    // Costruttore
    public Visita(Paziente paziente, LocalDate data, LocalTime orario, String specialista,
                  String tipoVisita, String motivoVisita, String stato) {
        this.paziente = paziente;
        this.data = data;
        this.orario = orario;
        this.specialista = specialista;
        this.tipoVisita = tipoVisita;
        this.motivoVisita = motivoVisita;
        this.stato = stato;
    }

    // Getter e Setter
    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Visita{" +
                "paziente=" + paziente +
                ", data=" + data +
                ", orario=" + orario +
                ", specialista='" + specialista + '\'' +
                ", tipoVisita='" + tipoVisita + '\'' +
                ", motivoVisita='" + motivoVisita + '\'' +
                ", stato='" + stato + '\'' +
                '}';
    }

}
