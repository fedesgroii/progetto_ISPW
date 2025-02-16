package work_in_progress;

import javafx.stage.Stage;

public class FunctionalityUnavailableController {

    private final Stage previousStage;
    private final Stage currentStage;

    public FunctionalityUnavailableController(Stage previousStage, Stage currentStage) {
        this.previousStage = previousStage;
        this.currentStage = currentStage;
    }

    public void handleBackButton() {
        if (previousStage != null) {
            previousStage.show(); // Mostra la finestra precedente se esiste
        }
        currentStage.close(); // Chiude la finestra corrente
    }
}
