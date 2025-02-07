package interfaccia_specialista;

import storage_db.DatabaseOperations;
import storage_db.DatabaseOperations.DatabaseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecialistDashboardControllerApp {
    private static final Logger logger = Logger.getLogger(SpecialistDashboardControllerApp.class.getName());
    private final SpecialistDashboardView view;

    public SpecialistDashboardControllerApp(SpecialistDashboardView view) {
        this.view = view;
    }

    public void handlePatientDataQuery() {
        try {
            List<Map<String, Object>> patientData = DatabaseOperations.executeQuery(
                    "SELECT numeroTesseraSanitaria, nome, cognome, dataDiNascita, " +
                            "numeroTelefonico, email, condizioniMediche, password FROM pazienti"
            );

            List<String> formattedData = new ArrayList<>();
            for (Map<String, Object> row : patientData) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    sb.append(entry.getKey())
                            .append(": ")
                            .append(entry.getValue())
                            .append(" | ");
                }
                formattedData.add(sb.toString().replaceAll(" \\| $", ""));
            }

            view.showDynamicView("Dati Pazienti", "Elenco dei pazienti registrati nel sistema:", formattedData);

        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, "Errore nel recupero dati pazienti", e);
            view.showErrorAlert("Errore Database", "Impossibile recuperare i dati dei pazienti.");
        }
    }

    public void handleLogout() {
        // Logica per il logout
    }

    public void handleWeeklyVisits() { /* Implementazione */ }
    public void handleAllVisits() { /* Implementazione */ }
    public void handleNewVisit() { /* Implementazione */ }
}