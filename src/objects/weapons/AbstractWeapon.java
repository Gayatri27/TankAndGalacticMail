package objects.weapons;

import application.CollisionTracker;

public abstract class AbstractWeapon {

  static CollisionTracker collisionTracker;

  abstract void shoot();

}
