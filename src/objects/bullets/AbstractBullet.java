package objects.bullets;

import objects.Movable;
import application.CollisionTracker;


public abstract class AbstractBullet extends Movable {
  protected int damage;
  protected double distanceFactor;
}
