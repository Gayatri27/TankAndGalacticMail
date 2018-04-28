package application;

import static java.lang.Thread.sleep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {

  JPanel currentPanel;

  Menu menu;
  World world;
  EndGameMenu endGameMenu;
  public KeyEvents keyEvents;

 
  
  public GameFrame() {

    setTitle( "Tank Game" );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setVisible( true );
    setResizable( true );
    setSize(800,600);

    startMenu();

    keyEvents = new KeyEvents();

    this.addKeyListener(keyEvents);

    // startGame();

    /*
    JLabel image1 = new JLabel (new ImageIcon("resources/wall.png"));
    JLabel image2 = new JLabel (new ImageIcon("resources/Ball_strip9.png"));
    image1.setBounds(0,0,100,100);
    image2.setBounds(300,300, 150,150);
    add( image1 );
    add( image2 );
    */

    //Thread thread = new Thread( panel.getClock() );
    //thread.start();
  }

  public void startMenu(){
    removeCurrent();
    menu = new Menu(this);
    this.addKeyListener(menu);
    add(menu);
    revalidate();
    currentPanel = menu;
  }


  public void startGame(){
    removeCurrent();
    world = new World(this);
    // this.addKeyListener(world);


    add(world);
    revalidate();
    currentPanel = world;
  }


  public void startEndGameMenu(String resultText){
    removeCurrent();
    endGameMenu = new EndGameMenu(this, resultText);
    this.addKeyListener(endGameMenu);
    add(endGameMenu);
    revalidate();
    currentPanel = endGameMenu;
  }

  private void removeCurrent(){
    if(currentPanel != null){
      remove(currentPanel);
      if(!(currentPanel instanceof World))
        this.removeKeyListener( (KeyListener) currentPanel);
    }
  }

}
