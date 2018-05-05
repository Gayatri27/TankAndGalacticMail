package objects;

import application.CollisionTracker;

import java.awt.*;

public abstract class Movable extends GameObject {
  protected int speed_turning = 8;
  protected int speed_moving = 6;
  protected static CollisionTracker collisionTracker;

  public void rotateRight(){
    if(angle - speed_turning <= 0){
      angle = 359;
    }else{
      angle -= speed_turning;
    }
  }

  public void rotateLeft(){
    if(angle + speed_turning >= 360){
      angle = 0;
    }else{
      angle += speed_turning;
    }
  }

  public double getDx(){
    return 1 * speed_moving * Math.cos(Math.toRadians(angle));
  }

  public double getDy(){
    return -1 * speed_moving * Math.sin(Math.toRadians(angle));
  }


  public void move(double dx, double dy){
    if(collisionTracker.collides(this, dx, dy) == null){
      x += dx;
      y += dy;
      setRectangle(new Rectangle((int)x, (int)y, getWidth(), getHeight()));
    }
  }



}
