package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Wall extends GameObject {

  private Boolean isDestructible;

  public Wall(int x, int y, Boolean isDestructible) {

    try {
      if (isDestructible) {
        image = ImageIO.read(new File("resources/wall.png"));
      } else {
        image = ImageIO.read(new File("resources/wall_indestructible.png"));
      }
      this.x = x;
      this.y = y;
      this.isDestructible = isDestructible;
    } catch (Exception e) {
      System.out.println("Exception while creating new wall object.");
    }
  }

  public Wall(Image image, int x, int y, Boolean isDestructible) {
    this.x = x;
    this.y = y;
    this.image = image;
    this.isDestructible = isDestructible;
  }

  public boolean isDestructible() {
    return this.isDestructible;
  }
}
