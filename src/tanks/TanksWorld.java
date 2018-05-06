package tanks;

import application.Audio;
import application.GameFrame;
import application.World;
import objects.*;
import objects.AbstractBullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TanksWorld extends World {


  protected final int MINI_MAP_WIDTH = 200;
  protected final int MINI_MAP_HEIGHT = 200;

  protected final String BACKGROUND_IMAGE = "tanks/resources/background_tile.png";
  final Color[] HEALTH_COLORS = new Color[]{
          new Color(182, 66, 1),
          new Color(219, 130, 0),
          new Color(243, 184, 0),
          new Color(158, 142, 1),
          new Color(80, 129, 4)
  };
  private final int DEFAULT_FONT_SIZE = 14;
  private final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_FONT_SIZE);
  private final int BORDER_THICKNESS = 4;
  private final int BOTTOM_BAR_THICKNESS = 25;
  private final int BOTTOM_BAR_MARGIN = 5;
  private final int HEALTH_BAR_WIDTH = 150;
  private final int HEALTH_BAR_HEIGHT = BOTTOM_BAR_THICKNESS - 10;

  protected SpriteTank tank1;
  protected SpriteTank tank2;
  protected ArrayList<TankBullet> bullets;
  ArrayList<Wall> walls;
  CopyOnWriteArrayList<Explosion> explosions;
  private BufferedImage main_bimg;
  private BufferedImage bimg;
  private BufferedImage bg_buffer;
  private BufferedImage frame_buffer;
  private GameFrame frame;
  private Audio gunSound;
  private final String EXPLOSION_SPRITE_FILE = "tanks/resources/Explosion_small_strip6.png";
  private Sprite explosionSprite;

  public TanksWorld(GameFrame frame) {


    super(2400,2400);

    windowWidth = 800;
    windowHeight = 600;

    try {
      Sprite tankSprite1 = new Sprite("tanks/resources/Tank_blue_basic_strip60.png", 64);
      Sprite tankSprite2 = new Sprite("tanks/resources/Tank_red_basic_strip60.png", 64);

      tank1 = new SpriteTank(tankSprite1, 1000, 150, 0, this);
      tank2 = new SpriteTank(tankSprite2, 1350, 200, 1, this);
      explosionSprite = new Sprite(EXPLOSION_SPRITE_FILE, 32);

      this.frame = frame;
      frame.keyEvents.addObserver(tank1);
      frame.keyEvents.addObserver(tank2);

      makeWalls();

      gunSound = new Audio("tanks/resources/turret.wav");

    } catch (IOException exception) {
      System.err.println("Failed to load sprite.");
      exception.printStackTrace();
    }

    bullets = new ArrayList<>();
    explosions = new CopyOnWriteArrayList<>();

    this.setFocusable(true);

    clock.addObserver(this);
    clock.addObserver(tank1);
    clock.addObserver(tank2);

  }

  @Override
  public void endGame() {
    super.endGame();

    String resultText;
    if (tank1.getHealth() <= 0) {
      resultText = "Player 2 won!";
    } else {
      resultText = "Player 1 won!";
    }
    frame.startEndGameMenu(resultText);
  }

  @Override
  public void paint(Graphics g) {

    updateWindowSize();

    if (main_bimg == null || main_bimg.getWidth() != MAIN_WIDTH || main_bimg.getHeight() != MAIN_HEIGHT) {
      main_bimg = (BufferedImage) createImage(MAIN_WIDTH, MAIN_HEIGHT);
    }
    Graphics2D main_g2 = main_bimg.createGraphics();
    main_g2.setBackground(getBackground());
    main_g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    main_g2.clearRect(0, 0, MAIN_WIDTH, MAIN_HEIGHT);

    drawFrame(main_g2);

    main_g2.dispose();

    bimg = (BufferedImage) createImage(windowWidth, windowHeight);

    Graphics2D g2 = bimg.createGraphics();

    BufferedImage player_1_window = getPlayerWindow(tank1);
    BufferedImage player_2_window = getPlayerWindow(tank2);

    BufferedImage miniMap = (BufferedImage) createImage(MINI_MAP_WIDTH, MINI_MAP_HEIGHT);
    Graphics2D miniMapG = miniMap.createGraphics();

    miniMapG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    miniMapG.drawImage(main_bimg, 0, 0, MINI_MAP_WIDTH, MINI_MAP_HEIGHT, 0, 0, MAIN_WIDTH, MAIN_HEIGHT, null);
    miniMapG.dispose();

    g2.drawImage(player_1_window, BORDER_THICKNESS, BORDER_THICKNESS, this);
    g2.drawImage(player_2_window, windowWidth / 2 + BORDER_THICKNESS / 2, BORDER_THICKNESS, this);

    g.drawImage(bimg, 0, 0, this);

    int miniMapX = windowWidth / 2 - MINI_MAP_WIDTH / 2;
    int miniMapY = windowHeight - MINI_MAP_HEIGHT;

    g.drawImage(miniMap, miniMapX, miniMapY, this);

    g.drawImage(frame_buffer, 0, 0, this);

    //Health Bars
    int healthBarY = windowHeight - HEALTH_BAR_HEIGHT - (BOTTOM_BAR_THICKNESS - HEALTH_BAR_HEIGHT) / 2;

    // Health Tank 1
    g.setColor(HEALTH_COLORS[(int) ((double) tank1.getHealth() / 100 * (HEALTH_COLORS.length - 1))]);
    g.fillRect((HEALTH_BAR_WIDTH - (int) ((double) tank1.getHealth() / 100 * (double) HEALTH_BAR_WIDTH)) + miniMapX - BOTTOM_BAR_MARGIN - HEALTH_BAR_WIDTH, healthBarY, (int) ((double) tank1.getHealth() / 100 * (double) HEALTH_BAR_WIDTH), HEALTH_BAR_HEIGHT);

    // Health Tank 2
    g.setColor(HEALTH_COLORS[(int) ((double) tank2.getHealth() / 100 * (HEALTH_COLORS.length - 1))]);
    g.fillRect(miniMapX + BOTTOM_BAR_MARGIN + MINI_MAP_WIDTH, healthBarY, (int) ((double) tank2.getHealth() / 100 * (double) HEALTH_BAR_WIDTH), HEALTH_BAR_HEIGHT);
  }



  private BufferedImage getPlayerWindow(SpriteTank tank){
    int playerWindowHeight = windowHeight - BOTTOM_BAR_THICKNESS - BORDER_THICKNESS;
    int playerWindowWidth = windowWidth / 2 - BORDER_THICKNESS;

    int tank_x = tank.getX() - playerWindowWidth / 2 + tank.getWidth() / 2;
    if (tank_x < 0)
      tank_x = 0;
    if (tank_x + playerWindowWidth > MAIN_WIDTH)
      tank_x = MAIN_WIDTH - playerWindowWidth;

    int tank_y = tank.getY() - playerWindowHeight / 2 + tank.getHeight() / 2;
    if (tank_y < 0)
      tank_y = 0;
    if (tank_y + playerWindowHeight > MAIN_HEIGHT)
      tank_y = MAIN_HEIGHT - playerWindowHeight;

    return main_bimg.getSubimage(tank_x, tank_y, playerWindowWidth, playerWindowHeight);
  }



  public void drawFrame(Graphics2D graphics) {
    if (bg_buffer == null) {
      drawBackground();
    }

    if (frame_buffer == null) {
      drawFrameBuffer();
    }

    graphics.drawImage(bg_buffer, 0, 0, this);

    for (Wall wall : walls) {
      wall.draw(this, graphics);
    }

    for (AbstractBullet bullet : bullets) {
      bullet.draw(this, graphics);
    }

    for (Explosion explosion : explosions) {
      explosion.draw(this, graphics);
    }

    tank1.draw(this, graphics);
    tank2.draw(this, graphics);
  }

  public void drawFrameBuffer() {

    frame_buffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = frame_buffer.createGraphics();

    setRendingHints(g2);

    g2.setColor(Color.black);

    //Left border
    g2.fillRect(0, 0, BORDER_THICKNESS, windowHeight);

    //Top border
    g2.fillRect(0, 0, windowWidth, BORDER_THICKNESS);

    //Right border
    g2.fillRect(windowWidth - BORDER_THICKNESS, 0, BORDER_THICKNESS, windowHeight);


    //Middle Separator
    g2.fillRect(windowWidth / 2 - BORDER_THICKNESS / 2, 0, BORDER_THICKNESS, windowHeight - MINI_MAP_HEIGHT);

    int miniMapFrameX = windowWidth / 2 - MINI_MAP_WIDTH / 2;
    int miniMapFrameY = windowHeight - MINI_MAP_HEIGHT;

    // MiniMap Frame
    g2.fillRect(miniMapFrameX, miniMapFrameY, MINI_MAP_WIDTH, BORDER_THICKNESS);
    g2.fillRect(miniMapFrameX, miniMapFrameY, BORDER_THICKNESS, MINI_MAP_HEIGHT);
    g2.fillRect(miniMapFrameX + MINI_MAP_WIDTH - BORDER_THICKNESS, miniMapFrameY, BORDER_THICKNESS, MINI_MAP_HEIGHT);
    g2.fillRect(miniMapFrameX, miniMapFrameY + MINI_MAP_HEIGHT - BORDER_THICKNESS, MINI_MAP_WIDTH, BORDER_THICKNESS);

    //Bottom border
    g2.fillRect(0, windowHeight - BOTTOM_BAR_THICKNESS, miniMapFrameX, BOTTOM_BAR_THICKNESS);
    g2.fillRect(miniMapFrameX + MINI_MAP_WIDTH, windowHeight - BOTTOM_BAR_THICKNESS, miniMapFrameX, BOTTOM_BAR_THICKNESS);

    // Player Names
    g2.setColor(Color.white);

    g2.setFont(DEFAULT_FONT);
    FontMetrics fontMetrics = g2.getFontMetrics();
    int playerTextY = windowHeight - (BOTTOM_BAR_THICKNESS / 3);
    g2.drawString("Player 1", BORDER_THICKNESS + BOTTOM_BAR_MARGIN, playerTextY);
    g2.drawString("Player 2", windowWidth - BORDER_THICKNESS - BOTTOM_BAR_MARGIN - fontMetrics.stringWidth("Player 2"), playerTextY);
  }

  public void drawBackground() {
    try {
      bg_buffer = (BufferedImage) createImage(MAIN_WIDTH, MAIN_HEIGHT);

      Graphics2D bg_g2 = bg_buffer.createGraphics();
      setRendingHints(bg_g2);
      BufferedImage bg = ImageIO.read(new File(BACKGROUND_IMAGE));
      TexturePaint paint = new TexturePaint(bg, new Rectangle(bg.getWidth(), bg.getHeight()));
      bg_g2.setPaint(paint);
      bg_g2.fillRect(0, 0, MAIN_WIDTH, MAIN_HEIGHT);

    } catch (Exception e) {

      e.getStackTrace();
      System.out.println(e.getMessage());
    }
  }

  public void addBullet(TankBullet bullet) {
    gunSound.play();
    bullets.add(bullet);
    clock.addObserver(bullet);
  }

  public void removeBullet(TankBullet bullet) {
    gunSound.play();
    bullets.remove(bullet);
    clock.deleteObserver(bullet);
  }

  public void addExplosion(int x, int y) {
    Explosion explosion = new Explosion(explosionSprite, this, x, y);
    explosions.add(explosion);
    clock.addObserver(explosion);
  }

  public void removeExplosion(Explosion explosion) {
    explosions.remove(explosion);
    clock.deleteObserver(explosion);
  }

  public void removeWall(Wall wall) {
    walls.remove(wall);
  }


  private void makeWalls() throws IOException {

    walls = new ArrayList<>();

    Image wallImage = ImageIO.read(new File("tanks/resources/wall.png"));

    int wall_height = wallImage.getHeight(this);
    int wall_length = wallImage.getWidth(this);

    // center 36 is center
    int start_center_x = ((MAIN_WIDTH / 2 - wall_length) / wall_length) * wall_length;
    int start_center_y = ((MAIN_HEIGHT / 2 - wall_height) / wall_height) * wall_height;

    // left edge
    for (int y = 0; y <= MAIN_HEIGHT; y = y + wall_height) {
      walls.add(new Wall(0, y, this));
    }

    // right edge
    for (int y = 0; y <= MAIN_HEIGHT; y = y + wall_height) {
      walls.add(new Wall(MAIN_WIDTH - wall_length, y, this));
    }

    // top edge
    for (int x = 0; x <= MAIN_WIDTH; x = x + wall_length) {
      walls.add(new Wall(x, 0, this));
    }

    // bottom edge
    for (int x = 0; x <= MAIN_WIDTH; x = x + wall_length) {
      walls.add(new Wall(x, MAIN_HEIGHT - wall_height, this));
    }

    // center
    for (int y = 16 * wall_height; y <= 59 * wall_height; y = y + wall_height) {
      walls.add(new DestructibleWall(start_center_x, y, this));
    }

    // center of centers
    for (int x = 20 * wall_length; x <= 52 * wall_length; x = x + wall_length) {
      walls.add(new DestructibleWall(x, start_center_y - 7 * wall_length, this));
    }
    for (int x = 20 * wall_length; x <= 52 * wall_length; x = x + wall_length) {
      walls.add(new DestructibleWall(x, start_center_y + 7 * wall_length, this));
    }


    // extra corners
    for (int x = 46 * wall_length; x <= 60 * wall_length; x = x + wall_length) {
      walls.add(new DestructibleWall(x, 15 * wall_length, this));
      walls.add(new DestructibleWall(x, MAIN_HEIGHT - 16 * wall_length, this));
    }
    for (int x = 12 * wall_length; x <= 26 * wall_length; x = x + wall_length) {
      walls.add(new DestructibleWall(x, 15 * wall_length, this));
      walls.add(new DestructibleWall(x, MAIN_HEIGHT - 16 * wall_length, this));
    }


    // extra corner vertical walls
    for (int y = 16 * wall_height; y <= start_center_y - 12 * wall_length; y = y + wall_height) {
      walls.add(new DestructibleWall(19 * wall_height, y, this));
      walls.add(new DestructibleWall(53 * wall_height, y, this));
    }
    for (int y = MAIN_HEIGHT - 25 * wall_length; y <= MAIN_HEIGHT - 17 * wall_length; y = y + wall_height) {
      walls.add(new DestructibleWall(19 * wall_height, y, this));
      walls.add(new DestructibleWall(53 * wall_height, y, this));
    }

    // first bunker
    for (int x = start_center_x - 8 * wall_length; x <= start_center_x; x = x + wall_length) {
      walls.add(new Wall(x, 8 * wall_height, this));
    }
    for (int y = 0; y <= 8 * wall_height; y = y + wall_height) {
      walls.add(new Wall(start_center_x, y, this));
    }

    // second bunker
    for (int y = start_center_y; y >= start_center_y - 8 * wall_length; y = y - wall_height) {
      walls.add(new Wall(MAIN_WIDTH - 8 * wall_length, y, this));
    }
    for (int x = MAIN_WIDTH - 8 * wall_length; x <= MAIN_WIDTH; x = x + wall_length) {
      walls.add(new Wall(x, start_center_y, this));
    }

    // third bunker
    for (int x = start_center_x; x <= start_center_x + 8 * wall_length; x = x + wall_length) {
      walls.add(new Wall(x, 67 * wall_height, this));
    }
    for (int y = 67 * wall_height; y <= MAIN_HEIGHT; y = y + wall_height) {
      walls.add(new Wall(start_center_x, y, this));
    }

    // fourth bunker
    for (int y = start_center_y; y <= start_center_y + 8 * wall_length; y = y + wall_height) {
      walls.add(new Wall(8 * wall_length, y, this));
    }
    for (int x = 0; x <= 8 * wall_length; x = x + wall_length) {
      walls.add(new Wall(x, start_center_y, this));
    }


  }

}
