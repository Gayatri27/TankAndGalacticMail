package tanks;

import objects.AbstractWeapon;
import objects.GameObject;

public class TankWeapon extends AbstractWeapon {

	private GameObject tank;
	private TanksWorld world;
	int reloadSpeed = 5;
	int reloadCounter = 0;


	public TankWeapon(GameObject tank, TanksWorld world) {

		this.tank = tank;
		this.world = world;
		setcollisionTracker( world.getCollisionTracker() );
	}

	public void shoot() {
		reloadCounter ++;
		if(reloadCounter >= reloadSpeed){
			reloadCounter = 0;
			TankBullet bullet = new TankBullet(tank, world);
			world.addBullet(bullet);
		}
	}



}
