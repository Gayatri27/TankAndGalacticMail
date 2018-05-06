package application;

import galactic.Galactic;
import galactic.GalacticWorld;

public abstract class GameApplication {


  protected static String title = "Galactic Mail";
  protected static int frameWidth = 800;
  protected static int frameHeight = 600;
  protected static GameFrame gameFrame;
  protected static String gameGuide;


  public String getTitle() {
    return title;
  }

  public int getFrameWidth() {
    return frameWidth;
  }

  public int getFrameHeight() {
    return frameHeight;
  }

  public String getGameGuide() {
    return gameGuide;
  }

  public void start(){
    gameFrame = new GameFrame(this);
  }


  abstract public World getWorld();

}
