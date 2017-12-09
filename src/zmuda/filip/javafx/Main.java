package zmuda.filip.javafx;

import com.sun.java.swing.plaf.windows.WindowsOptionPaneUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	static double width = 1200;
	static double height = 675;
	Game game;
	Stage window;
	
	VBox panel;
	VBox background;
	Button startButton;
	Button helpButton;
	Scene scene;

	public enum Lines {
		FIRST(height / 9, 300), SECOND(height / 3, 450), THIRD(height / 20 * 11, 600), FOURTH(height / 4 * 3, 750);

		double y;
		double velocity;
		
		

		private Lines(double y, double velocity) {
			this.y = y;
			this.velocity = velocity;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {

		this.window = window;

		panel = new VBox();
		background = new VBox();
		startButton = new Button("Start");
		helpButton = new Button("Help");
		scene = new Scene(panel);
		Sprite road = new Sprite(
				new Image(getClass().getResource("road.png").toExternalForm(), width * 2, height, true, true));
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		
		startButton.setPrefSize(width / 6, height / 9);
		startButton.setOnAction(e -> handleStart());

		helpButton.setPrefSize(width / 6, height / 9);

		background.getChildren().addAll(startButton, helpButton);
		background.setAlignment(Pos.CENTER);
		background.setSpacing(height / 20);
		background.setMaxWidth(width/4);
		background.setMinHeight(height/2);
		background.setBackground(new Background(new BackgroundFill(Color.CORNSILK, new CornerRadii(50), null)));
		
		panel.getChildren().add(background);
		panel.setAlignment(Pos.CENTER);
		panel.setMinSize(width, height);
		panel.setMaxSize(width, height);
		panel.setBackground(new Background(new BackgroundImage(road.image, null, null, null, BackgroundSize.DEFAULT)));

		window.setScene(scene);
		window.setTitle("My first game");
		window.setResizable(false);
		window.sizeToScene();
		window.show();
	}

	private void handleStart() {
		game = new Game();
		window.setScene(game.getScene());
		game.setCollisionHandler(distance -> handleEnd(distance));
		game.start();

	}

	private Object handleEnd(double distance) {
		window.setScene(scene);
		startButton.setText(String.valueOf(distance));
		return null;
	}

}
