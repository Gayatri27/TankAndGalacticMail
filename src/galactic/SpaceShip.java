package galactic;

import application.Clock;
import application.KeyEvents;
import objects.Controllable;
import objects.Destroyable;
import objects.GameObject;
import objects.Sprite;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Observable;


public class SpaceShip extends Controllable implements Destroyable {


  private Sprite spriteFlying;

  private int KEY_RIGHT, KEY_LEFT, KEY_LAUNCH;
  private int health = 1;

  private final int TILE_SIZE = 48;
  private final int MIN_OVERLAP_TO_LAND = 60;
  private final int DEFAULT_SPEED_MOVING = 8;
  private final int DEFAULT_SPEED_TURNING = 6;

  private Planet lastLandedOn;
  private int landedTimestamp = 0;




  public SpaceShip(int x, int y, int keyScheme, GalacticWorld world){

    try{
      sprite = new Sprite("galactic/resources/Landed_strip72.png", TILE_SIZE);
      spriteFlying = new Sprite("galactic/resources/Flying_strip72.png", TILE_SIZE);
    }catch(Exception e){
      System.out.println("Error creating space ship sprite.");
    }

    this.x = x;
    this.y = y;

    speed_moving = DEFAULT_SPEED_MOVING;
    speed_turning = DEFAULT_SPEED_TURNING;

    setRectangle(new Rectangle(x,y,sprite.getTileSize(), sprite.getTileSize()));


    collisionTracker = world.getCollisionTracker();
    //collisionTracker.addMovingObject(this);
    this.world = world;

    setKeyScheme(keyScheme);
    initializeKeyStates();
  }

  public void draw(ImageObserver obs, Graphics2D g) {
    g.drawImage(sprite.getFrame((int)(angle/5)), ((int) x), ((int) y),obs);
  }

  @Override
  public void setKeyScheme(int scheme) {
    switch(scheme){
      case 0:
        KEY_RIGHT = KeyEvent.VK_D;;
        KEY_LEFT = KeyEvent.VK_A;;
        KEY_LAUNCH = KeyEvent.VK_SPACE;;
        break;

      case 1:
        KEY_RIGHT = KeyEvent.VK_RIGHT;;
        KEY_LEFT = KeyEvent.VK_LEFT;;
        KEY_LAUNCH = KeyEvent.VK_SPACE;;
        break;
    }
  }

  @Override
  protected void initializeKeyStates() {
    keyStates.put(KEY_RIGHT, false);
    keyStates.put(KEY_LEFT, false);
    keyStates.put(KEY_LAUNCH, false);
  }


  @Override
  public void update(Observable observable, Object arg) {
    if(observable instanceof KeyEvents){
      updateKeyStates( (KeyEvent) arg );
    } else if(observable instanceof Clock){
      updateMove();
    }
  }



  public void updateMove() {


    if (keyStates.get(KEY_RIGHT)) {
      rotateRight();
    }

    if (keyStates.get(KEY_LEFT)) {
      rotateLeft();
    }

    move(getDx(), getDy());



    if (keyStates.get(KEY_LAUNCH)) {
      speed_moving = DEFAULT_SPEED_MOVING;
    }

  }


  @Override
  public void move(double dx, double dy){



    GameObject collidedWith = collisionTracker.collides(this, dx, dy);

    if(collidedWith != lastLandedOn && lastLandedOn != null){
      ((GalacticWorld) world).removePlanet(lastLandedOn);
      lastLandedOn = null;
      landedTimestamp = 0;
    }


    if(collidedWith == null){
      x += dx;
      y += dy;



    } else {
      if( collidedWith instanceof Planet){


        /*
        Compute the area of the intersection, which is a rectangle too:

        SI = Max(0, Min(XA2, XB2) - Max(XA1, XB1)) * Max(0, Min(YA2, YB2) - Max(YA1, YB1))
        From there you compute the area of the union:

        SU = SA + SB - SI
        And you can consider the ratio

        SI / SU
        (100% in case of a perfect overlap, down to 0%).
         */

        x += dx;
        y += dy;

        int XA1 = collidedWith.getX();
        int XA2 = collidedWith.getX() + collidedWith.getWidth();

        int YA1 = collidedWith.getY();
        int YA2 = collidedWith.getY() + collidedWith.getHeight();

        int SA = collidedWith.getWidth() * collidedWith.getHeight();

        int XB1 = (int)x;
        int XB2 = (int)x+ getWidth();

        int YB1 = (int)y;
        int YB2 = (int)y + getHeight();

        int SB = getWidth() * getHeight();

        double SI = Math.max(0, Math.min(XA2, XB2) - Math.max(XA1, XB1)) * Math.max(0, Math.min(YA2, YB2) - Math.max(YA1, YB1));

        double SU = SA + SB - SI;

        int intersection = (int) (SI / SU * 100);

        if(intersection > MIN_OVERLAP_TO_LAND && lastLandedOn != collidedWith ){
          lastLandedOn = (Planet) collidedWith;
          speed_moving = 0;
          ((GalacticWorld) world).countScorePlanet();
        }

      }else if(collidedWith instanceof Asteroid){
        world.endGame();
      }

      if(speed_moving == 0){
        int currentTimestamp = (int) (System.currentTimeMillis() / 1000L);
        if(landedTimestamp == 0){
          landedTimestamp = currentTimestamp;
        }else if( landedTimestamp != 0 && landedTimestamp != currentTimestamp){
          ((GalacticWorld) world).countScoreIdle(currentTimestamp - landedTimestamp);
          landedTimestamp = currentTimestamp;
        }
      }

    }



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

  }



  @Override
  public int getWidth() {
    return sprite.getTileSize();
  }

  @Override
  public int getHeight() {
    return sprite.getTileSize();
  }

  @Override
  public void setHealth(int amount) {
    health = amount;
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public void reduceHealth(int amount) {
    health -= amount;

    if(health <= 0){
      world.endGame();
    }
  }
}
