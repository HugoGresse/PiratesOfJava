package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.engine.TowerUpgrade;
import fr.imac.javawars.player.Player;


/**
 * Action for upgrading tower
 * 
 * @author Hugo
 * @see ActionTower
 */
public class ActionTowerUpgrade extends ActionTower{

	private TowerUpgrade towerUpgrade;
	
	
	/**
	 * Create an action for upgrading the given tower
	 * @param player
	 * 				THe player who own the tower and 
	 * @param towerUpgrade
	 * 				A given upgrade for a tower
	 */
	public ActionTowerUpgrade(Player player, Tower tower, TowerUpgrade towerUpgrade) {
		super(player, tower);
		this.towerUpgrade = towerUpgrade;
	}

}
