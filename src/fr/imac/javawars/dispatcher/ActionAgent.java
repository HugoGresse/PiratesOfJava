package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Agent;
import fr.imac.javawars.player.Player;


/**
 * Action related to an agent
 * 
 * @author Hugo
 * @see Action
 */
public abstract class ActionAgent extends Action{
	
	//private Agent agent;
	
	/**
	 * Create an action related to an agent
	 * @param player
	 * 				The player who owns the tower and 
	 * @param agent
	 * 				A given agent to act on
	 */

	public ActionAgent(Player player) {
		super(player);
	}
}
