package galactic;

import objects.Movable;
import objects.Sprite;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class Asteroid extends Movable {


  final int TILE_SIZE = 50;
  final int SPRITE_NUM_IMAGES = 180;
  int frame;
  final double NEXT_FRAME_CHANCE = 0.5;
  final int TURNING_DIRECTION;
  int damage = 1;

  public Asteroid(int x, int y, int angle, GalacticWorld world) {

    this.angle = angle;
    this.x = x;
    this.y = y;
    this.world = world;

    frame = (int) Math.random() * 180;

    TURNING_DIRECTION = (Math.random() > 0.5) ? 1 : -1;


    try {
      sprite = new Sprite("galactic/resources/Asteroid_strip180.png", TILE_SIZE);
      setRectangle(new Rectangle(x, y, TILE_SIZE, TILE_SIZE));
    } catch (Exception e) {
      System.out.println("Exception while creating new asteroid object.");
    }

  }

  @Override
  public void draw(ImageObserver obs, Graphics2D g) {
    if (Math.random() < NEXT_FRAME_CHANCE) {
      frame += TURNING_DIRECTION;
    }
    if (frame >= SPRITE_NUM_IMAGES) {
      frame = 0;
    } else if (frame <= 0) {
      frame = SPRITE_NUM_IMAGES - 1;
    }

    g.drawImage(sprite.getFrame(frame), ((int) x), ((int) y), obs);
  }

  @Override
  public void update(Observable o, Object arg) {
    move(getDx(), getDy());
  }

  @Override
  public void move(double dx, double dy) {

    x += dx;
    y += dy;

    /*
    GameObject collidedWith = collisionTracker.collides(this, dx, dy);

    if(collidedWith == null){
      x += dx;
      y += dy;
    } else {
      if(collidedWith instanceof Destroyable){
        ((Destroyable) collidedWith).reduceHealth(damage);
      }
      //((GalacticWorld) world).addExplosion((int) (x + dx), (int) (y + dy));
      //((GalacticWorld) world).removeAsteroid(this);
    }

    */

    if (x + TILE_SIZE < 0) {
      x = ((GalacticWorld) world).getEFFECTIVE_WIDTH();
    }
    if (y + TILE_SIZE < 0) {
      y = ((GalacticWorld) world).getEFFECTIVE_HEIGHT();
    }

    if (x > ((GalacticWorld) world).getEFFECTIVE_WIDTH()) {
      x = -TILE_SIZE;
    }
    if (y > ((GalacticWorld) world).getEFFECTIVE_HEIGHT()) {
      y = -TILE_SIZE;
    }


    setRectangle(new Rectangle((int) x, (int) y, TILE_SIZE, TILE_SIZE));


  }

  public int getWidth() {
    return TILE_SIZE;
  }

  public int getHeight() {
    return TILE_SIZE;
  }

}
