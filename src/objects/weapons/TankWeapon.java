package objects.weapons;

import application.World;
import objects.GameObject;
import objects.bullets.TankBullet;

public class TankWeapon extends AbstractWeapon{

  private GameObject tank;
  private World world;

  public TankWeapon(GameObject tank, World world){

    this.tank = tank;
    this.world = world;

  }

  public void shoot(){
    TankBullet bullet = new TankBullet(tank.getX(), tank.getY());
    world.addBullet(bullet);
  }

}