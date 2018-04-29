package objects;

import application.World;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class Explosion extends GameObject {
  private Sprite sprite;
  	private World world;
  	private int currentFrame = 0;

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
  		if (currentFrame < 4) {
  			currentFrame++;
  		} else {
  			world.removeExplosion(this);
  		}
  	}
}
