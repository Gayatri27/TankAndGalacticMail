package application;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public abstract class World extends JPanel implements Observer {

  protected Clock clock;

  protected final int MAIN_WIDTH;
  protected final int MAIN_HEIGHT;

  protected int windowWidth;
  protected int windowHeight;

  protected CollisionTracker collisionTracker;

  public World(int main_width, int main_height){
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
    clock.deleteObservers();
    clock.stop();
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
