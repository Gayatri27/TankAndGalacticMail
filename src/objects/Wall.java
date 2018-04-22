package objects;

import java.awt.*;

public class Wall extends GameObject {

    private Boolean isDestructible;


  public Wall(Image image, int x, int y, Boolean isDestructible ){
    System.out.println("new wall object");
    this.x = x;
    this.y = y;
    this.image = image;
    this.isDestructible = isDestructible;
  }




}
