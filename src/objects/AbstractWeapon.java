package objects;

import application.CollisionTracker;

public abstract class AbstractWeapon {

  private static CollisionTracker collisionTracker;

  public abstract void shoot();

  public void setcollisionTracker(CollisionTracker collisionTracker){
    this.collisionTracker = collisionTracker;
  }

}
