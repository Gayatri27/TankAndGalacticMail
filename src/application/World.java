package application;

import javafx.animation.Animation;
import objects.*;
import objects.bullets.AbstractBullet;

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

  protected final int MINI_MAP_WIDTH = 150;
  protected final int MINI_MAP_HEIGHT = 150;

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

  protected ArrayList<AbstractBullet> bullets;

  private Clock clock;
  ArrayList<Wall> walls;
  ArrayList<Obstacle> obstacles;

  public World(GameFrame frame) {

    KeyControl key = new KeyControl();

    frame.addKeyListener(key);

    gameEvents = new GameEvents();

    try {
      Image tank1 = ImageIO.read(new File("resources/Tank_blue_basic_strip60.png"));
      Image tank2 = ImageIO.read(new File("resources/Tank_red_basic_strip60.png"));
      this.tank1 = new Tank(tank1, 1000, 150, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
          KeyEvent.VK_C, this);


      this.tank2 = new Tank(tank2, 1350, 200, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_N, this);


      // this.tank2 = new Tank(tank2, 1350, 2250, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_N);

      walls = new ArrayList<>();
      obstacles = new ArrayList<>();

      Image wallImage = ImageIO.read(new File("resources/wall.png"));

      int wall_height = wallImage.getHeight(this);
      int wall_length = wallImage.getWidth(this);

      // left edge
      for (int y = 0; y <= MAIN_HEIGHT; y = y + wall_height) {
        walls.add(new Wall(0, y, false));
      }

      // right edge
      for (int y = 0; y <= MAIN_HEIGHT; y = y + wall_height) {
        walls.add(new Wall(MAIN_WIDTH - wall_length, y, false));
      }

      // top edge
      for (int x = 0; x <= MAIN_WIDTH; x = x + wall_length) {
        walls.add(new Wall(x, 0, false));
      }

      // bottom edge
      for (int x = 0; x <= MAIN_WIDTH; x = x + wall_length) {
        walls.add(new Wall(x, MAIN_HEIGHT - wall_height, false));
      }

      // center 36 is center
      int start_center_x = ((MAIN_WIDTH / 2 - wall_length) / wall_length) * wall_length;
      int start_center_y = ((MAIN_HEIGHT / 2 - wall_height) / wall_height) * wall_height;


      for (int y = 16 * wall_height; y <= 59 * wall_height; y = y + wall_height) {
        walls.add(new Wall(start_center_x, y, true));
      }

      /*
       * for(int x = 16*wall_length; x <= 59 * wall_length;
       * x=x+wall_length){ walls.add(new Wall( x, start_center_y, true));
       * }
       */

      // center of centers
      for (int x = 20 * wall_length; x <= 52 * wall_length; x = x + wall_length) {
        walls.add(new Wall(x, start_center_y - 7 * wall_length, true));
      }

      for (int x = 20 * wall_length; x <= 52 * wall_length; x = x + wall_length) {
        walls.add(new Wall(x, start_center_y + 7 * wall_length, true));
      }

      // extra corners

      for (int x = 46 * wall_length; x <= 60 * wall_length; x = x + wall_length) {
        walls.add(new Wall(x, 15 * wall_length, true));
        walls.add(new Wall(x, MAIN_HEIGHT - 16 * wall_length, true));
      }

      for (int x = 12 * wall_length; x <= 26 * wall_length; x = x + wall_length) {
        walls.add(new Wall(x, 15 * wall_length, true));
        walls.add(new Wall(x, MAIN_HEIGHT - 16 * wall_length, true));
      }

      // extra corner vertical walls
      for (int y = 16 * wall_height; y <= start_center_y - 12 * wall_length; y = y + wall_height) {
        walls.add(new Wall(19 * wall_height, y, true));
        walls.add(new Wall(53 * wall_height, y, true));
      }

      for (int y = MAIN_HEIGHT - 25 * wall_length; y <= MAIN_HEIGHT - 17 * wall_length; y = y + wall_height) {
        walls.add(new Wall(19 * wall_height, y, true));
        walls.add(new Wall(53 * wall_height, y, true));
      }

      // first bunker
      for (int x = start_center_x - 8 * wall_length; x <= start_center_x; x = x + wall_length) {
        walls.add(new Wall(x, 8 * wall_height, false));
      }

      for (int y = 0; y <= 8 * wall_height; y = y + wall_height) {
        walls.add(new Wall(start_center_x, y, false));
      }

      // second bunker
      for (int y = start_center_y; y >= start_center_y - 8 * wall_length; y = y - wall_height) {
        walls.add(new Wall(MAIN_WIDTH - 8 * wall_length, y, false));
      }

      for (int x = MAIN_WIDTH - 8 * wall_length; x <= MAIN_WIDTH; x = x + wall_length) {
        walls.add(new Wall(x, start_center_y, false));
      }

      // third bunker
      for (int x = start_center_x; x <= start_center_x + 8 * wall_length; x = x + wall_length) {
        walls.add(new Wall(x, 67 * wall_height, false));
      }
      for (int y = 67 * wall_height; y <= MAIN_HEIGHT; y = y + wall_height) {
        walls.add(new Wall(start_center_x, y, false));
      }

      // fourth bunker
      for (int y = start_center_y; y <= start_center_y + 8 * wall_length; y = y + wall_height) {
        walls.add(new Wall(8 * wall_length, y, false));
      }

      for (int x = 0; x <= 8 * wall_length; x = x + wall_length) {
        walls.add(new Wall(x, start_center_y, false));
      }

    } catch (IOException exception) {
      System.err.println("Failed to load sprite.");
      exception.printStackTrace();
    }

    gameEvents.addObserver(tank1);
    gameEvents.addObserver(tank2);

    this.dimension = new Dimension(WIDTH, HEIGHT);


    this.animations = new ArrayList<>();

    bullets = new ArrayList<>();


    this.setFocusable(true);

    clock = new Clock();
    clock.addObserver(this);
    clock.start();

  }

  public void addBullet(AbstractBullet bullet){
    bullets.add(bullet);
    clock.addObserver(bullet);

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

    // create the main image
    Graphics2D main_g2 = createGraphics2D(MAIN_WIDTH, MAIN_HEIGHT);

    // if(players.size()!=0)
    // clock.tick();

    Dimension windowSize = getSize();
    drawFrame(MAIN_WIDTH, MAIN_HEIGHT, main_g2);

    main_g2.dispose();

    bimg = (BufferedImage) createImage(windowSize.width, windowSize.height);

    Graphics2D g2 = bimg.createGraphics();

    // 64 is tank width and height
    int tank1_x = tank1.getX() - windowSize.width / 4 + 64 / 2;
    if (tank1_x < 0)
      tank1_x = 0;
    if (tank1_x + windowSize.width / 2 > MAIN_WIDTH)
      tank1_x = MAIN_WIDTH - windowSize.width / 2;

    int tank1_y = tank1.getY() - windowSize.height / 2 + 64 / 2;
    if (tank1_y < 0)
      tank1_y = 0;
    if (tank1_y + windowSize.height > MAIN_HEIGHT)
      tank1_y = MAIN_HEIGHT - windowSize.height;

    int tank2_x = tank2.getX() - windowSize.width / 4 + 64 / 2;
    if (tank2_x < 0)
      tank2_x = 0;
    if (tank2_x + windowSize.width / 2 > MAIN_WIDTH)
      tank2_x = MAIN_WIDTH - windowSize.width / 2;

    int tank2_y = tank2.getY() - windowSize.height / 2 + 64 / 2;
    if (tank2_y < 0)
      tank2_y = 0;
    if (tank2_y + windowSize.height > MAIN_HEIGHT)
      tank2_y = MAIN_HEIGHT - windowSize.height;

    /*
     *
     * int tank1_x = tank1.getTankCenterX() - windowSize.width / 4 ;
     * if(tank1_x < 0) tank1_x = 0; int tank1_y = tank1.getTankCenterY() -
     * windowSize.height; if(tank1_y < 0) tank1_y = 0;
     *
     * int tank2_x = tank2.getTankCenterX() - windowSize.width / 4 ;
     * if(tank2_x < 0) tank2_x = 0;
     *
     * int tank2_y = tank2.getTankCenterY() - windowSize.height; if(tank2_y
     * < 0) tank2_y = 0;
     */

    BufferedImage player_1_window = main_bimg.getSubimage(tank1_x, tank1_y, windowSize.width / 2,
        windowSize.height);

    BufferedImage player_2_window = main_bimg.getSubimage(tank2_x, tank2_y, windowSize.width / 2,
        windowSize.height);

    BufferedImage miniMap = (BufferedImage) createImage(MINI_MAP_WIDTH, MINI_MAP_HEIGHT);
    Graphics2D miniMapG = miniMap.createGraphics();

    miniMapG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    miniMapG.drawImage(main_bimg, 0, 0, MINI_MAP_WIDTH, MINI_MAP_HEIGHT, 0, 0, MAIN_WIDTH, MAIN_HEIGHT, null);
    miniMapG.dispose();

    g2.drawImage(player_1_window, 0, 0, this);
    g2.drawImage(player_2_window, windowSize.width / 2, 0, this);

    g.drawImage(bimg, 0, 0, this);

    int miniMapX = windowSize.width / 2 - MINI_MAP_WIDTH / 2;
    int miniMapY = windowSize.height - MINI_MAP_HEIGHT;

    g.drawImage(miniMap, miniMapX, miniMapY, this);

    //System.out.println(MAIN_WIDTH/2 - MINI_MAP_WIDTH/2);
    //System.out.println(MAIN_HEIGHT-MINI_MAP_HEIGHT);
    g.drawImage(miniMap, 1125,2250, this);


  }

  public void drawFrame(int w, int h, Graphics2D graphics) {
    obstacles.clear();
    if (bg_buffer == null)
      drawBackground();
    graphics.drawImage(bg_buffer, 0, 0, this);

    tank1.draw(this, graphics);
    tank2.draw(this, graphics);

    boolean noTankCollision = GameUtil.noCollision(tank1, tank2);
    boolean noTankCollisionNextMove = GameUtil.noCollisionNextMove(tank1, tank2);
    boolean noTank1WallCollision = true;
    boolean noTank1WallCollisionNextMove = true;
    boolean noTank2WallCollision = true;
    boolean noTank2WallCollisionNextMove = true;

    for (Wall wall : walls) {
      int wallWidth = wall.image.getWidth(null);
      int wallHeight = wall.image.getHeight(null);
      Rectangle rectangle = new Rectangle(wall.getX(), wall.getY(), wallWidth, wallHeight);
      Obstacle currentObstacle = new Obstacle(rectangle, wall.isDestructible());
      obstacles.add(currentObstacle);
      wall.draw(this, graphics);

      if (!GameUtil.noCollision(tank1, currentObstacle.getRectangle())) {
        noTank1WallCollision = false;
      }
      if (!GameUtil.noCollision(tank2, currentObstacle.getRectangle())) {
        noTank2WallCollision = false;
      }
      if (!GameUtil.noCollisionNextMove(tank1, currentObstacle.getRectangle())) {
        noTank1WallCollisionNextMove = false;
      }
      if (!GameUtil.noCollisionNextMove(tank2, currentObstacle.getRectangle())) {
        noTank2WallCollisionNextMove = false;
      }
    }

    if (noTankCollision && noTank1WallCollision) {
      tank1.updateMove();
    } else if (noTankCollisionNextMove && noTank1WallCollisionNextMove) {
      tank1.updateMove();
    }

    if (noTankCollision && noTank2WallCollision) {
      tank2.updateMove();
    } else if (noTankCollisionNextMove && noTank2WallCollisionNextMove) {
      tank2.updateMove();
    }

    for(AbstractBullet bullet : bullets){
      bullet.draw(this, graphics);
    }

  }


    /*
     * if (GameUtil.noCollision(tank1, tank2)) { tank1.updateMove();
     * tank2.updateMove(); } else if (GameUtil.noCollisionNextMove(tank1,
     * tank2)) { tank1.updateMove(); tank2.updateMove(); }
     */


  public void drawBackground() {
    try {
      bg_buffer = (BufferedImage) createImage(MAIN_WIDTH, MAIN_HEIGHT);

      Graphics2D bg_g2 = bg_buffer.createGraphics();

      BufferedImage bg = ImageIO.read(new File(BACKGROUND_IMAGE));
      TexturePaint paint = new TexturePaint(bg, new Rectangle(bg.getWidth(), bg.getHeight()));
      bg_g2.setPaint(paint);
      bg_g2.fillRect(0, 0, MAIN_WIDTH, MAIN_HEIGHT);

      // File outputfile = new File("resources/ooo.jpg");
      // ImageIO.write(bg_buffer, "jpg", outputfile);


      /*
      File outputfile = new File("resources/ooo.jpg");
      ImageIO.write(bg_buffer, "jpg", outputfile);
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
