package graphics;

import objects.Sprite;
import objects.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class GameObject {

  private int x;
  private int y;

  private BufferedImage image;
  private ImageObserver observer;

  private int rotation = 0;
  private int currentFrame = 0;
  private Sprite sprite;

  public GameObject( String resourceLocation ) throws IOException {
    this( resourceLocation, null );
  }

  public GameObject( Sprite sprite, int startingFrame, int startingX, int startingY, int rotation ) throws IOException {
    x = startingX;
    y = startingY;

    this.rotation = rotation;
    this.sprite = sprite;
    this.currentFrame = startingFrame;
    this.image = sprite.getFrame(currentFrame);
    this.observer = null;
  }

  public GameObject( String resourceLocation, ImageObserver observer ) throws IOException {
    x = 0;
    y = 0;

    image = ImageIO.read( new File( resourceLocation ));
    this.observer = observer;
  }

  public void setX( int x ) {
    this.x = x;
  }

  public int getX() {
    return this.x;
  }

  public void setY( int y ) {
    this.y = y;
  }

  public int getY() {
    return this.y;
  }

  public int getWidth() {
    return this.image.getWidth();
  }

  public int getHeight() {
    return this.image.getHeight();
  }

  public int getRotation() {
    return this.rotation;
  }

  public void setRotation(int rotation) {
    this.rotation = rotation;
  }

  public void repaint( Graphics graphics ) {
    graphics.drawImage( image, x, y, observer );
  }

  public Animation rotate(int rotation) {
    this.rotation = rotation;
    switch(rotation) {
      case 0:
        currentFrame = 0;
        image = sprite.getFrame(0);
        break;
      case 90:
        currentFrame = 16;
        image = sprite.getFrame(16);
        break;
      case 180:
        currentFrame = 32;
        image = sprite.getFrame(32);
        break;
      case 270:
        currentFrame = 48;
        image = sprite.getFrame(48);
        break;
    }
    // Animation animation = new Animation(sprite, getX(), getY(), currentFrame, rotation);
    // return animation;
    return null;
  }
}
