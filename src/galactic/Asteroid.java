package galactic;

import objects.Destroyable;
import objects.GameObject;
import objects.Movable;
import objects.Sprite;
import tanks.TanksWorld;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class Asteroid extends Movable {


  final int TILE_SIZE = 48;
  final int SPRITE_NUM_IMAGES = 180;
  int damage = 1;

  public Asteroid(int x, int y, GalacticWorld world) {

    angle = 100;
    this.x = x;
    this.y = y;
    this.world = world;
    world.getCollisionTracker().addMovingObject(this);

    try {
      sprite = new Sprite("galactic/resources/Asteroid_strip180.png", TILE_SIZE);
      setRectangle(new Rectangle(x, y, TILE_SIZE, TILE_SIZE));
    } catch (Exception e) {
      System.out.println("Exception while creating new asteroid object.");
    }

  }

  @Override
  public void draw(ImageObserver obs, Graphics2D g) {
    g.drawImage(sprite.getFrame((angle/(360/SPRITE_NUM_IMAGES))), ((int) x), ((int) y),obs);
  }

  @Override
  public void update(Observable o, Object arg) {
    move(getDx(), getDy());
  }

  @Override
  public void move(double dx, double dy){

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

    if(x + TILE_SIZE < 0){
      x = ((GalacticWorld)world).getEFFECTIVE_WIDTH();
    }
    if(y + TILE_SIZE < 0){
      y = ((GalacticWorld)world).getEFFECTIVE_HEIGHT();
    }

    if(x > ((GalacticWorld)world).getEFFECTIVE_WIDTH() ){
      x = - TILE_SIZE;
    }
    if(y >((GalacticWorld)world).getEFFECTIVE_HEIGHT()){
      y = - TILE_SIZE ;
    }


    setRectangle(new Rectangle((int)x, (int)y, TILE_SIZE, TILE_SIZE));



  }

  public int getWidth() {
    return TILE_SIZE;
  }

  public int getHeight() {
    return TILE_SIZE;
  }

}
