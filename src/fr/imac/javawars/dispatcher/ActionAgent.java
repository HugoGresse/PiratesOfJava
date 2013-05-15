package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Agent;
import fr.imac.javawars.player.Player;


/**
 * Action related to agent
 * 
 * @author Hugo
 * @see Action
 */
public abstract class ActionAgent extends Action{
	
	private Agent agent;
	
	/**
	 * Create an actio nrelated to Agent
	 * @param player
	 * 				THe player who own the tower and 
	 * @param agent
	 * 				A given agent to act on
	 */
	public ActionAgent(Player player, Agent agent) {
		super(player);
		this.agent = agent;
	}

}
