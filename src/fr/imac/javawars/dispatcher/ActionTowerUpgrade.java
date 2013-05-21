package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.player.Player;


/**
 * Action for upgrading tower
 * 
 * @author Hugo
 * @see ActionTower
 */
public class ActionTowerUpgrade extends ActionTower{

	private int towerUpgrade;
	private int price;
	
	/**
	 * Create an action for upgrading the given tower
	 * @param player
	 * 				THe player who own the tower and 
	 * @param tower
	 * 				the tower you want to upgrade
	 * @param up
	 * 				the upgrade you want to do : 1 for range, 2 for damage
	 */
	public ActionTowerUpgrade(Player player, Tower tower, int up) {
		super(player, tower);
		this.towerUpgrade = up;
		if(up == 1)
			price = 1;
		else if(up ==2)
			price = 2;
	}
	public int getTowerUpgrade() {
		return towerUpgrade;
	}

	public int getPrice() {
		return price;
	}

	public void setTowerUpgrade(int towerUpgrade) {
		this.towerUpgrade = towerUpgrade;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
