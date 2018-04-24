package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.function.Function;

public class EndGameMenu extends JPanel implements KeyListener {

  JLabel l1,l2,l3;

  private MainMenu mainMenu;
  private GameFrame gameFrame;

  EndGameMenu(GameFrame gameFrame){

    this.gameFrame = gameFrame;

    mainMenu = new MainMenu();

    mainMenu.addMenuItem("Player 1 Won!", "");
    mainMenu.addMenuItem("Play Again", "startGame");
    mainMenu.addMenuItem("Exit Game", "exitGame");
    mainMenu.populate(this);


    GridLayout experimentLayout = new GridLayout(0,1);
    setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
    setLayout(experimentLayout);

    setBackground(Color.BLACK);
  }

  public void startGame(){
    System.out.println("staring the game");
    gameFrame.startGame();
  }

  public void exitGame(){
    System.out.println("exiting the game");
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setColor(Color.red);

  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

    switch(e.getKeyCode()) {
      case KeyEvent.VK_UP:
        mainMenu.previous();
        break;
      case KeyEvent.VK_DOWN:
        mainMenu.next();
        break;

      case KeyEvent.VK_ENTER:
        mainMenu.menuSelected(this);
        break;
    }

    this.repaint();
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

}
