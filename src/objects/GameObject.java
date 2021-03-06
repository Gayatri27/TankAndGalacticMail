package objects;

import application.World;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public abstract class GameObject implements Observer {

  protected World world;
  protected double x, y;
  protected int angle;
  protected Rectangle rectangle;
  protected Image image;
  protected Sprite sprite;


  public GameObject() {
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getX() {
    return (int) this.x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getY() {
    return (int) this.y;
  }

  public int getAngle() {
    return this.angle;
  }


  public int getWidth() {
    if (sprite != null) {
      return sprite.getTileSize();
    } else {
      return image.getWidth(null);
    }
  }

  public int getHeight() {
    if (sprite != null) {
      return sprite.getTileSize();
    } else {
      return image.getHeight(null);
    }
  }

  @Override
  public void update(Observable o, Object arg) {
  }

  public void draw(ImageObserver observer, Graphics2D g) {
    g.drawImage(image, (int) x, (int) y, observer);
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  public void setRectangle(Rectangle rectangle) {
    this.rectangle = rectangle;
  }

  public void updateRectangle() {
    this.rectangle = new Rectangle((int) x, (int) y, getWidth(), getHeight());
  }
}
