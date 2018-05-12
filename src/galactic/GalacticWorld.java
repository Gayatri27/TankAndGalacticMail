package galactic;

import application.Audio;
import application.GameFrame;
import application.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


public class GalacticWorld extends World {

  protected final String BACKGROUND_IMAGE = "galactic/resources/Background.png";

  private Audio gameMusic;

  private final int DEFAULT_FONT_SIZE = 14;
  private final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_FONT_SIZE);
  private final int BORDER_THICKNESS = 4;
  private final int BOTTOM_BAR_THICKNESS = 25;
  private final int BOTTOM_BAR_MARGIN = 5;


  private final int PLANET_MARGIN = 80;

  protected ArrayList<SpaceShip> spaceships = new ArrayList<>();
  protected ArrayList<Planet> planets = new ArrayList<>();
  protected ArrayList<Asteroid> asteroids = new ArrayList<>();
  protected ArrayList<Explosion> explosions = new ArrayList<>();

  private BufferedImage main_bimg;
  private BufferedImage bg_buffer;

  private GameFrame frame;

  private int score = 0;
  private final int SCORE_PLANET = 100;
  private final int SCORE_IDLE_PER_SECONDS = -2;

  protected final int EFFECTIVE_WIDTH;
  protected final int EFFECTIVE_HEIGHT;


  public GalacticWorld(GameFrame frame) {
    super(800, 600);

    EFFECTIVE_WIDTH = MAIN_WIDTH;
    EFFECTIVE_HEIGHT = MAIN_HEIGHT - BOTTOM_BAR_THICKNESS;

    this.frame = frame;

    addPlanets(8);

    addAsteroids(6);
    addSpaceship(planets.get(0).getX(), planets.get(0).getY());

    clock.addObserver(this);

    gameMusic = new Audio("galactic/resources/Music.mid");
    gameMusic.play();

    this.setFocusable(true);
  }


  private void addPlanets(int num) {
    for (int i = 0; i < num; i++) {
      Planet planet = new Planet(0, 0, i, this);

      do {
        int random_y = (int) (Math.random() * (EFFECTIVE_HEIGHT - 2 * PLANET_MARGIN)) + PLANET_MARGIN;
        int random_x = (int) (Math.random() * (EFFECTIVE_WIDTH - 2 * PLANET_MARGIN)) + PLANET_MARGIN;
        planet.setX(random_x);
        planet.setY(random_y);
        planet.updateRectangle();
      } while (collisionTracker.collides(planet, planet.getWidth()) != null);

      /*

       ||
              collisionTracker.collides(planet,planet.getWidth(), planet.getHeight()) != null ||
              collisionTracker.collides(planet,-planet.getWidth(), -planet.getHeight()) != null ||
              collisionTracker.collides(planet,-planet.getWidth(), planet.getHeight()) != null ||
              collisionTracker.collides(planet,planet.getWidth(), -planet.getHeight()) != null ||
              collisionTracker.collides(planet,planet.getWidth(), 0) != null ||
              collisionTracker.collides(planet,-planet.getWidth(), 0) != null ||
              collisionTracker.collides(planet,0, planet.getHeight()) != null ||
              collisionTracker.collides(planet,0, -planet.getHeight()) != null

       */

      // TODO: make new method to take radius for collision
      planets.add(planet);
      collisionTracker.addStaticObject(planet);
    }
  }

  private void addAsteroids(int num) {

    for (int i = 0; i < num; i++) {

      int random_angle = (int) (Math.random() * (360));

      Asteroid asteroid = new Asteroid(0, 0, random_angle, this);

      do {
        int random_y = (int) (Math.random() * (EFFECTIVE_HEIGHT - 2 * PLANET_MARGIN)) + PLANET_MARGIN;
        int random_x = (int) (Math.random() * (EFFECTIVE_WIDTH - 2 * PLANET_MARGIN)) + PLANET_MARGIN;
        asteroid.setX(random_x);
        asteroid.setY(random_y);
        asteroid.updateRectangle();
      } while (collisionTracker.collides(asteroid, 0, 0) != null);

      asteroids.add(asteroid);
      collisionTracker.addMovingObject(asteroid);
      clock.addObserver(asteroid);
    }
  }

  @Override
  public void endGame() {
    super.endGame();
    gameMusic.stop();
    frame.startEndGameMenu("Your Score: " + score);
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
  }

  public void drawFrame(Graphics2D graphics) {
    if (bg_buffer == null) {
      drawBackground();
    }

    graphics.drawImage(bg_buffer, 0, 0, this);

    if (planets.size() == 0) {
      endGame();
    }

    for (Planet planet : planets) {
      planet.draw(this, graphics);
    }


    for (Asteroid asteroid : asteroids) {
      asteroid.draw(this, graphics);
    }

    for (Explosion explosion : explosions) {
      explosion.draw(this, graphics);
    }

    for (SpaceShip spaceship : spaceships) {
      spaceship.draw(this, graphics);
    }

    graphics.setColor(Color.black);
    graphics.fillRect(0, MAIN_HEIGHT - BOTTOM_BAR_THICKNESS, MAIN_WIDTH, BOTTOM_BAR_THICKNESS);
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
    } catch (Exception e) {
      e.getStackTrace();
      System.out.println(e.getMessage());
    }
  }

  public void addExplosion(int x, int y) {
    Explosion explosion = new Explosion(this, x, y);
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
    collisionTracker.removeMovingObject(asteroid);
  }

  public void removePlanet(Planet planet) {
    planets.remove(planet);
    clock.deleteObserver(planet);
    collisionTracker.removeStaticObject(planet);

  }

  public void addSpaceship(int x, int y) {
    SpaceShip spaceship = new SpaceShip(x, y, 0, this);
    spaceships.add(spaceship);
    clock.addObserver(spaceship);
    frame.keyEvents.addObserver(spaceship);
  }

  public void removeSpaceship(SpaceShip spaceship) {
    spaceships.remove(spaceship);
    clock.deleteObserver(spaceship);
    collisionTracker.removeMovingObject(spaceship);
    frame.keyEvents.deleteObserver(spaceship);

  }

  public void countScorePlanet() {
    score += SCORE_PLANET;
  }

  public void countScoreIdle(int seconds) {
    score += SCORE_IDLE_PER_SECONDS * seconds;
    if (score < 0) score = 0;
  }


  public int getEFFECTIVE_WIDTH() {
    return EFFECTIVE_WIDTH;
  }

  public int getEFFECTIVE_HEIGHT() {
    return EFFECTIVE_HEIGHT;
  }
}
