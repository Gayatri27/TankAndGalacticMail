package objects.weapons;

import application.World;
import objects.GameObject;
import objects.bullets.TankBullet;

public class TankWeapon extends AbstractWeapon {

	private GameObject tank;
	private World world;
	int reloadSpeed = 5;
	int reloadCounter = 0;


	public TankWeapon(GameObject tank, World world) {

		this.tank = tank;
		this.world = world;
		collisionTracker = world.getCollisionTracker();
	}

	public void shoot() {
		reloadCounter ++;
		if(reloadCounter >= reloadSpeed){
			reloadCounter = 0;
			//TankBullet bullet = new TankBullet(tank, tank.getTankCenterX(), tank.getTankCenterY(), tank.getAngle());
			TankBullet bullet = new TankBullet(tank, world);
			world.addBullet(bullet);
			// collisionTracker.addMovingObject(bullet);
		}
	}



}
