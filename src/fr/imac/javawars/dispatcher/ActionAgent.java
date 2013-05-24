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
	
	private Agent agent;
	
	/**
	 * Create an action related to an agent
	 * @param player
	 * 				The player who owns the tower and 
	 * @param agent
	 * 				A given agent to act on
	 */

	public ActionAgent(Player player, Agent agent) {
		super(player);
		this.agent = agent;
		player.addAgent(agent);
	}
	
	public Agent getAgent() {
		return this.agent;
	}
	
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
}
