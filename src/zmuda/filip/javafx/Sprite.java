package zmuda.filip.javafx;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

	protected Image image;
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
		if(spr.getBoundary().intersects(this.getBoundary()))
			return isPixelCollide(this.x, this.y, this.image, spr.x, spr.y, spr.image);
		return false;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public boolean isPixelCollide(double x1, double y1, Image image1, double x2, double y2,
			Image image2) {
		// initialization
		double width1 = x1 + image1.getWidth() - 1, height1 = y1 + image1.getHeight() - 1,
				width2 = x2 + image2.getWidth() - 1, height2 = y2 + image2.getHeight() - 1;

		int xstart = (int) Math.max(x1, x2), ystart = (int) Math.max(y1, y2), xend = (int) Math.min(width1, width2),
				yend = (int) Math.min(height1, height2);

		// intersection rect
		int toty = Math.abs(yend - ystart);
		int totx = Math.abs(xend - xstart);

		for (int y = 1; y < toty - 1; y++) {
			int ny = Math.abs(ystart - (int) y1) + y;
			int ny1 = Math.abs(ystart - (int) y2) + y;

			for (int x = 1; x < totx - 1; x++) {
				int nx = Math.abs(xstart - (int) x1) + x;
				int nx1 = Math.abs(xstart - (int) x2) + x;
				try {
					if (((image1.getPixelReader().getArgb(nx, ny) & 0xFF000000) != 0x00)
							&& ((image2.getPixelReader().getArgb(nx1, ny1) & 0xFF000000) != 0x00)) {
						// collide!!
						return true;
					}
				} catch (Exception e) {
					// System.out.println("s1nx+","+ny+" - s2 = "+nx1+","+ny1);
				}
			}
		}

		return false;
	}

}
