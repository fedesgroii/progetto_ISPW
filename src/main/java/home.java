import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class home extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Home - MindLab");

        // Contenitore principale
        VBox container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #f2f2f2;");
        container.setAlignment(Pos.TOP_CENTER);

        // Header
        HBox header = new HBox();
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setPadding(new Insets(10, 0, 10, 0));
        header.setStyle("-fx-border-color: #ccc; -fx-border-width: 0 0 1 0;");

        // Carica le icone Font Awesome
        Image userIconImage = new Image(getClass().getResourceAsStream("/icons/icone/solid/user-large.svg"));
        ImageView userIcon = new ImageView(userIconImage);
        userIcon.setFitHeight(32);
        userIcon.setFitWidth(32);

        Image cogIconImage = new Image(getClass().getResourceAsStream("/icons/icone/solid/user-gear.svg"));
        ImageView cogIcon = new ImageView(cogIconImage);
        cogIcon.setFitHeight(32);
        cogIcon.setFitWidth(32);

        Image lockIconImage = new Image(getClass().getResourceAsStream("/icons/icone/solid/shield.svg"));
        ImageView lockIcon = new ImageView(lockIconImage);
        lockIcon.setFitHeight(32);
        lockIcon.setFitWidth(32);

        // Aggiungi le icone all'header
        header.getChildren().addAll(userIcon, cogIcon, lockIcon);

        // Benvenuto
        Text welcomeText = new Text("Ciao, Federico!");
        welcomeText.setFont(Font.font("Roboto", FontWeight.BOLD, 24));
        welcomeText.setStyle("-fx-fill: #333;");

        // Box pulsanti
        VBox boxContainer = new VBox();
        boxContainer.setSpacing(20);

        VBox prenotaBox = createBox("Prenota una visita",
                "Cerca il momento perfetto per la tua prossima visita",
                "#28a745");
        prenotaBox.setOnMouseClicked(event -> showAlert("Prenota una visita"));

        VBox storicoBox = createBox("Storico Visite",
                "Visualizza e gestisci le tue visite",
                "#28a745");
        storicoBox.setOnMouseClicked(event -> showAlert("Storico Visite"));

        VBox shopBox = createBox("Shop",
                "Scopri i nostri prodotti",
                "#ffffff", "#000000", "#000000");
        shopBox.setOnMouseClicked(event -> showAlert("Shop"));

        boxContainer.getChildren().addAll(prenotaBox, storicoBox, shopBox);

        // Footer
        HBox footer = new HBox();
        footer.setSpacing(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10, 0, 0, 0));
        footer.setStyle("-fx-border-color: #ccc; -fx-border-width: 1 0 0 0;");

        Text bachecaLink = createFooterLink("Bacheca");
        Text homeLink = createFooterLink("Home");
        homeLink.setStyle("-fx-fill: #28a745; -fx-font-weight: bold;");
        Text visiteLink = createFooterLink("Visite");

        footer.getChildren().addAll(bachecaLink, homeLink, visiteLink);

        // Aggiungere tutto al contenitore principale
        container.getChildren().addAll(header, welcomeText, boxContainer, footer);

        // Creare la scena
        Scene scene = new Scene(container, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createBox(String title, String description, String bgColor) {
        return createBox(title, description, bgColor, "white", "white");
    }

    private VBox createBox(String title, String description, String bgColor, String textColor, String borderColor) {
        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle(String.format(
                "-fx-background-color: %s; -fx-border-radius: 8px; -fx-background-radius: 8px; " +
                        "-fx-border-color: %s; -fx-text-fill: %s;",
                bgColor, borderColor, textColor));

        Text titleText = new Text(title);
        titleText.setFont(Font.font("Roboto", FontWeight.BOLD, 18));
        titleText.setStyle(String.format("-fx-fill: %s;", textColor));

        Text descriptionText = new Text(description);
        descriptionText.setStyle(String.format("-fx-fill: %s;", textColor));

        box.getChildren().addAll(titleText, descriptionText);
        box.setOnMouseEntered(event -> box.setStyle(
                String.format("-fx-background-color: %s; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-border-color: %s;" +
                                " -fx-text-fill: %s; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);",
                        bgColor, borderColor, textColor)));
        box.setOnMouseExited(event -> box.setStyle(
                String.format("-fx-background-color: %s; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-border-color: %s;" +
                        " -fx-text-fill: %s;", bgColor, borderColor, textColor)));

        return box;
    }

    private Text createFooterLink(String label) {
        Text link = new Text(label);
        link.setFont(Font.font("Roboto", FontWeight.NORMAL, 14));
        link.setStyle("-fx-fill: #333;");
        return link;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Azione");
        alert.setHeaderText(null);
        alert.setContentText("Hai cliccato su: " + message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
