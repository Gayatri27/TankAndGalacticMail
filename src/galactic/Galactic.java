package galactic;

import application.GameApplication;
import application.World;

public class Galactic extends GameApplication {

  public Galactic() {

    title = "Galactic Mail";
    frameWidth = 800;
    frameHeight = 600;
    gameGuide = "<html>You play an intergalactic mail carrier who must deliver mail to a number of inhabited moons. You must safely steer a course from moon to moon while avoiding dangerous asteroids. <br><br>" +
        "Player keys:<br> A: turn left<br> D: turn right<br> Space: Fly <br><br>";
  }

  @Override
  public World getWorld() {
    return new GalacticWorld(gameFrame);
  }
}
