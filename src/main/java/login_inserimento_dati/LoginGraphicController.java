package login_inserimento_dati;

import homepage.Home;
import interfaccia_specialista.SpecialistDashboardView;
import javafx.stage.Stage;
import model.Paziente;
import model.Specialista;
import session_manager.SessionManagerPaziente;
import session_manager.SessionManagerSpecialista;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginGraphicController {
    private static final Logger LOGGER = Logger.getLogger(LoginGraphicController.class.getName());

    private final LoginViewBase view; // Riferimento alla vista
    private final LoginBean loginBean; // Bean per gestire i dati del login
    private final LoginAppController appController; // Controllore applicativo

    public LoginGraphicController(LoginViewBase view) {
        this.view = view;
        this.loginBean = new LoginBean(); // Inizializza il bean
        this.appController = new LoginAppController(); // Passa la vista al controllore applicativo
    }

    /**
     * Gestisce il tentativo di login: popola il bean e attiva il controllore applicativo.
     */
    public void handleLoginAttempt(String userType) {
        // Popola il bean con i dati della vista
        populateLoginBean();
        // Estrai i valori dal bean
        String email = loginBean.getEmailBean();
        String password = loginBean.getPasswordBean();

        // Verifica se i campi sono stati impostati correttamente
        if (email.isEmpty() || password.isEmpty()) {
            view.showError();
            LOGGER.log(Level.WARNING, "Tentativo di login fallito: campi email o password vuoti.");
            return;
        }

        // Attiva il controllore applicativo per verificare le credenziali
        Object user = appController.checkCredentials(userType, email, password);

        if (user != null) {
            loginBean.setLoginSuccess(true);
            loginBean.setUserType(userType);

            if (user instanceof Specialista) {
                // Imposta la sessione attiva dello specialista
                SessionManagerSpecialista.getInstance().setSpecialistaLoggato((Specialista) user);
                LOGGER.log(Level.INFO, "Sessione attiva impostata per lo specialista: {0}", ((Specialista) user).getEmail());
            } else if (user instanceof Paziente) {
                // Imposta la sessione attiva del paziente
                SessionManagerPaziente.setPazienteLoggato((Paziente) user);
                LOGGER.log(Level.INFO, "Sessione attiva impostata per il paziente: {0}", ((Paziente) user).getEmail());
            }

            // Registra il login riuscito
            LOGGER.log(Level.INFO, "Login riuscito per l'utente: {0}", email);
            handleSuccessfulLogin(); // Chiama il metodo per gestire il login riuscito
        } else {
            // Mostra un messaggio di errore
            view.showError();
            LOGGER.log(Level.WARNING, "Tentativo di login fallito per l'utente: {0}", email);
        }
    }

    /**
     * Preleva i valori dai campi della vista e li imposta nel bean.
     */
    private void populateLoginBean() {
        // Ottieni i valori dai campi della vista
        String email = view.getEmailField().getText().trim();
        String password = view.getPasswordField().getText();

        // Imposta i valori nel bean
        loginBean.setEmailBean(email);
        loginBean.setPasswordBean(password);

        // Logga i valori inseriti (solo per debug, non in produzione)
        LOGGER.log(Level.FINE, "Valori inseriti - Email: {0}, Password: [OMESSA]", email);
    }

    /**
     * Gestisce il login riuscito: apre la nuova interfaccia e chiude la finestra corrente.
     */
    private void handleSuccessfulLogin() {
        // Registra il login riuscito
        LOGGER.log(Level.INFO, "Login effettuato con successo per l'utente: {0}", loginBean.getEmailBean());

        // Determina il tipo di utente dal LoginBean
        String userType = loginBean.getUserType();

        // Crea una nuova finestra in base al tipo di utente
        Stage newStage = new Stage();
        if ("Specialist".equalsIgnoreCase(userType)) {
            // Mostra l'interfaccia per lo specialista
            LOGGER.log(Level.INFO, "Apertura dell'interfaccia per lo specialista.");
            openSpecialistInterface(newStage);
        } else if ("Patient".equalsIgnoreCase(userType)) {
            // Mostra l'interfaccia per il paziente
            LOGGER.log(Level.INFO, "Apertura dell'interfaccia per il paziente.");
            openPatientInterface(newStage);
        } else {
            // Gestisci un caso imprevisto (tipo di utente non valido)
            LOGGER.log(Level.SEVERE, "Tipo di utente non valido: {0}", userType);
            view.showError();
            return;
        }

        // Chiudi la finestra di login corrente
        Stage currentStage = (Stage) view.getEmailField().getScene().getWindow();
        currentStage.close();
    }

    /**
     * Apre l'interfaccia del paziente.
     */
    private void openPatientInterface(Stage stage) {
        try {
            Home homepageView = new Home();
            homepageView.start(stage); // Avvia la dashboard del paziente
            LOGGER.log(Level.INFO, "Interfaccia paziente caricata con successo.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'apertura dell'interfaccia per il paziente.", e);
            view.showError();
        }
    }

    /**
     * Apre l'interfaccia dello specialista.
     */
    private void openSpecialistInterface(Stage stage) {
        try {
            SpecialistDashboardView dashboardView = new SpecialistDashboardView();
            dashboardView.start(stage); // Avvia la dashboard dello specialista
            LOGGER.log(Level.INFO, "Interfaccia specialista caricata con successo.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'apertura dell'interfaccia per lo specialista.", e);
            view.showError();
        }
    }
}