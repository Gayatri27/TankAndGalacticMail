package application;

import javax.swing.*;
import java.util.Observer;

public class GameFrame extends JFrame {

  JPanel currentPanel;
  public KeyEvents keyEvents;

  public GameFrame() {

    setTitle( "Tank Game" );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setVisible( true );
    setResizable( true );
    setSize(800,600);

    keyEvents = new KeyEvents();
    this.addKeyListener(keyEvents);

    showMainMenu();
  }

  public void showMainMenu(){
    addPanel( new MainMenu(this) );
  }

  public void showGameGuide(){
    addPanel( new GameGuide(this) );
  }

  public void startGame(){
    addPanel( new World(this) );
  }

  public void startEndGameMenu(String resultText){
    addPanel( new EndGameMenu(this, resultText) );
  }

  private void addPanel(JPanel panel){

    if(currentPanel != null){
      remove(currentPanel);
      keyEvents.deleteObserver((Observer) currentPanel);
      currentPanel = null;

    }

    add(panel);
    revalidate();
    currentPanel = panel;

    if(!(panel instanceof World))
      keyEvents.addObserver((Observer) panel);
  }


}
