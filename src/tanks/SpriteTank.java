package tanks;

import application.*;
import objects.Controllable;
import objects.Destroyable;
import objects.Sprite;
import tanks.TankWeapon;
import tanks.TanksWorld;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Observable;


public  class SpriteTank extends Controllable implements Destroyable {

  private int KEY_FORWARD, KEY_BACK, KEY_RIGHT, KEY_LEFT, KEY_FIRE;
  private int health = 100;
  TankWeapon weapon;

  public SpriteTank(Sprite sprite, int x, int y, int keyScheme, TanksWorld world){
    this.sprite = sprite;
    this.x = x;
    this.y = y;

    speed_moving = 8;
    speed_turning = 6;

    setRectangle(new Rectangle(x,y,sprite.getTileSize(), sprite.getTileSize()));

    weapon = new TankWeapon(this, world);

    collisionTracker = world.getCollisionTracker();
    collisionTracker.addMovingObject(this);
    this.world = world;

    setKeyScheme(keyScheme);
    initializeKeyStates();
  }

  public void draw(ImageObserver obs, Graphics2D g) {
    g.drawImage(sprite.getFrame((int)(angle/6)), ((int) x), ((int) y),obs);
  }

  @Override
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

  @Override
  protected void initializeKeyStates() {
    keyStates.put(KEY_FORWARD, false);
    keyStates.put(KEY_BACK, false);
    keyStates.put(KEY_RIGHT, false);
    keyStates.put(KEY_LEFT, false);
    keyStates.put(KEY_FIRE, false);
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

    double dx = 0, dy = 0;

    if (keyStates.get(KEY_FORWARD)) {
      dx += getDx();
      dy += getDy();
    }

    if (keyStates.get(KEY_BACK)) {
      dx -= getDx();
      dy -= getDy();
    }

    if(dx !=0 || dy != 0){
      move(dx, dy);
    }

    if (keyStates.get(KEY_FIRE)) {
      weapon.shoot();
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
