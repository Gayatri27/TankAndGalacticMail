package objects;

import objects.weapons.AbstractWeapon;
import objects.weapons.TankWeapon;

import java.io.IOException;

public class Tank extends GameObject {


    AbstractWeapon weapon;

    public Tank( Sprite sprite, int startingFrame, int startingX, int startingY, int rotation ) throws IOException {
      super(sprite, startingFrame, startingX, startingY, rotation);

      weapon = new TankWeapon();

    }

  }
