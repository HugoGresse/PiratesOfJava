package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.player.Player;


/**
 * Create an action for : User want to create a Tower at specified Point
 * 
 * 
 *  @author Hugo
 *  @see ActionTower
 */
public class ActionTowerCreate extends ActionTower{

	/**
	 * Create an action for : User want to create a Tower at specified Point
	 * @param player
	 * 				The player who want to create a tower
	 * @param tower
	 * 				the tower he want to create
	 */
	public ActionTowerCreate(Player player, Tower t) {
		super(player, t);
		
	}

	
}
