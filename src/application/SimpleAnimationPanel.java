package application;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimpleAnimationPanel extends AnimationPanel implements KeyListener {

  public SimpleAnimationPanel(){
    super();

    this.addKeyListener( this );
    this.setFocusable( true );
  }

  @Override
  public void run() {

    System.out.println("Running");

    while(true) {
      //tank2.setX(( tank2.getX() + STEP ) % WIDTH );
      //tank2.setY(( tank2.getY() + STEP ) % HEIGHT );

      this.repaint();
      try {
        Thread.sleep(50);
      } catch (InterruptedException interrupted) {

      }
    }
  }


  @Override
  public void keyPressed(KeyEvent e) {

    switch(e.getKeyCode()) {


      case KeyEvent.VK_LEFT:
        if(tank.getRotation() != 180) {
          addAnimation(tank.rotate(180));
        } else {
          tank.setX(( tank.getX() - STEP ) % WIDTH );
        }
        break;
      case KeyEvent.VK_RIGHT:
        if(tank.getRotation() != 0) {
          addAnimation(tank.rotate(0));
        } else {
          tank.setX(( tank.getX() + STEP ) % WIDTH );
        }
        break;
      case KeyEvent.VK_UP:
        if(tank.getRotation() != 90) {
          addAnimation(tank.rotate(90));
        } else {
          tank.setY(( tank.getY() - STEP ) % HEIGHT );
        }
        break;
      case KeyEvent.VK_DOWN:
        if(tank.getRotation() != 270) {
          addAnimation(tank.rotate(270));
        } else {
          tank.setY(( tank.getY() + STEP ) % HEIGHT );
        }
        break;
      case 65: //A
        if(tank2.getRotation() != 180) {
          addAnimation(tank2.rotate(180));
        } else {
          tank2.setX(( tank2.getX() - STEP ) % WIDTH );
        }
        break;
      case 68: //D
        if(tank2.getRotation() != 0) {
          addAnimation(tank2.rotate(0));
        } else {
          tank2.setX(( tank2.getX() + STEP ) % WIDTH );
        }
        break;
      case 87: // W
        if(tank2.getRotation() != 90) {
          addAnimation(tank2.rotate(90));
        } else {
          tank2.setY(( tank2.getY() - STEP ) % HEIGHT );
        }
        break;
      case 83: // S
        if(tank2.getRotation() != 270) {
          addAnimation(tank2.rotate(270));
        } else {
          tank2.setY(( tank2.getY() + STEP ) % HEIGHT );
        }
        break;


      case KeyEvent.VK_F:
        // addAnimation( new Animation( this.explosion, tank.getX(), tank.getY(), 3, false, "Explosion") );
        break;
    }

    this.repaint();
  }


  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
