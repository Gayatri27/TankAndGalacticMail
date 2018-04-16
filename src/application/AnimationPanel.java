package application;

import objects.Animation;
import graphics.GameObject;
import objects.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jrob on 4/9/17.
 */
public abstract class AnimationPanel extends JPanel implements Runnable {
  protected final int WIDTH = 640;
  protected final int HEIGHT = 640;
  protected final int STEP = 10;
  protected final String BACKGROUND_IMAGE = "resources/background_tile.png";
  protected final String TANK_IMAGE = "resources/Tank_grey_basic.png";


  private BufferedImage bimg;

  protected Dimension dimension;

  protected GameObject tank;
  protected GameObject tank2;
  protected GameObject background;

  protected ArrayList< Animation > animations;


  public Graphics2D createGraphics2D(int w, int h) {
    Graphics2D g2 = null;
    if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
      bimg = (BufferedImage) createImage(w, h);
    }
    g2 = bimg.createGraphics();
    g2.setBackground(getBackground());
    g2.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
    g2.clearRect(0, 0, w, h);
    return g2;
  }

  public void paint(Graphics g) {
//    if(players.size()!=0)
//      clock.tick();
    Dimension windowSize = getSize();
    Graphics2D g2 = createGraphics2D(windowSize.width, windowSize.height);
    drawFrame(windowSize.width, windowSize.height, g2);
    g2.dispose();
    g.drawImage(bimg, 0, 0, this);
  }


  public void drawFrame(int w, int h, Graphics2D graphics) {

    for( int x = 0; x < WIDTH; x += background.getWidth() ) {
      for( int y = 0; y < HEIGHT; y+= background.getHeight() ) {
        background.setX( x );
        background.setY( y );
        background.repaint( graphics );
      }
    }

    Animation animation;

    for( int counter = 0; counter < animations.size(); counter++ ) {
      animation = animations.get( counter );

//      if( animation.isStopped() ) {
//        animations.remove( counter );
//      } else {
//        animation.repaint( graphics );
//      }
      animation.repaint( graphics );
    }

    tank.repaint( graphics );
    tank2.repaint( graphics );

  }


  public AnimationPanel() {
    try {
      Sprite sprite = new Sprite("resources/Tank_blue_basic_strip60.png", 60);
      Sprite sprite2 = new Sprite("resources/Tank_red_basic_strip60.png", 60);
      this.tank = new GameObject( sprite, 0, 270, 100, 0 );
      this.tank2 = new GameObject( sprite2, 32, 270, 450, 180 );
      this.background = new GameObject( BACKGROUND_IMAGE );
    } catch( IOException exception ) {
      System.err.println( "Failed to load sprite." );
      exception.printStackTrace();
    }

    this.animations = new ArrayList<>();
    this.dimension = new Dimension( WIDTH, HEIGHT );
  }

  @Override
  public Dimension getPreferredSize() {
    return this.dimension;
  }


  protected void addAnimation( Animation animation ) {
    // animations.add( animation );
  }
}
