import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ServerGUI {

    private Stage primaryStage;
    private ListView<String> messages;
    private Server server;
    public ServerGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        messages = new ListView<>();
    }
    public Scene serverWelcomeScene() {
        Text serverWelcome = new Text("Welcome to the Server!");

        Text portInputText = new Text("Enter Port Number");
        TextField portInput = new TextField();

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e->{
            if (!portInput.getText().isEmpty()) {
                primaryStage.setScene(serverDataScene());
                server = new Server(data -> {
                    Platform.runLater(() -> {
                        messages.getItems().add(data.toString());
                    });
                }, portInput.getText());
            }
        });

        Image pokerImage = new Image("Cards.png");
        ImageView pokerImageViewer = new ImageView(pokerImage);
        ImageView pokerImageViewer2 = new ImageView(pokerImage);

        VBox portTextAndField = new VBox(20, portInputText, portInput, continueButton);
        HBox portAndImages = new HBox(20, pokerImageViewer, portTextAndField, pokerImageViewer2);

        VBox sceneOrganization = new VBox(20, serverWelcome, portAndImages);

        BorderPane sceneLayout = new BorderPane();
        sceneLayout.setCenter(sceneOrganization);

        return new Scene(sceneLayout, 800, 800);
    }

    private Scene serverDataScene() {
        Text title = new Text("Server Database");
        Button offButton = new Button("Off");
        //TextField temp = new TextField();
        //offButton.setOnAction(e->server.clients.get(0).send(temp.getText()));

        HBox titleAndButton = new HBox(20, title, offButton);

        VBox sceneOrganization = new VBox(20, titleAndButton, messages);

        BorderPane sceneLayout = new BorderPane(sceneOrganization);

        return new Scene(sceneLayout, 800, 800);
    }
}
