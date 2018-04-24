package objects.weapons;

import application.World;
import objects.GameObject;
import objects.Tank;
import objects.bullets.TankBullet;

public class TankWeapon extends AbstractWeapon{

  private Tank tank;
  private World world;

  public TankWeapon(Tank tank, World world){

    this.tank = tank;
    this.world = world;

  }

  public void shoot(){
    TankBullet bullet = new TankBullet(tank.getX(), tank.getY(), tank.getAngle());
    world.addBullet(bullet);
  }

}