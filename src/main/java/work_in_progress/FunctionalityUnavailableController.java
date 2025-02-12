package work_in_progress;

import javafx.stage.Stage;
import java.util.Optional;

public class FunctionalityUnavailableController {

    private final Optional<Stage> previousStage;
    private final Stage currentStage;

    public FunctionalityUnavailableController(Stage previousStage, Stage currentStage) {
        this.previousStage = Optional.ofNullable(previousStage);
        this.currentStage = currentStage;
    }

    public void handleBackButton() {
        previousStage.ifPresent(Stage::show);
        currentStage.close();
    }
}
