package interfaccia_specialista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class SpecialistDashboardView {
    private final Stage primaryStage;
    private final SpecialistDashboardControllerApp controller;

    private Button[] mainButtons;
    private BorderPane layout;

    public SpecialistDashboardView(Stage primaryStage, SpecialistDashboardControllerApp controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        createUIComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void createUIComponents() {
        mainButtons = new Button[] {
                createButton("Visualizza Dati Pazienti"),
                createButton("Visualizza Visite della Settimana"),
                createButton("Visualizza Tutte le Visite"),
                createButton("Registra Nuova Visita")
        };
    }

    private void setupLayout() {
        layout = new BorderPane();
        layout.setTop(createHeader());
        layout.setCenter(createButtonContainer());
        layout.setBottom(createFooter());
    }

    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.getStyleClass().add("button");
        return btn;
    }

    private HBox createHeader() {
        Text headerText = new Text("Interfaccia Specialista");
        headerText.getStyleClass().add("header-text");

        HBox header = new HBox(headerText);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.getStyleClass().add("header");
        return header;
    }

    private VBox createButtonContainer() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));

        HBox row1 = createButtonRow(mainButtons[0]);
        HBox row2 = createButtonRow(mainButtons[1], mainButtons[2]);
        HBox row3 = createButtonRow(mainButtons[3]);

        container.getChildren().addAll(row1, row2, row3);
        return container;
    }

    private HBox createButtonRow(Button... buttons) {
        HBox row = new HBox(20, buttons);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(10));
        return row;
    }

    private HBox createFooter() {
        Button btnLogout = createButton("Log Out");
        btnLogout.getStyleClass().add("logout-button");
        btnLogout.setOnAction(e -> controller.handleLogout());

        HBox footer = new HBox(btnLogout);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.getStyleClass().add("footer");
        return footer;
    }

    private void setupEventHandlers() {
        mainButtons[0].setOnAction(e -> controller.handlePatientDataQuery());
        mainButtons[1].setOnAction(e -> controller.handleWeeklyVisits());
        mainButtons[2].setOnAction(e -> controller.handleAllVisits());
        mainButtons[3].setOnAction(e -> controller.handleNewVisit());
    }

    public void showDynamicView(String title, String description, List<String> content) {
        DynamicQueryView.show(
                new Stage(),
                title,
                description,
                content,
                () -> primaryStage.show()
        );
        primaryStage.hide();
    }

    public void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Scene getScene() {
        return new Scene(layout, 800, 600);
    }
}