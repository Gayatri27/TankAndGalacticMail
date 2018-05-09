package galactic;

import application.Audio;
import application.World;
import objects.GameObject;
import objects.Sprite;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class Explosion extends GameObject {
  private int currentFrame = 0;

  private final String SPRITE_FILE = "galactic/resources/Explosion_strip9.png";
  private final int SPRITE_TILE_SIZE = 64;
  private final int SPRITE_NUM_IMAGES = 9;

  private static Audio audio;

  Explosion(World world, int x, int y) {
    try{
      this.sprite = new Sprite(SPRITE_FILE, SPRITE_TILE_SIZE);
      this.world = world;
      this.x = x;
      this.y = y;
    }catch (Exception e){
      System.out.println("Error occurred while trying to create explosion.");
    }

    if(audio == null){
      audio = new Audio("galactic/resources/Explosion.wav");
    }
    audio.play();

  }

  public void draw(ImageObserver obs, Graphics2D g) {
    g.drawImage(sprite.getFrame(currentFrame), ((int) x), ((int) y), obs);
  }

  @Override
  public void update(Observable observable, Object arg) {
    if (currentFrame < SPRITE_NUM_IMAGES-1) {
      currentFrame++;
    } else {
      ((GalacticWorld)world).removeExplosion(this);
      world.endGame();
    }
  }
}
