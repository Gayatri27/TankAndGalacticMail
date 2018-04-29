package objects.bullets;

import application.CollisionTracker;
import application.World;
import objects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Observable;

public class TankBullet extends AbstractBullet {

	Sprite sprite;
	GameObject tank;
	World world;

	private final int SPEED = 15;
  private final int DAMAGE = 10;
  private final double DISTANCE_FACTOR = 1.6;

  private final String SPRITE_FILE = "resources/Shell_basic_strip60.png";
  private final int SPRITE_TILE_SIZE = 24;
  private final int SPRITE_NUM_IMAGES = 60;


  private final int ANGLE_STEP_SIZE = 6;

	int angle;

	int dx, dy;

  private static CollisionTracker collisionTracker;


  public TankBullet(GameObject tank, World world) {

    try {
      sprite = new Sprite(SPRITE_FILE, SPRITE_TILE_SIZE);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    this.tank = tank;

		this.angle = tank.getAngle();

    this.x = tank.getX() + tank.getWidth()/2
            - sprite.getTileSize()/2
            + DISTANCE_FACTOR * tank.getWidth()/2 * Math.cos(Math.toRadians(tank.getAngle())) ;

    this.y = tank.getY() + tank.getHeight()/2
            - sprite.getTileSize()/2
            -  DISTANCE_FACTOR * tank.getHeight()/2 * Math.sin(Math.toRadians(tank.getAngle()));

    this.world = world;
    collisionTracker = world.getCollisionTracker();

	}

	public void draw(ImageObserver obs, Graphics2D g) {
    g.drawImage(sprite.getFrame((angle/(360/SPRITE_NUM_IMAGES))), ((int) x), ((int) y),obs);
  }

	@Override
	public void update(Observable o, Object arg) {

    moveBullet(1 * SPEED * Math.cos(Math.toRadians(angle)) ,
            -1 * SPEED * Math.sin(Math.toRadians(angle)));
	}


  public void moveBullet(double dx, double dy){
    GameObject collidedWith = collisionTracker.collides(this, dx, dy);

    if(collidedWith == null){
      // System.out.println("Moving bullet");
      x += dx;
      y += dy;

      //setRectangle(new Rectangle((int)x, (int)y, sprite.getTileSize(), sprite.getTileSize()));
    } else {
      if(collidedWith instanceof Destroyable){
        ((Destroyable) collidedWith).reduceHealth(DAMAGE);
      }

      if(collidedWith instanceof Wall || collidedWith instanceof SpriteTank){
        world.addExplosion(getX() + (int) dx, getY() + (int) dy);
      }

      world.removeBullet(this);
    }
  }




  public int getWidth() {
    return sprite.getTileSize();
  }

  public int getHeight() {
    return sprite.getTileSize();
  }

	public GameObject getTank() {
		return this.tank;
	}
}
