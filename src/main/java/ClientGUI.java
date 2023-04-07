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

import java.util.ArrayList;

public class ClientGUI {
    private Stage primaryStage;
    private Client client;

    private PokerInfo pokerGame;

    private ArrayList<ImageView> cardImages;


    public ClientGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.cardImages = new ArrayList<>();

        for (int i = 1; i <= 52; i++) {
            Image temp = new Image(i + ".png");
            cardImages.add(new ImageView(temp));
        }
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
                //primaryStage.setScene(gameScene());
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

        VBox dealer;
        VBox player;

        if (pokerGame.gamePhase == 1) {
            Image backofCard = new Image("BackOfCard.png");
            ImageView backOfCardView1 = new ImageView(backofCard);
            ImageView backOfCardView2 = new ImageView(backofCard);
            ImageView backOfCardView3 = new ImageView(backofCard);
            ImageView backOfCardView4 = new ImageView(backofCard);
            ImageView backOfCardView5 = new ImageView(backofCard);
            ImageView backOfCardView6 = new ImageView(backofCard);

            HBox dealerSide = new HBox(20, backOfCardView1, backOfCardView2, backOfCardView3);
            Text dealerLabel = new Text("Dealer Hand");
            dealer = new VBox(10, dealerSide, dealerLabel);

            HBox playerSide = new HBox(20, backOfCardView4, backOfCardView5, backOfCardView6);
            Text playerLabel = new Text("Player Hand");
            player = new VBox(10, playerLabel, playerSide);

            Text anteText = new Text("Place your ante wager $5-$25");
            TextField anteRequest = new TextField();
            anteRequest.setPromptText("Required");

            Text pairText = new Text("Pair Plus Wager? $5-$25");
            TextField pairRequest = new TextField();
            pairRequest.setPromptText("Optional");

            Button start = new Button("Start");
            start.setOnAction(e->{
                if (!anteRequest.getText().isEmpty() && !anteRequest.getText().matches(".*\\D.*") && Integer.valueOf(anteRequest.getText()) >= 5 && Integer.valueOf(anteRequest.getText()) <= 25) {
                    pokerGame.ante = Integer.valueOf(anteRequest.getText());
                    if (!pairRequest.getText().isEmpty() && !pairRequest.getText().matches(".*\\D.*") && Integer.valueOf(pairRequest.getText()) >= 5 && Integer.valueOf(pairRequest.getText()) <= 25) {
                        pokerGame.isPairPlus = true;
                        pokerGame.pairPlusAmount = Integer.valueOf(pairRequest.getText());
                    }
                    client.send(pokerGame);
                }
            });

            VBox sceneOrganization = new VBox(10, dealer, anteText, anteRequest, pairText, pairRequest, start, player);

            BorderPane sceneLayout = new BorderPane(sceneOrganization);

            return new Scene(sceneLayout, 800, 800);
        }
        return new Scene(new Button("hi"), 800, 800);
    }


}
