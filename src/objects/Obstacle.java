package objects;

import java.awt.Rectangle;

public class Obstacle {

	private final Rectangle rectangle;
	private final boolean isDestructible;
	
	public Obstacle(Rectangle rectangle, boolean isDestructible) {
		this.rectangle = rectangle;
		this.isDestructible = isDestructible;
	}
	
	public boolean isDestructable() {
		return this.isDestructible;
	}
	
	public Rectangle getRectangle() {
		return this.rectangle;
	}
}
