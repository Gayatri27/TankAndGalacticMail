package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Observable;

public class Tank extends GameObject {

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
		int imageWidthScaled = image.getWidth(null);
		int imageHeightScaled = image.getHeight(null);
		int imageAngleScaled = angle * 64;
		g.drawImage(image, x, y, x + imageWidthScaled / 60, y + imageHeightScaled, imageAngleScaled, 0,
				imageAngleScaled + 64, imageHeightScaled, obs);
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
		return x + ((image.getWidth(null) / 60) / 2);
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
			double xAngle = 5 * Math.cos(Math.toRadians(6 * angle));
			x = x + (int) xAngle;
			double yAngle = 5 * Math.sin(Math.toRadians(6 * angle));
			y = y - (int) yAngle;
		}

		if (moveDown) {
			double xAngle = 5 * Math.cos(Math.toRadians(6 * angle));
			x = x - (int) xAngle;
			double yAngle = 5 * Math.sin(Math.toRadians(6 * angle));
			y = y + (int) yAngle;
		}

		if (angle == -1)
			angle = 59;
		else if (angle == 60)
			angle = 0;

		if (coolDown > 0) {
			moveLeft = false;
			moveRight = false;
			moveUp = false;
			moveDown = false;
		}
	}
}
