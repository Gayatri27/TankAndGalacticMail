package application;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import objects.GameObject;
import objects.Tank;

public class GameUtil {

	public static boolean noCollision(GameObject tank1, GameObject tank2) {
		if (tank1.getTankRectangle().intersects(tank2.getTankRectangle())) {
			return false;
		}
		return true;
	}

	public static boolean noCollisionNextMove(GameObject tank1, GameObject tank2) {
		if (tank1.getNextMoveTankRectangle().intersects(tank2.getNextMoveTankRectangle())) {
			return false;
		}
		return true;
	}

	public static boolean noCollision(GameObject tank, Rectangle rectangle) {
		if (tank.getTankRectangle().intersects(rectangle)) {
			return false;
		}
		return true;
	}

	public static boolean noCollisionNextMove(GameObject tank, Rectangle rectangle) {
		if (tank.getNextMoveTankRectangle().intersects(rectangle)) {
			return false;
		}
		return true;
	}

	public static boolean noCollision(GameObject tank, Rectangle2D rectangle) {
		if(tank.getAdjustedTankRectangleForBullets().intersects(rectangle)) {
			return false;
		}
		return true;
	}

	public static boolean noCollision(Rectangle rectangle, Rectangle2D rectangle2d) {
		if((rectangle).intersects(rectangle2d)) {
			return false;
		}
		return true;
	}
}
