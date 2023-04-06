import javafx.application.Application;
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


public class GUI extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Text title = new Text("3 Card Poker");
		Text serverText = new Text("Server");
		Text clientText = new Text("Client");

		Button serverButton = new Button();
		serverButton.setOnAction(e->{
			ServerGUI serverGUI = new ServerGUI(primaryStage);
			primaryStage.setScene(serverGUI.serverWelcomeScene());
		});

		Button clientButton = new Button();
		clientButton.setOnAction(e->{
			ClientGUI clientGUI = new ClientGUI(primaryStage);
			primaryStage.setScene(clientGUI.clientWelcomeScene());
		});

		HBox serverAndClientText = new HBox(50, serverText, clientText);
		HBox serverAndClientButtons = new HBox(50, serverButton, clientButton);

		VBox sceneLayout = new VBox(20, title, serverAndClientText, serverAndClientButtons);

		primaryStage.setScene(new Scene(sceneLayout, 800, 800));
		primaryStage.show();




		
				
		
	}

}
