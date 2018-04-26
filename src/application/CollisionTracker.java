package application;

import objects.GameObject;
import objects.bullets.TankBullet;

import java.awt.*;
import java.util.ArrayList;

public class CollisionTracker {

  private ArrayList<GameObject> staticObjects;
  private ArrayList<GameObject> movingObjects;

  CollisionTracker(){
    staticObjects = new ArrayList<>();
    movingObjects = new ArrayList<>();
  }


  public GameObject collides(GameObject object, double dx, double dy){

//    if(object instanceof TankBullet){
//      System.out.println( object.getX() + " " + object.getY() + " " + object.getHeight() + " " + object.getWidth() + " " );
//    }

    Rectangle projection = new Rectangle(object.getX()+(int)dx, (int)object.getY()+(int)dy, object.getHeight(), object.getWidth());

    for(GameObject staticObject: staticObjects){
      if( staticObject.getRectangle().intersects(projection)){
        return staticObject;
      }
    }

    for(GameObject movingObject: movingObjects){
      if(movingObject != object){
        if( movingObject.getRectangle().intersects(projection)){
          //System.out.println("returnign true from moving objects");
          return movingObject;
        }
      }
    }

    return null;

  }

  public void removeStaticObject(GameObject object){
    staticObjects.remove(object);
  }

  public void removeMovingObject(GameObject object){
    movingObjects.remove(object);
  }

  public void addStaticObject(GameObject object){
    staticObjects.add(object);
  }

  public void addMovingObject(GameObject object){
    movingObjects.add(object);
  }


}