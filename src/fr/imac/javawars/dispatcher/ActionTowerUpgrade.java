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
	}
	
	public int getUpgrade(){
		return towerUpgrade;
	}
	
}
