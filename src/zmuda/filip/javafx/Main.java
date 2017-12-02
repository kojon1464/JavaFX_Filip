package zmuda.filip.javafx;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {

	double width = 1200;
	double height = 675;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {
		Group layout = new Group();
		Scene scene = new Scene(layout);

		Canvas canvas = new Canvas(width, height);
		layout.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		Sprite road = new Sprite(new Image(getClass().getResource("road.png").toExternalForm(), width * 2, height, true, true));
		Sprite car = new Sprite(new Image(getClass().getResource("car.png").toExternalForm(), width / 8, height / 8, true, true));
		Sprite[] object = new Sprite[10];
		object[0] = new Sprite(new Image(getClass().getResource("car.png").toExternalForm(), width / 8, height / 8, true, true));

		final long startNanoTime = System.nanoTime();

		ArrayList<KeyCode> input = new ArrayList<>();
		scene.setOnKeyPressed(e -> {
			if (!input.contains(e.getCode()))
				input.add(e.getCode());
		});

		scene.setOnKeyReleased(e -> {
			input.remove(e.getCode());
		});
		
		AnimationTimer timer = new AnimationTimer() {

			double objectsX = width * 1.5;
			double carVelocity = 0;
			double roadX = 0;
			double carY = (height - car.getHeight())/2;
			double currentTime = 0;
			double deltaTime;

			@Override
			public void handle(long currentNanoTime) {

				if (input.contains(KeyCode.D))
					carVelocity += 12*deltaTime;
				if (input.contains(KeyCode.A))
					carVelocity -= 28*deltaTime;
				if(!input.contains(KeyCode.A) && !input.contains(KeyCode.D))
					carVelocity -=4*deltaTime;
				if(carVelocity < 0)
					carVelocity = 0;
				if(carVelocity > 40)
					carVelocity = 40;
				
				if(input.contains(KeyCode.S))
					carY += carVelocity/2;
				if(input.contains(KeyCode.W))
					carY -= carVelocity/2;
				if(carY < 10)
					carY = 10;
				if(carY > height - car.getHeight() - 10)
					carY = height - car.getHeight() - 10;

				double time = (currentNanoTime - startNanoTime) / 1000000000.0;
				deltaTime = time - currentTime;
				currentTime = time;

				roadX -= carVelocity;
				if (roadX <= -745)
					roadX = 0;
				objectsX -= carVelocity;

				road.render(gc, roadX, 0);
				car.render(gc, width/6, carY);
				object[0].render(gc, objectsX, height/4);
			}

		};
		timer.start();

		window.setScene(scene);
		window.setTitle("My first game");
		window.setResizable(false);
		window.sizeToScene();
		window.show();
	}

}
