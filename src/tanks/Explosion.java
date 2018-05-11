package tanks;

import application.World;
import objects.GameObject;
import objects.Sprite;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class Explosion extends GameObject {
  private int currentFrame = 0;

  private final int SPRITE_NUM_IMAGES = 4;


  public Explosion(Sprite sprite, World world, int x, int y) {
    this.sprite = sprite;
    this.world = world;
    this.x = x;
    this.y = y;

  }

  public void draw(ImageObserver obs, Graphics2D g) {
    g.drawImage(sprite.getFrame(currentFrame), ((int) x), ((int) y), obs);
  }

  @Override
  public void update(Observable observable, Object arg) {
    if (currentFrame < SPRITE_NUM_IMAGES) {
      currentFrame++;
    } else {
      ((TanksWorld) world).removeExplosion(this);
    }
  }
}
