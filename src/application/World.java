package application;

import objects.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class World extends JPanel implements Observer {

  private static final long serialVersionUID = -7437242000932772800L;
  private GameEvents gameEvents;

  protected final int MAIN_WIDTH = 2400;
  protected final int MAIN_HEIGHT = 2400;
  protected final int STEP = 10;
  protected final String BACKGROUND_IMAGE = "resources/background_tile.png";
  protected final String TANK_IMAGE = "resources/Tank_grey_basic.png";

  private BufferedImage main_bimg;
  private BufferedImage bimg;
  private BufferedImage bg_buffer;

  protected Dimension dimension;
  protected Tank tank1;
  protected Tank tank2;

  protected ArrayList<Animation> animations;

  private Clock clock;
  ArrayList<Wall> walls;

  public World(GameFrame frame) {

    KeyControl key = new KeyControl();

    frame.addKeyListener(key);

    gameEvents = new GameEvents();

    try {
      Image tank1 = ImageIO.read(new File("resources/Tank_blue_basic_strip60.png"));
      Image tank2 = ImageIO.read(new File("resources/Tank_red_basic_strip60.png"));
      this.tank1 = new Tank(tank1, 300, 300, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
          KeyEvent.VK_C);
      this.tank2 = new Tank(tank2, 100, 300, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L,
          KeyEvent.VK_N);

      Image wallImage = ImageIO.read(new File("resources/wall.png"));
      walls = new ArrayList<Wall>();
      walls.add(new Wall(wallImage,0,0, false));
      walls.add(new Wall(wallImage,100,100,false));
      walls.add(new Wall(wallImage,100,132,false));
      walls.add(new Wall(wallImage,200,200, false));
      walls.add(new Wall(wallImage,232,200, false));


    } catch (IOException exception) {
      System.err.println("Failed to load sprite.");
      exception.printStackTrace();
    }

    gameEvents.addObserver(tank1);
    gameEvents.addObserver(tank2);
    this.animations = new ArrayList<>();
    this.dimension = new Dimension(WIDTH, HEIGHT);

    this.setFocusable(true);

    clock = new Clock();
    clock.addObserver(this);
    clock.start();

  }

  public Graphics2D createGraphics2D(int w, int h) {
    Graphics2D g2 = null;
    if (main_bimg == null || main_bimg.getWidth() != w || main_bimg.getHeight() != h) {
      main_bimg = (BufferedImage) createImage(w, h);
    }
    g2 = main_bimg.createGraphics();
    g2.setBackground(getBackground());
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
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




/*
    int tank1_x = tank1.getX() - windowSize.width / 4 + tank1.getWidth()/2;
    if(tank1_x < 0) tank1_x = 0;
    int tank1_y = tank1.getY() - windowSize.height / 2  + tank1.getHeight()/2;
    if(tank1_y < 0) tank1_y = 0;

    int tank2_x = tank2.getX() - windowSize.width / 4  + tank1.getWidth()/2;
    if(tank2_x < 0) tank2_x = 0;

    int tank2_y = tank2.getY() - windowSize.height / 2 + tank1.getHeight()/2;
    if(tank2_y < 0) tank2_y = 0;
*/

    int tank1_x = tank1.getTankCenterX() - windowSize.width / 4 ;
    if(tank1_x < 0) tank1_x = 0;
    int tank1_y = tank1.getTankCenterY() - windowSize.height;
    if(tank1_y < 0) tank1_y = 0;

    int tank2_x = tank2.getTankCenterX() - windowSize.width / 4 ;
    if(tank2_x < 0) tank2_x = 0;

    int tank2_y = tank2.getTankCenterY() - windowSize.height;
    if(tank2_y < 0) tank2_y = 0;

    BufferedImage player_1_window = main_bimg.getSubimage(tank1_x, tank1_y,windowSize.width / 2, windowSize.height);

    BufferedImage player_2_window = main_bimg.getSubimage(tank2_x, tank2_y,windowSize.width / 2, windowSize.height);

    g2.drawImage(player_1_window, 0, 0, this);
    g2.drawImage(player_2_window, windowSize.width / 2, 0, this);

    g.drawImage(bimg, 0, 0, this);


  }

  public void drawFrame(int w, int h, Graphics2D graphics) {

    if(bg_buffer == null) drawBackground();
    graphics.drawImage(bg_buffer, 0, 0, this);

    tank1.draw(this, graphics);
    tank1.updateMove();
    tank2.draw(this, graphics);
    tank2.updateMove();

  }


  public void drawBackground() {
    try{

      bg_buffer = (BufferedImage) createImage(MAIN_WIDTH, MAIN_HEIGHT);

      Graphics2D bg_g2 = bg_buffer.createGraphics();

      BufferedImage bg = ImageIO.read(new File(BACKGROUND_IMAGE));
      TexturePaint paint = new TexturePaint(bg, new Rectangle(bg.getWidth(),bg.getHeight()));
      bg_g2.setPaint(paint);
      bg_g2.fillRect(0, 0, MAIN_WIDTH, MAIN_HEIGHT);

      for(Wall w : walls){
        w.draw(this, bg_g2);
      }

      /*
      File outputfile = new File("resources/ooo.jpg");
      ImageIO.write(bg_buffer, "jpg", outputfile);

      System.out.println("drawbackground done");
      */

    }catch(Exception e){
      e.getStackTrace();
      System.out.println(e.getMessage());
    }
  }



    @Override
  public Dimension getPreferredSize() {
    return this.dimension;
  }

  @Override
  public void update(Observable o, Object arg) {
     repaint();
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
