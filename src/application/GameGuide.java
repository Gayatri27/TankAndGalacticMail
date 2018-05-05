package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class GameGuide extends JPanel implements Observer {

  GameFrame gameFrame;

  GameGuide(GameFrame gameFrame, String gameGuideText){

    this.gameFrame = gameFrame;

    JLabel guideText = new JLabel(gameGuideText, JLabel.LEFT);
    guideText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
    guideText.setForeground(Color.WHITE);
    add(guideText);


    JLabel continueText = new JLabel("Hit the space key to continue...", JLabel.CENTER);
    continueText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    continueText.setForeground(Color.WHITE);
    continueText.setVerticalTextPosition(JLabel.CENTER);
    add(continueText);

    GridLayout experimentLayout = new GridLayout(0,1);
    setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
    setLayout(experimentLayout);

    setBackground(Color.BLACK);
  }



  @Override
  public void update(Observable observable, Object arg) {

    if(observable instanceof KeyEvents){
      KeyEvent keyEvent = (KeyEvent) arg;
      if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
        gameFrame.showMainMenu();
      }
    }
  }


}
