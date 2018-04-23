package objects;

import objects.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameObject implements Observer {

  public int x, y;

  public Image image;
  private ImageObserver observer;

  public  GameObject(){}

  public GameObject(String resourceLocation) throws IOException {
    this(resourceLocation, null);
  }

  public GameObject(Image image, int x, int y, int forwardKey, int backKey, int leftKey, int rightKey, int fireKey)
      throws IOException {
    this.x = x;
    this.y = y;
    this.image = image;
  }

  public GameObject(String resourceLocation, ImageObserver observer) throws IOException {
    x = 0;
    y = 0;

    image = ImageIO.read(new File(resourceLocation));
    this.observer = observer;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getX() {
    return this.x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getY() {
    return this.y;
  }

  public int getWidth() {
    return image.getWidth(null);
  }

  public int getHeight() {
    return image.getHeight(null);
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public void repaint(Graphics graphics) {
    //graphics.drawImage(image, x, y, observer);
  }

  public Animation rotate(int rotation) {
    return null;
  }

  @Override
  public void update(Observable o, Object arg) {

  }

  public void draw(ImageObserver observer, Graphics2D g) {
    g.drawImage(image, x, y, observer);

  }
}
