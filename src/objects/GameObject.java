package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameObject implements Observer {

  protected double x, y;

	public int angle;

	//public int health;
	private Rectangle rectangle;

	public Image image;
	private ImageObserver observer;

	public GameObject() {
	}

	public GameObject(String resourceLocation) throws IOException {
		this(resourceLocation, null);
	}

	public GameObject(Image image, double x, double y, int forwardKey, int backKey, int leftKey, int rightKey, int fireKey)
			throws IOException {
		this.x = x;
		this.y = y;
		this.image = image;
	}

	public GameObject(String resourceLocation, ImageObserver observer) throws IOException {
		x = 0;
		y = 0;

		image = ImageIO.read(new File(resourceLocation));
		this.observer = observer;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return (int) this.x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getAngle() {
		return this.angle;
	}


	/*
	public int getHealth() {
		return this.health;
	}
  public void setHealth(int health) {
    this.health = health;
  }
*/


  public int getWidth() {
		return image.getWidth(null);
	}

	public int getHeight() {
		return image.getHeight(null);
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void repaint(Graphics graphics) {
		// graphics.drawImage(image, x, y, observer);
	}

	@Override
	public void update(Observable o, Object arg) {

	}

	public void draw(ImageObserver observer, Graphics2D g) {
		g.drawImage(image, (int) x,  (int) y, observer);

	}

  public Rectangle getRectangle(){
    return rectangle;
  }

  public void setRectangle(Rectangle rectangle){
    this.rectangle = rectangle;
  }

	public Rectangle getTankRectangle() {
		return new Rectangle((int)x, (int)y, image.getWidth(null), image.getHeight(null));
	}

	public Rectangle getNextMoveTankRectangle() { //TODO fix nextY nextX
		return new Rectangle((int)x, (int)y, image.getWidth(null), image.getHeight(null));
	}


	public Rectangle getAdjustedTankRectangleForBullets() {
		int adjustedWidth = image.getWidth(null);
		int adjustedHeight = image.getWidth(null);
		return new Rectangle((int)x, (int)y, adjustedWidth, adjustedHeight);
	}


}
