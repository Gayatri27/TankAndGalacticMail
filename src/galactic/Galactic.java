package galactic;

import application.GameApplication;
import application.GameFrame;
import application.World;

public class Galactic extends GameApplication {

  public Galactic() {

    title = "Galactic Mail";
    frameWidth = 800;
    frameHeight = 600;
    gameGuide = "<html>Drive your Tank against the enemy and blast them off. <br><br>" +
            "Player 1 keys:<br> A: turn left  S: move backward D: turn right W: move forward  Space: Fire <br><br>" +
            "Player 2 keys:<br> J: turn left  K: move backward L: turn right I: move forward  Enter: Fire </html>";
    }

  @Override
  public World getWorld() {
    return new GalacticWorld(gameFrame);
  }
}


