package application;

import java.util.Observable;

import static java.lang.Thread.sleep;

public class Clock extends Observable implements Runnable {

  private final int DURATION = 42; // testing
  private Thread thread;

  Clock(){
    thread = new Thread(this);
  }

  void start(){
    thread.start();
  }

  @Override
  public void run() {

    while (true) {
      try {
        sleep(DURATION);

        setChanged();
        notifyObservers();

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}


/**
 *

 private static final long serialVersionUID = 8815532421606947532L;
 private final int DURATION = 35; // testing
 private World panel;



 @Override
 public void run() {
 while (true) {
 panel.repaint();
 try {
 sleep(DURATION);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
 }

 */
