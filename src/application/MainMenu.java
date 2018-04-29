package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class MainMenu extends JPanel implements Observer {

  ListMenu listMenu;
  GameFrame gameFrame;

  MainMenu(GameFrame gameFrame){

    this.gameFrame = gameFrame;

    listMenu = new ListMenu();

    listMenu.addMenuItem("Start Game", "startGame");
    listMenu.addMenuItem("How to play", "showGuide");
    listMenu.addMenuItem("Exit Game", "exitGame");
    listMenu.populate(this);


    GridLayout experimentLayout = new GridLayout(0,1);
    setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
    setLayout(experimentLayout);

    setBackground(Color.BLACK);
  }

  public void startGame(){
    gameFrame.startGame();
  }

  public void exitGame(){
    System.exit(0);
  }

  public void showGuide(){
    gameFrame.showGameGuide();
  }

  @Override
  public void update(Observable observable, Object arg) {

    if(observable instanceof KeyEvents){

      KeyEvent keyEvent = (KeyEvent) arg;

      if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
        switch(keyEvent.getKeyCode()) {
          case KeyEvent.VK_UP:
            listMenu.previous();
            break;

          case KeyEvent.VK_DOWN:
            listMenu.next();
            break;

          case KeyEvent.VK_ENTER:
            listMenu.menuSelected(this);
            break;
        }

        this.repaint();
      }
    }
  }
}
