package objects;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public abstract class Controllable extends Movable {

  HashMap<Integer, Boolean> keyStates  = new HashMap<>();

  abstract void setKeyScheme(int scheme);

  abstract void initializeKeyStates();


  void updateKeyStates(KeyEvent keyevent){

    if (keyevent.getID() == KeyEvent.KEY_PRESSED) {
      keyStates.replace(keyevent.getKeyCode(), true);
    }

    if (keyevent.getID() == KeyEvent.KEY_RELEASED) {
      keyStates.replace(keyevent.getKeyCode(), false);
    }

  }



}
