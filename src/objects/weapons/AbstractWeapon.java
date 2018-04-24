package objects.weapons;

import objects.bullets.AbstractBullet;

import java.util.Observable;

public abstract class AbstractWeapon {

  AbstractBullet bullet;

  abstract void shoot();



}
