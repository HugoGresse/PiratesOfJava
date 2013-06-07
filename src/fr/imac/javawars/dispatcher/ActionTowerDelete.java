package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.player.Player;


/**
 * Create an action for : User wants to delete a Tower at specified Point
 * 
 */
public class ActionTowerDelete extends ActionTower{

	/**
	 * Create an action for : User wants to delete a Tower 
	 * @param player
	 * 				The player who want to delete a tower
	 * @param t
	 * 				the tower he wants to delete
	 */
	public ActionTowerDelete(Player player, Tower t) {
		super(player, t);
		
	}

	
}
