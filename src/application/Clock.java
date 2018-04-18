package application;

import java.util.Observable;

import static java.lang.Thread.sleep;

public class Clock extends Observable implements Runnable {
  private final int DURATION = 2000; // testing

  @Override
  public void run() {

    while (true) {
      try {
        sleep(DURATION);


        System.out.println("game clock running");

        setChanged();
        notifyObservers();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
