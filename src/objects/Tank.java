package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Observable;

public class Tank extends GameObject {

	private final int TOTAL_IMAGES_IN_A_SPRITE = 60;
	private final int ANGLE_STEP_SIZE = 6;
	private final int SPEED_STEP_SIZE = 5;
	private final int IMAGE_ROTATION_STEP = 64;

	private final int forwardKey, backKey, leftKey, rightKey, fireKey;

	private boolean moveLeft, moveRight, moveUp, moveDown;
	private int angle = 0, coolDown = 0;

	public Tank(Image image, int x, int y, int forwardKey, int backKey, int leftKey, int rightKey, int fireKey)
			throws IOException {
		super(image, x, y, forwardKey, backKey, leftKey, rightKey, fireKey);
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.fireKey = fireKey;
	}

	public void draw(ImageObserver obs, Graphics2D g) {
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		int imageAngle = angle * IMAGE_ROTATION_STEP;

		// This is drawImage from Graphics class in java
		// https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
		//
		// 	drawImage(Image img, int dx1, int dy1,
		//											 int dx2, int dy2,
		// 											 int sx1, int sy1,
		//											 int sx2, int sy2,
		//											 ImageObserver observer)
		//
		//	img - the specified image to be drawn. This method does nothing if img is null.
		//	dx1 - the x coordinate of the first corner of the destination rectangle.
		//	dy1 - the y coordinate of the first corner of the destination rectangle.
		//	dx2 - the x coordinate of the second corner of the destination rectangle.
		//	dy2 - the y coordinate of the second corner of the destination rectangle.
		//	sx1 - the x coordinate of the first corner of the source rectangle.
		//	sy1 - the y coordinate of the first corner of the source rectangle.
		//	sx2 - the x coordinate of the second corner of the source rectangle.
		//	sy2 - the y coordinate of the second corner of the source rectangle.
		//	observer - object to be notified as more of the image is scaled and converted.

		g.drawImage(image, x, y,
				x + imageWidth / TOTAL_IMAGES_IN_A_SPRITE, y + imageHeight,
				imageAngle, 0,
				imageAngle + IMAGE_ROTATION_STEP, imageHeight,
				obs);
	}

	@Override
	public void update(Observable obj, Object arg) {

		GameEvents gameEvents = (GameEvents) arg;
		KeyEvent e = (KeyEvent) gameEvents.event;
		if (e.getKeyCode() == forwardKey) {
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				// x -= speed;
				moveUp = false;
			} else if (e.getID() == KeyEvent.KEY_PRESSED) {
				moveUp = true;
			}
		} else if (e.getKeyCode() == backKey) {
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				// x -= speed;
				moveDown = false;
			} else if (e.getID() == KeyEvent.KEY_PRESSED) {
				moveDown = true;
			}
		} else if (e.getKeyCode() == leftKey) {
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				// x -= speed;
				moveLeft = false;
			} else if (e.getID() == KeyEvent.KEY_PRESSED) {
				moveLeft = true;
			}
		} else if (e.getKeyCode() == rightKey) {
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				// x -= speed;
				moveRight = false;
			} else if (e.getID() == KeyEvent.KEY_PRESSED) {
				moveRight = true;
			}
		} else if (e.getKeyCode() == fireKey) {
			System.out.println("ABC");
		}
	}

	public int getAngle() {
		return angle;
	}

	public int getTankCenterX() {
		return x + ((image.getWidth(null) / TOTAL_IMAGES_IN_A_SPRITE) / 2);
	}

	public int getTankCenterY() {
		return y + (image.getHeight(null) / 2);
	}

	public void updateMove() {
		if (moveLeft) {
			angle += 1;
		}

		if (moveRight) {
			angle -= 1;
		}

		if (moveUp) {
			double xAngle = SPEED_STEP_SIZE * Math.cos(Math.toRadians(ANGLE_STEP_SIZE * angle));
			x = x + (int) xAngle;
			double yAngle = SPEED_STEP_SIZE * Math.sin(Math.toRadians(ANGLE_STEP_SIZE * angle));
			y = y - (int) yAngle;
		}

		if (moveDown) {
			double xAngle = SPEED_STEP_SIZE * Math.cos(Math.toRadians(ANGLE_STEP_SIZE * angle));
			x = x - (int) xAngle;
			double yAngle = SPEED_STEP_SIZE * Math.sin(Math.toRadians(ANGLE_STEP_SIZE * angle));
			y = y + (int) yAngle;
		}

		// Angle can go only from 0 to 59
		//
		// 360 is the total rotation, and we have 60 images in a sprite,
		// hence, angle needs to change in steps of 6
		if (angle == -1)
			angle = 59;
		else if (angle == TOTAL_IMAGES_IN_A_SPRITE)
			angle = 0;

		if (coolDown > 0) {
			moveLeft = false;
			moveRight = false;
			moveUp = false;
			moveDown = false;
		}
	}
}
