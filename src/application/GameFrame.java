package application;

import galactic.GalacticWorld;
import javafx.application.Application;
import tanks.TanksWorld;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public class GameFrame extends JFrame {

  JPanel currentPanel;
  public KeyEvents keyEvents;
  GameApplication gameApplication;

  public GameFrame(GameApplication gameApplication) {

    this.gameApplication = gameApplication;
    setTitle(gameApplication.getTitle());
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setVisible( true );
    setResizable( true );
    //setSize(gameApplication.getFrameWidth(), gameApplication.getFrameHeight());
    getContentPane().setPreferredSize(new Dimension(gameApplication.getFrameWidth(), gameApplication.getFrameHeight()));
    pack();


    keyEvents = new KeyEvents();
    this.addKeyListener(keyEvents);

    showMainMenu();
  }

  public void showMainMenu(){
    addPanel(new MainMenu(this) );
  }

  public void showGameGuide(){
    addPanel(new GameGuide(this, gameApplication.getGameGuide() )) ;
  }

  public void startGame(){
    addPanel( gameApplication.getWorld() );
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

    if(!(panel instanceof GalacticWorld))
      keyEvents.addObserver((Observer) panel);
  }

}
