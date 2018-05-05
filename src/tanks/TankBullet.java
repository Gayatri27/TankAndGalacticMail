package tanks;

import objects.AbstractBullet;
import objects.*;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class TankBullet extends AbstractBullet {

	private Sprite sprite;


  private final String SPRITE_FILE = "tanks/resources/Shell_basic_strip60.png";
  private final int SPRITE_TILE_SIZE = 24;
  private final int SPRITE_NUM_IMAGES = 60;

  public TankBullet(GameObject tank, TanksWorld world) {

    speed_moving = 15;
    damage = 10;
    distanceFactor = 1.6;

    try {
      sprite = new Sprite(SPRITE_FILE, SPRITE_TILE_SIZE);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

		this.angle = tank.getAngle();

    this.x = tank.getX() + tank.getWidth()/2
            - sprite.getTileSize()/2
            + distanceFactor * tank.getWidth()/2 * Math.cos(Math.toRadians(tank.getAngle())) ;

    this.y = tank.getY() + tank.getHeight()/2
            - sprite.getTileSize()/2
            -  distanceFactor * tank.getHeight()/2 * Math.sin(Math.toRadians(tank.getAngle()));

    this.world = world;
    collisionTracker = world.getCollisionTracker();

	}

  @Override
	public void draw(ImageObserver obs, Graphics2D g) {
    g.drawImage(sprite.getFrame((angle/(360/SPRITE_NUM_IMAGES))), ((int) x), ((int) y),obs);
  }

	@Override
	public void update(Observable o, Object arg) {
    move(getDx(), getDy());

    //move(1 * speed_moving * Math.cos(Math.toRadians(angle)) ,
      //      -1 * speed_moving * Math.sin(Math.toRadians(angle)));


	}

	@Override
  public void move(double dx, double dy){
    GameObject collidedWith = collisionTracker.collides(this, dx, dy);

    if(collidedWith == null){
      x += dx;
      y += dy;
    } else {
      if(collidedWith instanceof Destroyable){
        ((Destroyable) collidedWith).reduceHealth(damage);
      }
      ((TanksWorld) world).addExplosion((int) (x + dx), (int) (y + dy));
      ((TanksWorld) world).removeBullet(this);
    }
  }

  public int getWidth() {
    return sprite.getTileSize();
  }

  public int getHeight() {
    return sprite.getTileSize();
  }
}
