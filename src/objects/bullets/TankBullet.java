package objects.bullets;

import graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class TankBullet extends AbstractBullet  {

  Sprite sprite;
  int x,y;

  public TankBullet(int x, int y) {
    this.x = x;
    this.y = y;

    try {
      sprite = new Sprite("resources/Shell_light_strip60.png", 24);



    }catch (Exception e){
      System.out.println(e.getMessage());
    }

  }


  public void draw(ImageObserver obs, Graphics2D g) {

    try {
      sprite = new Sprite("resources/Shell_light_strip60.png", 60);

    }catch (Exception e){

    }
    g.drawImage(sprite.getFrame(10),x, y,obs);
  }



}
