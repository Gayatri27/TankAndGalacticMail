package objects;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Animation implements Observer {
  protected Sprite sprite;

  private int x;
  private int y;
  private int rotation;

  protected int currentFrame;

  protected boolean completed;

  private int counter = 0;

  public Animation() {
  }

  public Animation( Sprite sprite, int x, int y, int currentFrame, int rotation ) {
    this.sprite = sprite;
    this.x = x;
    this.y = y;
    this.rotation = rotation;
    this.currentFrame = currentFrame;
    this.completed = false;
  }

  public boolean isCompleted() {
    return this.completed;
  }

  @Override
  public void update(Observable o, Object arg) {
    currentFrame = ( currentFrame + 1 ) % this.sprite.frameCount();
    x = ( x + 1 ) % World.WIDTH;
    y = ( y + 1 ) % World.HEIGHT;
  }

  public void repaint( Graphics graphics ) {
    graphics.drawImage(
      this.sprite.getFrame( currentFrame ),
      x, y, null
    );
  }
}
