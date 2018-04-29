package objects.bullets;

import application.CollisionTracker;
import objects.GameObject;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public abstract class AbstractBullet extends GameObject  implements Observer {

  static CollisionTracker collisionTracker;


}
