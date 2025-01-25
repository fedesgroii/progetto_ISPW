/*package prenotazione_visita;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PrenotazioneVisitaControllerApplicativo {

    public boolean validaPrenotazione(LocalDate data, String specialista, String tipoVisita, String motivo) {
        // Controlla se i campi sono validi
        return data != null && specialista != null && !specialista.isEmpty() &&
                tipoVisita != null && !tipoVisita.isEmpty() && motivo != null && !motivo.isEmpty();
    }

    public PrenotazioneVisitaControllerApplicativo creaPrenotazione(LocalDate data, String specialista, String tipoVisita, String motivo) {
        // Crea una nuova prenotazione
        LocalDateTime dataVisita = data.atStartOfDay();
        return new PrenotazioneVisita(dataVisita, specialista, tipoVisita, motivo);
    }
}
*/