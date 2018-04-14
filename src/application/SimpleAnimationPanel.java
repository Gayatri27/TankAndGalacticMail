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

    System.out.println("Runing");

    while( true ) {
      tank2.setX(( tank2.getX() + STEP ) % WIDTH );
      tank2.setY(( tank2.getY() + STEP ) % HEIGHT );

      this.repaint();

      try {
        Thread.sleep( 250 );
      } catch ( InterruptedException interrupted ) {

      }
    }



  }


  @Override
  public void keyPressed( KeyEvent e ) {

    switch( e.getKeyCode() ) {


      case KeyEvent.VK_LEFT:
        tank.setX(( tank.getX() - STEP ) % WIDTH );
        break;
      case KeyEvent.VK_RIGHT:
        tank.setX(( tank.getX() + STEP ) % WIDTH );
        break;
      case KeyEvent.VK_UP:
        tank.setY(( tank.getY() - STEP ) % HEIGHT );
        break;
      case KeyEvent.VK_DOWN:
        tank.setY(( tank.getY() + STEP ) % HEIGHT );
        break;


      case 65: //A
        tank2.setX(( tank2.getX() - STEP ) % WIDTH );
        break;
      case 68: //D
        tank2.setX(( tank2.getX() + STEP ) % WIDTH );
        break;
      case 87: // W
        tank2.setY(( tank2.getY() - STEP ) % HEIGHT );
        break;
      case 83: // S
        tank2.setY(( tank2.getY() + STEP ) % HEIGHT );
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
