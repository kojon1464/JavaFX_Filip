package zmuda.filip.javafx;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import zmuda.filip.javafx.Main.Lines;

public class Car extends Sprite {

	private double carX = 2000;
	private double carY;
	Lines line;

	public Car(Image image) {
		super(image);
	}

	public Car(Image image, double x) {
		super(image);
		this.carX = x;
		randomLine();
	}

	public void render(GraphicsContext gc, double carVelocity, double deltaTime) {
		this.carX -= this.line.velocity * deltaTime;
		this.carX -= carVelocity*deltaTime;
		super.render(gc, carX, this.carY);
	}

	public void check(GraphicsContext gc, Car[] objects) {
		Random random = new Random();
		if (this.carX < -300) {
			this.carX = 6000 + random.nextInt(300);
			randomLine();
			this.render(gc);
		} else if (this.carX > 6500) {
			this.carX = 6000 + random.nextInt(300);
			randomLine();
			this.render(gc);
		}
		this.canSpawn(objects);
	}

	public boolean canSpawn(Car[] objects) {
		boolean control = false;
		for (Car object : objects) {
			if (object != null) {
				if (!object.equals(this)) {
					if (object.intersects(this)) {
						control = true;
						this.carX += this.getWidth() + 10;
					}
				}
			}
		}
		return !control;
	}

	private void randomLine() {
		Random random = new Random();
		int control = random.nextInt(4);
		if (control == 0)
			this.line = Lines.FIRST;
		else if (control == 1)
			this.line = Lines.SECOND;
		else if (control == 2)
			this.line = Lines.THIRD;
		else
			this.line = Lines.FOURTH;

		this.carY = this.line.y + random.nextInt(50) - 25;
	}
}
