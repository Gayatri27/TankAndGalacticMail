package application;

import javax.swing.*;

/**
 * Created by jrob on 4/9/17.
 */
public class GameFrame extends JFrame {

  public GameFrame( World panel ) {

    setTitle( "Tank Game" );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    setVisible( true );
    setResizable( false );

    add( panel );
    pack();

    Thread thread = new Thread( panel.getClock() );
    thread.start();
  }
}
