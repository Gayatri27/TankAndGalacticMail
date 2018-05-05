package tanks;

import objects.GameObject;
import tanks.TanksWorld;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Wall extends GameObject {

  Wall(){ }

	public Wall(int x, int y, TanksWorld world) {
		try {
			image = ImageIO.read(new File("tanks/resources/wall_indestructible.png"));
      initialize(image, x, y, world);
		} catch (Exception e) {
			System.out.println("Exception while creating new wall object.");
		}
	}

	void initialize(Image image, int x, int y, TanksWorld world) {
      this.image = image;
      this.x = x;
      this.y = y;
      this.world = world;
      world.getCollisionTracker().addStaticObject(this);
      setRectangle(new Rectangle(x,y,image.getWidth(null), image.getHeight(null)));
  }



}