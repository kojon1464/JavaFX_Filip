package zmuda.filip.javafx;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

	private Image image;
	private double x;
	private double y;
	private double width;
	private double height;

	public Sprite(Image image) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void render(GraphicsContext gc, double x, double y) {
		this.x = x;
		this.y = y;
		gc.drawImage(image, x, y);
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(x, y, width, height);
	}

	public boolean intersects(Sprite spr) {
		return spr.getBoundary().intersects(this.getBoundary());
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

}
