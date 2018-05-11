package application;

import objects.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class CollisionTracker {

  private ArrayList<GameObject> staticObjects;
  private ArrayList<GameObject> movingObjects;

  CollisionTracker() {
    staticObjects = new ArrayList<>();
    movingObjects = new ArrayList<>();
  }


  public GameObject collides(GameObject object, double dx, double dy) {

    Rectangle projection = new Rectangle(object.getX() + (int) dx, (int) object.getY() + (int) dy, object.getHeight(), object.getWidth());

    return collides(object, projection);
  }


  public GameObject collides(GameObject object, double buffer) {

    Rectangle projection = new Rectangle((int) (object.getX() - buffer),
        (int) (object.getY() - buffer),
        (int) (object.getHeight() + 2 * buffer),
        (int) (object.getWidth() + 2 * buffer));

    return collides(object, projection);

  }


  public GameObject collides(GameObject object, Rectangle projection) {

    for (GameObject staticObject : staticObjects) {
      if (staticObject != object && staticObject.getRectangle().intersects(projection)) {
        return staticObject;
      }
    }

    for (GameObject movingObject : movingObjects) {
      if (movingObject != object && movingObject.getRectangle().intersects(projection)) {
        return movingObject;
      }
    }

    return null;
  }


  // formula ref: https://stackoverflow.com/a/9325084
  public int calculateIntersection(GameObject A, GameObject B){

    int XA1 = A.getX();
    int XA2 = A.getX() + A.getWidth();

    int YA1 = A.getY();
    int YA2 = A.getY() + A.getHeight();

    int SA = A.getWidth() * A.getHeight();


    int XB1 = B.getX();
    int XB2 = B.getX() + B.getWidth();

    int YB1 = B.getY();
    int YB2 = B.getY() + B.getHeight();

    int SB = B.getWidth() * B.getHeight();

    double SI = Math.max(0, Math.min(XA2, XB2) - Math.max(XA1, XB1)) * Math.max(0, Math.min(YA2, YB2) - Math.max(YA1, YB1));

    double SU = SA + SB - SI;

    int intersection = (int) (SI / SU * 100);

    return intersection;

  }

  public void removeStaticObject(GameObject object) {
    staticObjects.remove(object);
  }

  public void removeMovingObject(GameObject object) {
    movingObjects.remove(object);
  }

  public void addStaticObject(GameObject object) {
    staticObjects.add(object);
  }

  public void addMovingObject(GameObject object) {
    movingObjects.add(object);
  }


}
