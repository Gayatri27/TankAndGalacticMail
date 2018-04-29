package objects;

import application.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class DestructibleWall extends Wall implements Destroyable {

  private int health = 30;


  public DestructibleWall(int x, int y, World world) {
    try {
      image = ImageIO.read(new File("resources/wall.png"));
      initialize(image, x, y, world);
    } catch (Exception e) {
      System.out.println("Exception while creating new wall object.");
    }
  }

  @Override
  public void setHealth(int health) {
    this.health = health;
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public void reduceHealth(int amount) {
    health -= amount;
    if(health <= 0){
      world.removeWall(this);
      world.getCollisionTracker().removeStaticObject(this);
    }
  }

}
