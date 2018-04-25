package objects.bullets;

import graphics.Sprite;
import objects.Tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class TankBullet extends AbstractBullet {

	Sprite sprite;
	Tank tank;

	private final int SPEED = 5;

	private final int ANGLE_STEP_SIZE = 6;

	int angle;

	public double x, y;
	int dx, dy;

	public TankBullet(Tank tank, int x, int y, int angle) {

		this.tank = tank;
		this.x = x;
		this.y = y;
		this.angle = angle;

		try {
			sprite = new Sprite("resources/Shell_light_strip60.png", 24);
			image = sprite.getFrame(10);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void draw(ImageObserver obs, Graphics2D g) {
		g.drawImage(image, ((int) x), ((int) y), obs);
	}

	@Override
	public void update(Observable o, Object arg) {
		x += 1 * SPEED * Math.cos(Math.toRadians(ANGLE_STEP_SIZE * angle));
		y += -1 * SPEED * Math.sin(Math.toRadians(ANGLE_STEP_SIZE * angle));
	}

	public Tank getTank() {
		return this.tank;
	}
}
