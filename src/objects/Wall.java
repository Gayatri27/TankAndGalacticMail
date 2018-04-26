package objects;

import application.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Wall extends GameObject{

  World world;
  Wall(){ // why do we need this? giving error on destructable wall
  }

	public Wall(int x, int y, World world) {
		try {
			image = ImageIO.read(new File("resources/wall_indestructible.png"));
      initialize(image, x, y, world);
		} catch (Exception e) {
			System.out.println("Exception while creating new wall object.");
		}
	}

	void initialize(Image image, int x, int y, World world) {
      this.image = image;
      this.x = x;
      this.y = y;
      this.world = world;
      world.getCollisionTracker().addStaticObject(this);
      setRectangle(new Rectangle(x,y,image.getWidth(null), image.getHeight(null)));
  }



}