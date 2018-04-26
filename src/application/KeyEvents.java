package application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

public class KeyEvents extends Observable implements KeyListener {


  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    setChanged();
    notifyObservers(e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    setChanged();
    notifyObservers(e);
  }



}
