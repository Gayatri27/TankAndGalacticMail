package objects;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Animation implements Observer {
  protected Sprite sprite;
  private GameObject gameObject;

  private int x;
  private int y;

  private int startingFrame = 0;
  private int endingFrame = 0;

  protected boolean completed;

  private int counter = 0;

  public Animation() {
  }

  public Animation(GameObject gameObject, Sprite sprite, int x, int y, int startingFrame, int endingFrame ) {
    this.gameObject = gameObject;
    this.sprite = sprite;
    this.x = x;
    this.y = y;
    this.startingFrame = startingFrame;
    this.endingFrame = endingFrame;
    this.completed = false;
  }

  public boolean isCompleted() {
    return this.completed;
  }

  @Override
  public void update(Observable o, Object arg) {
    // currentFrame = ( currentFrame + 1 ) % this.sprite.frameCount();
    x = ( x + 1 ) % World.WIDTH;
    y = ( y + 1 ) % World.HEIGHT;
    System.out.println("MSR ");
  }

  public void repaint( Graphics graphics ) {
    System.out.println("ABC ABC ABC " + startingFrame + " " + endingFrame);
    if(startingFrame > endingFrame) {
      for(int i = startingFrame; i >= endingFrame; i--) {
        System.out.println("ABC " + i);
        this.gameObject.setImage(this.sprite.getFrame( i ));
      }
    } else {
      for(int i= startingFrame; i <= endingFrame; i++) {
        System.out.println("PQR " + i);
        this.gameObject.setImage(this.sprite.getFrame( i ));
      }
    }

    //graphics.drawImage(
    //this.sprite.getFrame( currentFrame ),
    //x, y, null);
  }
}
