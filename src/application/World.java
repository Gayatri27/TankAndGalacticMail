package application;

import objects.Animation;
import objects.GameEvents;
import objects.GameObject;
import objects.Tank;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class World extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = -7437242000932772800L;
  private GameEvents gameEvents;

  protected final int WIDTH = 800;
  protected final int HEIGHT = 800;
  protected final int STEP = 10;
  protected final String BACKGROUND_IMAGE = "resources/background_tile.png";

  private BufferedImage bimg;

  protected Dimension dimension;

  protected Tank tank1;
  protected Tank tank2;
  protected GameObject background;

  protected ArrayList<Animation> animations;

  public World() {
    gameEvents = new GameEvents();

    try {
      this.background = new GameObject(BACKGROUND_IMAGE);
      Image tank1 = ImageIO.read(new File("resources/Tank_blue_basic_strip60.png"));
      Image tank2 = ImageIO.read(new File("resources/Tank_red_basic_strip60.png"));

      this.tank1 = new Tank(tank1, 100, 300, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
          KeyEvent.VK_C);
      this.tank2 = new Tank(tank2, 100, 600, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L,
          KeyEvent.VK_N);
    } catch (IOException exception) {
      System.err.println("Failed to load sprite.");
      exception.printStackTrace();
    }

    gameEvents.addObserver(tank1);
    gameEvents.addObserver(tank2);
    this.animations = new ArrayList<>();
    this.dimension = new Dimension(WIDTH, HEIGHT);

    KeyControl key = new KeyControl();
    addKeyListener(key);

    this.setFocusable(true);
  }

  public Graphics2D createGraphics2D(int w, int h) {
    Graphics2D g2 = null;
    if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
      bimg = (BufferedImage) createImage(w, h);
    }
    g2 = bimg.createGraphics();
    g2.setBackground(getBackground());
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.clearRect(0, 0, w, h);
    return g2;
  }

  @Override
  public void paint(Graphics g) {
    // if(players.size()!=0)
    // clock.tick();
    Dimension windowSize = getSize();
    Graphics2D g2 = createGraphics2D(windowSize.width, windowSize.height);
    drawFrame(windowSize.width, windowSize.height, g2);
    g2.dispose();
    g.drawImage(bimg, 0, 0, this);
  }

  public void drawFrame(int w, int h, Graphics2D graphics) {
    for (int x = 0; x < WIDTH; x += background.getWidth()) {
      for (int y = 0; y < HEIGHT; y += background.getHeight()) {
        background.setX(x);
        background.setY(y);
        background.repaint(graphics);
      }
    }

    tank1.draw(this, graphics);
    tank1.updateMove();
    tank2.draw(this, graphics);
    tank2.updateMove();
  }

  @Override
  public Dimension getPreferredSize() {
    return this.dimension;
  }

  public class KeyControl extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      gameEvents.setValue(e);
    }

    public void keyReleased(KeyEvent e) {
      gameEvents.setValue(e);
    }
  }
}
