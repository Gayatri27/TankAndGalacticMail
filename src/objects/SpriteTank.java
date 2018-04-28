package objects;

import application.Clock;
import application.CollisionTracker;
import application.KeyEvents;
import application.World;
import objects.weapons.TankWeapon;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


public  class SpriteTank extends GameObject implements Destroyable {

  private int KEY_FORWARD, KEY_BACK, KEY_RIGHT, KEY_LEFT, KEY_FIRE;

  private int angle = 0;
  private final int SPEED_MOVING = 8;
  private final int SPEED_TURNING = 6;
  private static CollisionTracker collisionTracker;
  private int health = 100;
  private World world;

  HashMap<Integer, Boolean> keyStates  = new HashMap<>(5);


  private Sprite sprite;
  TankWeapon weapon;

  public SpriteTank(Sprite sprite, int x, int y, int keyScheme, World world){
    this.sprite = sprite;
    setKeyScheme(keyScheme);
    this.x = x;
    this.y = y;

    setRectangle(new Rectangle(x,y,sprite.getTileSize(), sprite.getTileSize()));


    weapon = new TankWeapon(this, world);

    collisionTracker = world.getCollisionTracker();
    collisionTracker.addMovingObject(this);
    this.world = world;

    keyStates.put(KEY_FORWARD, false);
    keyStates.put(KEY_BACK, false);
    keyStates.put(KEY_RIGHT, false);
    keyStates.put(KEY_LEFT, false);
    keyStates.put(KEY_FIRE, false);
  }

  public int getAngle(){
    return angle;
  }

  public void setKeyScheme(int scheme){
    switch(scheme){
      case 0:
        KEY_FORWARD = KeyEvent.VK_W;
        KEY_BACK = KeyEvent.VK_S;;
        KEY_RIGHT = KeyEvent.VK_D;;
        KEY_LEFT = KeyEvent.VK_A;;
        KEY_FIRE = KeyEvent.VK_SPACE;;
        break;

      case 1:
        KEY_FORWARD = KeyEvent.VK_I;
        KEY_BACK = KeyEvent.VK_K;;
        KEY_RIGHT = KeyEvent.VK_L;;
        KEY_LEFT = KeyEvent.VK_J;;
        KEY_FIRE = KeyEvent.VK_ENTER;;
        break;

      case 2:
        KEY_FORWARD = KeyEvent.VK_UP;
        KEY_BACK = KeyEvent.VK_DOWN;;
        KEY_RIGHT = KeyEvent.VK_RIGHT;;
        KEY_LEFT = KeyEvent.VK_LEFT;;
        KEY_FIRE = KeyEvent.VK_ENTER;;
        break;

    }
  }

  public void setKeyScheme(int forward, int back, int right, int left, int fire) {
    KEY_FORWARD = forward;
    KEY_BACK = back;
    KEY_RIGHT = right;
    KEY_LEFT = left;
    KEY_FIRE = fire;
  }


  public void draw(ImageObserver obs, Graphics2D g) {

    g.drawImage(sprite.getFrame((int)(angle/6)), ((int) x), ((int) y),obs);
  }



  @Override
  public void update(Observable observable, Object arg) {

    if(observable instanceof KeyEvents){

      KeyEvent e = (KeyEvent) arg;

      if (e.getID() == KeyEvent.KEY_PRESSED) {
        keyStates.replace(e.getKeyCode(), true);
      }else{
        keyStates.replace(e.getKeyCode(), false);
      }

    } else if(observable instanceof Clock){

      updateMove();

    }


  }

  public void updateMove() {


    if (keyStates.get(KEY_RIGHT)) {
      if(angle - SPEED_TURNING <= 0){
        angle = 359;
      }else{
        angle -= SPEED_TURNING;
      }
    }

    if (keyStates.get(KEY_LEFT)) {
      if(angle + SPEED_TURNING >= 360){
        angle = 0;
      }else{
        angle += SPEED_TURNING;
      }
    }

    double dx = 0, dy = 0;

    if (keyStates.get(KEY_FORWARD)) {

      dx += 1 * SPEED_MOVING * Math.cos(Math.toRadians(angle));
      dy += -1 * SPEED_MOVING * Math.sin(Math.toRadians(angle));
    }

    if (keyStates.get(KEY_BACK)) {
      dx -= 1 * SPEED_MOVING * Math.cos(Math.toRadians(angle));
      dy -= -1 * SPEED_MOVING * Math.sin(Math.toRadians(angle));
    }

    if(dx !=0 || dy != 0){
      moveTheTank(dx, dy);
    }

    if (keyStates.get(KEY_FIRE)) {
      weapon.shoot();
    }

  }

  public void moveTheTank(double dx, double dy){

    if(collisionTracker.collides(this, dx, dy) == null){
      x += dx;
      y += dy;
      setRectangle(new Rectangle((int)x, (int)y, sprite.getTileSize(), sprite.getTileSize()));
    }

  }

  public Rectangle getTankRectangle() {
    return new Rectangle((int)x, (int)y, sprite.getTileSize(), sprite.getTileSize());
  }


  public Rectangle getAdjustedTankRectangleForBullets() {
    int adjustedWidth = sprite.getTileSize() - 1;
    int adjustedHeight = sprite.getTileSize() - 1;
    return new Rectangle((int)x, (int)y, adjustedWidth, adjustedHeight);
  }

  public Rectangle getNextMoveTankRectangle() { //TODO fix nextY nextX
    return new Rectangle((int)x, (int)y, sprite.getTileSize(), sprite.getTileSize());
  }


  public int getWidth() {
    return sprite.getTileSize();
  }

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
    new Audio().play();
    health -= amount;

    if(health <= 0){
      world.endGame();
    }
  }
}
