package application;

import java.awt.Rectangle;

import objects.Tank;

public class GameUtil {

	public static boolean noCollision(Tank tank1, Tank tank2) {
		if (tank1.getTankRectangle().intersects(tank2.getTankRectangle())) {
			return false;
		}
		return true;
	}

	public static boolean noCollisionNextMove(Tank tank1, Tank tank2) {
		if (tank1.getNextMoveTankRectangle().intersects(tank2.getNextMoveTankRectangle())) {
			return false;
		}
		return true;
	}
	
	public static boolean noCollision(Tank tank, Rectangle rectangle) {
		if(tank.getTankRectangle().intersects(rectangle)) {
			return false;
		}
		return true;
	}
	
	public static boolean noCollisionNextMove(Tank tank, Rectangle rectangle) {
		if (tank.getNextMoveTankRectangle().intersects(rectangle)) {
			return false;
		}
		return true;
	}
}
