package interfaccia_specialista;

import javafx.stage.Stage;
import login.LoginView;
import java.util.logging.Logger;
import session_manager.SessionManagerSpecialista;
import model.Specialista;

/**
 * Controller applicativo per la Dashboard dello Specialista.
 * Si occupa di verificare l'autenticazione dello specialista in sessione e gestire il logout.
 */
public class SpecialistDashboardControllerApp {

    private static final Logger logger = Logger.getLogger(SpecialistDashboardControllerApp.class.getName());
    private final SpecialistDashboardView view;

    /**
     * Costruttore: verifica che lo specialista sia autenticato.
     *
     * @param view  La view della dashboard.
     * @param stage Lo stage corrente della dashboard.
     * @throws IllegalStateException Se nessuno specialista è loggato.
     */
    public SpecialistDashboardControllerApp(SpecialistDashboardView view, Stage stage) {
        this.view = view;

        if (!SessionManagerSpecialista.getInstance().isLoggedIn()) {
            handleUnauthenticatedUser(stage);
        }

        Specialista loggedSpecialista = SessionManagerSpecialista.getInstance().getSpecialistaLoggato();
        logger.info("Specialista autenticato: " + loggedSpecialista.getNome() + " " + loggedSpecialista.getCognome());
    }

    /**
     * Gestisce la procedura di logout.
     *
     * @param stage Lo stage corrente da chiudere.
     */
    public void handleLogout(Stage stage) {
        SessionManagerSpecialista.getInstance().resetSession();
        logger.info("Logout effettuato.");
        redirectToLogin(stage);
    }

    /**
     * Reindirizza l'utente alla pagina di login e chiude lo stage corrente.
     *
     * @param stage Lo stage corrente da chiudere.
     */
    private void redirectToLogin(Stage stage) {
        try {
            LoginView loginView = new LoginView();
            Stage loginStage = new Stage();
            loginView.start(loginStage);
            closeStage(stage);
        } catch (Exception e) {
            logger.severe("Errore durante l'avvio della finestra di login: " + e.getMessage());
            throw new RuntimeException("Impossibile avviare la finestra di login.", e);
        }
    }

    /**
     * Gestisce il caso in cui l'utente non è autenticato.
     *
     * @param stage Lo stage corrente da chiudere.
     */
    private void handleUnauthenticatedUser(Stage stage) {
        logger.severe("Nessuno specialista loggato. Reindirizzamento alla pagina di login.");
        redirectToLogin(stage);
        throw new IllegalStateException("Nessuno specialista loggato. Effettua il login.");
    }

    /**
     * Chiude lo stage corrente se non è nullo.
     *
     * @param stage Lo stage da chiudere.
     */
    private void closeStage(Stage stage) {
        if (stage == null) {
            logger.warning("Tentativo di chiudere uno stage nullo.");
            return;
        }
        stage.close();
    }
}