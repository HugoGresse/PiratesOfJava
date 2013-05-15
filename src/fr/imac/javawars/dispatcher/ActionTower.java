package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.player.Player;

/**
 * An action is when a Player want to do anything in the game 
 * Here, action related to tower
 * 
 * @author Hugo
 * @see Action
 */
public abstract class ActionTower extends Action {

	Tower tower;
	
	/**
	 * Constructor
	 * 
	 * @param player
	 * 				The player who own the tower
	 * @param tower
	 * 				the tower
	 */
	public ActionTower(Player player, Tower tower) {
		super(player);
		this.tower = tower;
		
	}

	public Tower getTower() {
		return tower;
	}

	
}
