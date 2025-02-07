package interfaccia_specialista;

import javafx.stage.Stage;
import java.util.List;

public class SpecialistDashboardGraphicController {
    private final Stage primaryStage;
    private final SpecialistDashboardView view;

    public SpecialistDashboardGraphicController(Stage primaryStage, SpecialistDashboardView view) {
        this.primaryStage = primaryStage;
        this.view = view;
    }

    public void showDynamicView(String title, String description, List<String> content) {
        view.showDynamicView(title, description, content);
        primaryStage.hide();
    }

    public void showErrorAlert(String title, String message) {
        view.showErrorAlert(title, message);
    }

    public void closeApplication() {
        primaryStage.close();
    }

    public void showMainView() {
        primaryStage.show();
    }
}