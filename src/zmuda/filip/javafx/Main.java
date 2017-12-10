package zmuda.filip.javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	String stringDistance;

	Game game;
	Stage window;

	VBox panel;
	VBox background;
	Button startButton;
	Button helpButton;
	Button exitButton;
	Scene scene;
	Label label = new Label("Don't crash!");
	Label label2 = new Label();
	VBox labelGroup = new VBox(label);

	Sprite road;

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
		exitButton = new Button("Exit");
		scene = new Scene(panel);
		road = new Sprite(
				new Image(getClass().getResource("road.png").toExternalForm(), width * 2, height, true, true));
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

		startButton.setPrefSize(width / 6, height / 9);
		startButton.setOnAction(e -> handleStart());

		helpButton.setPrefSize(width / 6, height / 9);
		helpButton.setOnAction(e -> handleHelp());

		exitButton.setPrefSize(width / 6, height / 9);
		exitButton.setOnAction(e -> window.close());

		labelGroup.setAlignment(Pos.CENTER);

		background.getChildren().addAll(labelGroup, startButton, helpButton, exitButton);
		background.setAlignment(Pos.CENTER);
		background.setSpacing(height / 40);
		background.setMaxWidth(width / 4);
		background.setMinHeight(height / 1.75);
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

	private void handleHelp() {
		VBox root = new VBox();
		VBox helpBackground = new VBox();
		Label helpLabel = new Label("Controls:\nW - move up\nS - move down\n\nDrive as far as you can without crashing!\n\nHint: Sometimes you can fit between lanes");
		Scene helpScene = new Scene(root);
		root.setBackground(new Background(new BackgroundImage(road.image, null, null, null, BackgroundSize.DEFAULT)));
		root.getChildren().add(helpBackground);
		root.setMinSize(width, height);
		root.setMaxSize(width, height);
		root.setAlignment(Pos.CENTER);
		
		helpBackground.setAlignment(Pos.CENTER);
		helpBackground.setSpacing(height / 40);
		helpBackground.setMaxWidth(width / 1.75);
		helpBackground.setMinHeight(height / 1.5);
		helpBackground.setBackground(new Background(new BackgroundFill(Color.CORNSILK, new CornerRadii(50), null)));

		Button backButton = new Button("Back");
		backButton.setPrefSize(width / 6, height / 9);
		backButton.setOnAction(e -> {
			if (stringDistance == null)
				window.setScene(scene);
			else
				handleEnd(stringDistance);
		});
		helpBackground.getChildren().addAll(helpLabel, backButton);
		helpScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		window.setScene(helpScene);

	}

	private void handleStart() {
		game = new Game();
		window.setScene(game.getScene());
		game.setCollisionHandler(distance -> handleEnd(distance));
		game.start();

	}

	private void handleEnd(String distance) {
		window.setScene(scene);
		startButton.setText("New Game");
		label.setText("Crasched after");
		stringDistance = distance;
		label2.setText(distance.substring(0, distance.indexOf('.')) + " m");
		if (labelGroup.getChildren().contains(label2) == false)
			labelGroup.getChildren().add(label2);
		background.setSpacing(height / 50);
	}

}
