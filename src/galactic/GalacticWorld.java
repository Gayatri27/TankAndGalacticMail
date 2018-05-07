package galactic;

import application.Audio;
import application.GameFrame;
import application.World;
import objects.AbstractBullet;
import objects.Explosion;
import objects.Sprite;
import tanks.DestructibleWall;
import tanks.SpriteTank;
import tanks.TankBullet;
import tanks.Wall;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class GalacticWorld  extends World {

  protected final String BACKGROUND_IMAGE = "galactic/resources/Background.png";

  private final int DEFAULT_FONT_SIZE = 14;
  private final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_FONT_SIZE);
  private final int BORDER_THICKNESS = 4;
  private final int BOTTOM_BAR_THICKNESS = 25;
  private final int BOTTOM_BAR_MARGIN = 5;
  private final int HEALTH_BAR_WIDTH = 150;
  private final int HEALTH_BAR_HEIGHT = BOTTOM_BAR_THICKNESS - 10;

  private final int PLANET_MARGIN = 80;



  protected SpaceShip spaceShip;
  protected ArrayList<Planet> planets = new ArrayList<>();
  protected ArrayList<Asteroid> asteroids  = new ArrayList<>();
  protected ArrayList<Explosion> explosions  = new ArrayList<>();

  private BufferedImage main_bimg;
  private BufferedImage bimg;
  private BufferedImage bg_buffer;
  private BufferedImage frame_buffer;

  private Sprite explosionSprite;


  private GameFrame frame;


  private int score = 0;
  private final int SCORE_PLANET = 100;
  private final int SCORE_IDLE_PER_SECONDS = -2;

  protected final int EFFECTIVE_WIDTH;
  protected final int EFFECTIVE_HEIGHT;


  public GalacticWorld(GameFrame frame) {


    super(800,600);

    EFFECTIVE_WIDTH = MAIN_WIDTH;
    EFFECTIVE_HEIGHT = MAIN_HEIGHT - BOTTOM_BAR_THICKNESS;

    try{
      explosionSprite = new Sprite("galactic/resources/Explosion_small_strip6.png", 32);
    }catch (Exception e){

    }


    spaceShip = new SpaceShip(50,100, 0,this);

    addPlanets(8);

//    planets.add( new Planet(700,200, 0, this) );
//    planets.add( new Planet(600,500, 1, this) );
//    planets.add( new Planet(300,400, 2, this) );
//    planets.add( new Planet(400,100, 3, this) );
//    planets.add( new Planet(500,100, 4, this) );
//    planets.add( new Planet(300,100, 5, this) );
//    planets.add( new Planet(300,200, 6, this) );
//    planets.add( new Planet(250,300, 7, this) );


    Asteroid asteroid1 = new Asteroid(200,600,  this);
    clock.addObserver(asteroid1);
    asteroids.add(asteroid1);

    Asteroid asteroid2 = new Asteroid(300,500,  this);
    clock.addObserver(asteroid2);
    asteroids.add(asteroid2);


    Asteroid asteroid3 = new Asteroid(400,400,  this);
    clock.addObserver(asteroid3);
    asteroids.add(asteroid3);


    Asteroid asteroid4 = new Asteroid(500,300,  this);
    clock.addObserver(asteroid4);
    asteroids.add(asteroid4);



    clock.addObserver(this);
    clock.addObserver(spaceShip);

    this.frame = frame;
    frame.keyEvents.addObserver(spaceShip);

    this.setFocusable(true);

    /*
    bullets = new ArrayList<>();
    explosions = new CopyOnWriteArrayList<>();
    clock.addObserver(tank2);
    */
  }


  private void addPlanets(int num){

    for(int i = 0; i<num; i++){
      Planet planet = new Planet(0,0, i, this);

      do{
        int random_y = (int) (Math.random()*(EFFECTIVE_HEIGHT-2*PLANET_MARGIN)) + PLANET_MARGIN;
        int random_x = (int) (Math.random()*(EFFECTIVE_WIDTH-2*PLANET_MARGIN)) + PLANET_MARGIN;
        planet.setX(random_x);
        planet.setY(random_y);
        planet.updateRectangle();
      }while( collisionTracker.collides(planet,0, 0) != null);

      planets.add( planet );


    }



  }

  @Override
  public void endGame() {
    super.endGame();

    String resultText;
//    if (tank1.getHealth() <= 0) {
//      resultText = "Player 2 won!";
//    } else {
//      resultText = "Player 1 won!";
//    }
//
    resultText = "Your Score: " + score;

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


    g.drawImage(main_bimg, 0, 0, this);



    // g.drawImage(frame_buffer, 0, 0, this);

  }




  public void drawFrame(Graphics2D graphics) {
    if (bg_buffer == null) {
      drawBackground();
    }

    if (frame_buffer == null) {
     // drawFrameBuffer();
    }

    graphics.drawImage(bg_buffer, 0, 0, this);


    for (Planet planet : planets) {
      planet.draw(this, graphics);
    }

    for (Asteroid asteroid : asteroids) {
      asteroid.draw(this, graphics);
    }



    /*

    for (AbstractBullet bullet : bullets) {
      bullet.draw(this, graphics);
    }

    for (Explosion explosion : explosions) {
      explosion.draw(this, graphics);
    }
    */

    spaceShip.draw(this, graphics);



    graphics.setColor(Color.black);

    graphics.fillRect(0, MAIN_HEIGHT - BOTTOM_BAR_THICKNESS, MAIN_WIDTH, BOTTOM_BAR_THICKNESS);

    // Player Names
    graphics.setColor(Color.white);

    graphics.setFont(DEFAULT_FONT);
    int scoreTextY = MAIN_HEIGHT - (BOTTOM_BAR_THICKNESS / 3);
    graphics.drawString("Your Score: " + score, BORDER_THICKNESS + BOTTOM_BAR_MARGIN, scoreTextY);


  }

  public void drawBackground() {
    try {
      BufferedImage bg = ImageIO.read(new File(BACKGROUND_IMAGE));
      bg_buffer = (BufferedImage) createImage(MAIN_WIDTH, MAIN_HEIGHT);
      Graphics2D bg_g2 = bg_buffer.createGraphics();
      setRendingHints(bg_g2);
      bg_g2.drawImage(bg, 0, 0, MAIN_WIDTH, MAIN_HEIGHT, null);

      /*
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
      bg_g2.fillRect(0, windowHeight - BOTTOM_BAR_THICKNESS, miniMapFrameX, BOTTOM_BAR_THICKNESS);
      bg_g2.fillRect(miniMapFrameX + MINI_MAP_WIDTH, windowHeight - BOTTOM_BAR_THICKNESS, miniMapFrameX, BOTTOM_BAR_THICKNESS);

      // Player Names
      g2.setColor(Color.white);

      g2.setFont(DEFAULT_FONT);
      FontMetrics fontMetrics = g2.getFontMetrics();
      int playerTextY = windowHeight - (BOTTOM_BAR_THICKNESS / 3);
      g2.drawString("Player 1", BORDER_THICKNESS + BOTTOM_BAR_MARGIN, playerTextY);
      g2.drawString("Player 2", windowWidth - BORDER_THICKNESS - BOTTOM_BAR_MARGIN - fontMetrics.stringWidth("Player 2"), playerTextY);
      */








    } catch (Exception e) {
      e.getStackTrace();
      System.out.println(e.getMessage());
    }
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

  public void removeAsteroid(Asteroid asteroid) {
    asteroids.remove(asteroid);
    clock.deleteObserver(asteroid);
  }

  public void removePlanet(Planet planet) {
    planets.remove(planet);
    clock.deleteObserver(planet);
  }

  public void countScorePlanet() {
    score += SCORE_PLANET;
  }

  public void countScoreIdle(int seconds) {
    score += SCORE_IDLE_PER_SECONDS * seconds;
  }


  public int getEFFECTIVE_WIDTH(){
    return EFFECTIVE_WIDTH;
  }

  public int getEFFECTIVE_HEIGHT(){
    return EFFECTIVE_HEIGHT;
  }

  /*


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


  */


  /*
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
  */



}
