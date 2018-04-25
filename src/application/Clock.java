package application;

import java.util.Observable;

import static java.lang.Thread.sleep;

public class Clock extends Observable implements Runnable {

  private final int DURATION = 25; // testing
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
