package application;

import objects.*;
import objects.bullets.AbstractBullet;
import objects.bullets.TankBullet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public class World extends JPanel implements Observer {

  protected Clock clock;

  protected final int MAIN_WIDTH;
  protected final int MAIN_HEIGHT;

  protected int windowWidth;
  protected int windowHeight;


  protected CollisionTracker collisionTracker;


  World(int main_width, int main_height){
    MAIN_WIDTH = main_width;
    MAIN_HEIGHT = main_height;

    collisionTracker = new CollisionTracker();

    clock = new Clock();
    clock.start();

  }

  public CollisionTracker getCollisionTracker() {
    return collisionTracker;
  }


  public void endGame() {
    clock.stop();
    clock.deleteObservers();
  }

  @Override
  public void update(Observable o, Object arg) {
    repaint();
  }

  protected void updateWindowSize() {
    Dimension windowSize = getSize();
    windowWidth = (int) windowSize.getWidth();
    windowHeight = (int) windowSize.getHeight();
  }

  protected void setRendingHints(Graphics2D g2) {
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
  }


}
