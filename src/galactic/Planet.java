package galactic;

import objects.GameObject;
import objects.Sprite;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Planet extends GameObject {

  final int PLANET; // 0 to 8
  final int TILE_SIZE = 48;

  public Planet(int x, int y, int planet, GalacticWorld world) {

    PLANET = planet;
    this.x = x;
    this.y = y;
    this.world = world;

    try {
      sprite = new Sprite("galactic/resources/Planetoids_strip8.png", TILE_SIZE);
      setRectangle(new Rectangle(x, y, TILE_SIZE, TILE_SIZE));

    } catch (Exception e) {
      System.out.println("Exception while creating new planet object.");
    }

  }


  public void draw(ImageObserver obs, Graphics2D g) {
    g.drawImage(sprite.getFrame(PLANET), ((int) x), ((int) y), obs);
  }


}
