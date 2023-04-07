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

public class ClientGUI {
    private Stage primaryStage;
    private Client client;

    private PokerInfo pokerGame;


    public ClientGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //temp = new ListView<>();
    }

    public Scene clientWelcomeScene() {
        Text title = new Text("Welcome to 3 Card Poker!");
        Text connectPrompt = new Text("Connect to a Server");

        TextField IPInputField = new TextField();
        TextField portInputField = new TextField();

        IPInputField.setPromptText("IP Address");
        portInputField.setPromptText("Port Number");

        Button connectButton = new Button("Connect");
        connectButton.setOnAction(e-> {
            if (!IPInputField.getText().isEmpty() && !portInputField.getText().isEmpty()) {
                primaryStage.setScene(gameScene());
                client = new Client(data -> {
                    Platform.runLater(() -> {
                        //temp.getItems().add(data.toString());
                        pokerGame = (PokerInfo) data;
                        primaryStage.setScene(gameScene());
                    });
                }, IPInputField.getText(), portInputField.getText());
                client.start();
            }
        });

        Image pokerImage = new Image("Cards.png");
        ImageView pokerImageViewer = new ImageView(pokerImage);
        ImageView pokerImageViewer2 = new ImageView(pokerImage);

        VBox connectionPrompts = new VBox(20, connectPrompt, IPInputField, portInputField, connectButton);

        HBox promptsAndImages = new HBox(20, pokerImageViewer, connectionPrompts, pokerImageViewer2);

        VBox sceneOrganization = new VBox(title, promptsAndImages);

        BorderPane sceneLayout = new BorderPane(sceneOrganization);

        return new Scene(sceneLayout, 800, 800);
    }

    private Scene gameScene() {
        TextField input = new TextField();


        //if (pokerGame.hasDealerCards == )
        //Button send = new Button("Send");
        //send.setOnAction(e->client.send(input.getText()));

        return new Scene(new VBox(input), 800, 800);
    }


}
