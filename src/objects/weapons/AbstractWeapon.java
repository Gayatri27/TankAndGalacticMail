package objects.weapons;

import application.CollisionTracker;
import objects.bullets.AbstractBullet;

import java.util.Observable;

public abstract class AbstractWeapon {

  static CollisionTracker collisionTracker;


  AbstractBullet bullet;

  abstract void shoot();



}
