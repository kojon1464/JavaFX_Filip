package zmuda.filip.javafx;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Game {

	static double width = 1200;
	static double height = 675;
	Group layout;
	Scene scene;
	AnimationTimer timer;
	private CollisionHandler handler;

	public enum Lines {
		FIRST(height / 9, 300), SECOND(height / 3, 450), THIRD(height / 20 * 11, 600), FOURTH(height / 4 * 3, 750);

		double y;
		double velocity;

		private Lines(double y, double velocity) {
			this.y = y;
			this.velocity = velocity;
		}
	}

	public Game() {
		layout = new Group();
		scene = new Scene(layout);
		Label label = new Label();

		Canvas canvas = new Canvas(width, height);
		layout.getChildren().addAll(canvas, label);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		Sprite road = new Sprite(
				new Image(getClass().getResource("road.png").toExternalForm(), width * 2, height, true, true));
		Sprite car = new Sprite(
				new Image(getClass().getResource("car.png").toExternalForm(), width / 8, height / 8, true, true));
		Car[] objects = new Car[20];
		initiateObjects(objects);

		final long startNanoTime = System.nanoTime();
		
		StringProperty distance = new SimpleStringProperty();
		label.textProperty().bind(distance);
		label.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

		ArrayList<KeyCode> input = new ArrayList<>();
		scene.setOnKeyPressed(e -> {
			if (!input.contains(e.getCode()))
				input.add(e.getCode());
		});

		scene.setOnKeyReleased(e -> {
			input.remove(e.getCode());
		});

		timer = new AnimationTimer() {

			double carVelocity = 500;
			double roadX = 0;
			double carY = (height - car.getHeight()) / 2;
			double currentTime = 0;
			double deltaTime;
			double distanceTraveled = 0;
			String stringDistance;
			
			
			@Override
			public void handle(long currentNanoTime) {

				if (input.contains(KeyCode.S))
					carY += carVelocity * deltaTime;
				if (input.contains(KeyCode.W))
					carY -= carVelocity * deltaTime;
				if (carY < 10)
					carY = 10;
				if (carY > height - car.getHeight() - 10)
					carY = height - car.getHeight() - 10;

				double time = (currentNanoTime - startNanoTime) / 1000000000.0;
				deltaTime = time - currentTime;
				currentTime = time;
				
				distanceTraveled += carVelocity * deltaTime/10;
				stringDistance = String.valueOf(distanceTraveled);
				distance.setValue(stringDistance.substring(0, stringDistance.indexOf('.')) + " m");

				roadX -= carVelocity * deltaTime;
				if (roadX <= -743)
					roadX = 0;

				road.render(gc, roadX, 0);
				car.render(gc, width / 6, carY);
				for (Car object : objects) {
					if (object != null)
						object.render(gc, carVelocity, deltaTime);
				}

				for (Car object : objects) {
					if (object != null) {
						if (object.intersects(car)){
							handler.handleCollision(stringDistance);
							stop();
						}
						object.check(gc, objects);
					}

				}
			}

		};
	}

	private void initiateObjects(Car[] objects) {
		objects[0] = new Car(
				new Image(getClass().getResource("car2.png").toExternalForm(), width / 8, height / 8, true, true),
				2000);
		objects[1] = new Car(
				new Image(getClass().getResource("car3.png").toExternalForm(), width / 8, height / 8, true, true),
				3000);
		objects[2] = new Car(
				new Image(getClass().getResource("car4.png").toExternalForm(), width / 8, height / 8, true, true),
				4000);
		objects[3] = new Car(
				new Image(getClass().getResource("car5.png").toExternalForm(), width / 8, height / 8, true, true),
				5000);
		objects[4] = new Car(
				new Image(getClass().getResource("car6.png").toExternalForm(), width / 8, height / 8, true, true),
				6000);
		objects[5] = new Car(
				new Image(getClass().getResource("car7.png").toExternalForm(), width / 8, height / 8, true, true),
				2350);
		objects[6] = new Car(
				new Image(getClass().getResource("car2.png").toExternalForm(), width / 8, height / 8, true, true),
				6350);
		objects[7] = new Car(
				new Image(getClass().getResource("car3.png").toExternalForm(), width / 8, height / 8, true, true),
				5350);
		objects[8] = new Car(
				new Image(getClass().getResource("car4.png").toExternalForm(), width / 8, height / 8, true, true),
				4350);
		objects[9] = new Car(
				new Image(getClass().getResource("car5.png").toExternalForm(), width / 8, height / 8, true, true),
				3350);
		objects[10] = new Car(
				new Image(getClass().getResource("car6.png").toExternalForm(), width / 8, height / 8, true, true),
				2700);
		objects[11] = new Car(
				new Image(getClass().getResource("car7.png").toExternalForm(), width / 8, height / 8, true, true),
				3700);
		objects[12] = new Car(
				new Image(getClass().getResource("car2.png").toExternalForm(), width / 8, height / 8, true, true),
				4700);
		objects[13] = new Car(
				new Image(getClass().getResource("car4.png").toExternalForm(), width / 8, height / 8, true, true),
				5700);
		objects[14] = new Car(
				new Image(getClass().getResource("car7.png").toExternalForm(), width / 8, height / 8, true, true),
				6700);
	}

	public Scene getScene() {
		return scene;
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}
	
	public void setCollisionHandler(CollisionHandler handler){
		this.handler = handler;
	}

}
