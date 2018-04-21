package application;

import objects.Animation;
import objects.GameObject;
import objects.Sprite;
import objects.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class World extends JPanel implements KeyListener, Observer {


  private Clock clock;

  protected final int MAIN_WIDTH = 2400;
  protected final int MAIN_HEIGHT = 2400;
  protected final int STEP = 10;
  protected final String BACKGROUND_IMAGE = "resources/background_tile.png";
  protected final String TANK_IMAGE = "resources/Tank_grey_basic.png";

  private BufferedImage main_bimg;


  private BufferedImage bimg;

  protected Dimension dimension;

  protected GameObject tank;
  protected GameObject tank2;
  protected GameObject background;

  protected ArrayList< Animation > animations;


  public World(){
    clock = new Clock();

    try {
      Sprite sprite = new Sprite("resources/Tank_blue_basic_strip60.png", 60);
      Sprite sprite2 = new Sprite("resources/Tank_red_basic_strip60.png", 60);
      this.tank = new Tank( sprite, 0, 270, 100, 0 );
      this.tank2 = new Tank( sprite2, 32, 270, 450, 180 );
      this.background = new GameObject( BACKGROUND_IMAGE );
    } catch( IOException exception ) {
      System.err.println( "Failed to load sprite." );
      exception.printStackTrace();
    }

    this.animations = new ArrayList<>();
    this.dimension = new Dimension( WIDTH, HEIGHT );
    this.addKeyListener( this );
    this.setFocusable( true );
  }


  public Clock getClock() {
    return clock;
  }

  public Graphics2D createGraphics2D(int w, int h) {
    Graphics2D g2 = null;
    if (main_bimg == null || main_bimg.getWidth() != w || main_bimg.getHeight() != h) {
      main_bimg = (BufferedImage) createImage(w, h);
    }
    g2 = main_bimg.createGraphics();
    g2.setBackground(getBackground());
    g2.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
    g2.clearRect(0, 0, w, h);
    return g2;
  }

  @Override
  public void paint(Graphics g) {

    //create the main image
    Graphics2D main_g2 = createGraphics2D(MAIN_WIDTH, MAIN_HEIGHT);



//    if(players.size()!=0)
//      clock.tick();
    Dimension windowSize = getSize();
    drawFrame(MAIN_WIDTH, MAIN_HEIGHT, main_g2);

    main_g2.dispose();

    bimg = (BufferedImage) createImage(windowSize.width, windowSize.height);

    Graphics2D g2 = bimg.createGraphics();


    int tank1_x = tank.getX() - windowSize.width / 4 + tank.getWidth()/2;
    if(tank1_x < 0) tank1_x = 0;
    int tank1_y = tank.getY() - windowSize.height / 2  + tank.getHeight()/2;
    if(tank1_y < 0) tank1_y = 0;

    int tank2_x = tank2.getX() - windowSize.width / 4  + tank.getWidth()/2;
    if(tank2_x < 0) tank2_x = 0;

    int tank2_y = tank2.getY() - windowSize.height / 2 + tank.getHeight()/2;
    if(tank2_y < 0) tank2_y = 0;


    BufferedImage player_1_window = main_bimg.getSubimage(tank1_x, tank1_y,windowSize.width / 2, windowSize.height);

    BufferedImage player_2_window = main_bimg.getSubimage(tank2_x, tank2_y,windowSize.width / 2, windowSize.height);

    g2.drawImage(player_1_window, 0, 0, this);
    g2.drawImage(player_2_window, windowSize.width / 2, 0, this);


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



  @Override
  public Dimension getPreferredSize() {
    return this.dimension;
  }


  protected void addAnimation( Animation animation ) {
    // animations.add( animation );
  }


  @Override
  public void keyPressed(KeyEvent e) {


    System.out.println("key pressed " + e.getKeyCode());

    switch(e.getKeyCode()) {


      case KeyEvent.VK_LEFT:
        if(tank.getRotation() != 180) {
          addAnimation(tank.rotate(180));
        } else {
          tank.setX(( tank.getX() - STEP ));
          System.out.println(tank.getX());
        }
        break;
      case KeyEvent.VK_RIGHT:
        if(tank.getRotation() != 0) {
          addAnimation(tank.rotate(0));
        } else {
          tank.setX(( tank.getX() + STEP ));
        }
        break;
      case KeyEvent.VK_UP:
        if(tank.getRotation() != 90) {
          addAnimation(tank.rotate(90));
        } else {
          tank.setY(( tank.getY() - STEP ) );
        }
        break;
      case KeyEvent.VK_DOWN:
        if(tank.getRotation() != 270) {
          addAnimation(tank.rotate(270));
        } else {
          tank.setY(( tank.getY() + STEP ) );
        }
        break;
      case 65: //A
        if(tank2.getRotation() != 180) {
          addAnimation(tank2.rotate(180));
        } else {
          tank2.setX(( tank2.getX() - STEP )  );
        }
        break;
      case 68: //D
        if(tank2.getRotation() != 0) {
          addAnimation(tank2.rotate(0));
        } else {
          tank2.setX(( tank2.getX() + STEP )  );
        }
        break;
      case 87: // W
        if(tank2.getRotation() != 90) {
          addAnimation(tank2.rotate(90));
        } else {
          tank2.setY(( tank2.getY() - STEP ));
        }
        break;
      case 83: // S
        if(tank2.getRotation() != 270) {
          addAnimation(tank2.rotate(270));
        } else {
          tank2.setY(( tank2.getY() + STEP ));
        }
        break;


      case KeyEvent.VK_F:
        // addAnimation( new Animation( this.explosion, tank.getX(), tank.getY(), 3, false, "Explosion") );
        break;
    }

    this.repaint();
  }


  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }


  @Override
  public void update(Observable o, Object arg) {

  }
}
