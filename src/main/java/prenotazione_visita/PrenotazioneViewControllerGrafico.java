package prenotazione_visita;

import java.time.LocalTime;

public class PrenotazioneViewControllerGrafico {
    private final PrenotazioneVisitaView view;

    public PrenotazioneViewControllerGrafico(PrenotazioneVisitaView view) {
        this.view = view;
    }

    boolean isValidDataVisita(PrenotazioneVisitaBean bean) {
        return validateField(
                bean.getDataVisita() != null,
                view::showErrorDataVisita,
                view::hideErrorDataVisita
        );
    }

    boolean isValidOrarioVisita(PrenotazioneVisitaBean bean) {
        return validateField(
                isValidAppointmentTime(bean),
                view::showErrorOrarioVisita,
                view::hideErrorOrarioVisita
        );
    }

    boolean isValidSpecialista(PrenotazioneVisitaBean bean) {
        return validateStringField(
                bean.getSpecialista(),
                view::showErrorSpecialista,
                view::hideErrorSpecialista
        );
    }

    boolean isValidTipoVisita(PrenotazioneVisitaBean bean) {
        return validateStringField(
                bean.getTipoVisita(),
                view::showErrorTipoVisita,
                view::hideErrorTipoVisita
        );
    }

    boolean isValidMotivoVisita(PrenotazioneVisitaBean bean) {
        return validateStringField(
                bean.getMotivoVisita(),
                view::showErrorMotivoVisita,
                view::hideErrorMotivoVisita
        );
    }

    private boolean validateField(boolean condition, Runnable showError, Runnable hideError) {
        if (!condition) {
            showError.run();
            return false;
        }
        hideError.run();
        return true;
    }

    private boolean validateStringField(String value, Runnable showError, Runnable hideError) {
        return validateField(
                value != null && !value.trim().isEmpty(),
                showError,
                hideError
        );
    }

    private boolean isValidAppointmentTime(PrenotazioneVisitaBean bean) {
        return validateField(
                bean.getOrarioVisita() != null && isWithinBusinessHours(bean.getOrarioVisita()),
                view::showErrorOrarioVisita,
                view::hideErrorOrarioVisita
        );
    }

    private boolean isWithinBusinessHours(LocalTime time) {
        LocalTime opening = LocalTime.of(8, 0);
        LocalTime closing = LocalTime.of(20, 0);
        return !time.isBefore(opening) && !time.isAfter(closing);
    }
}