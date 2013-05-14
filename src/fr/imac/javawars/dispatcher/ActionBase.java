package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Base;
import fr.imac.javawars.player.Player;


/**
 * Action related to a base
 * 
 * @author Hugo
 * @see Action
 */
public abstract class ActionBase extends Action{

	private Base base;
	
	/**
	 * Create an action for related to base
	 * @param player
	 * 				The player who sent the agent
	 * @param base
	 * 				The base you want to act on
	 */
	public ActionBase(Player player, Base base) {
		super(player);
		this.base = base;
	}

}
