package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Agent;
import fr.imac.javawars.engine.Base;
import fr.imac.javawars.player.Player;


/**
 * Action for sending agent to a base
 * 
 * @author Hugo
 * @see ActionAgent
 */
public class ActionAgentSend extends ActionAgent{

	Base baseTarget;
	
	/**
	 * Create an action sending an agent to a base
	 * @param player
	 * 				The player who owns the agent
	 * @param agent
	 * 				The agent you want to moove
	 * @param base
	 * 				the target base
	 */
	public ActionAgentSend(Player player, Agent agent, Base base) {
		super(player, agent);
		this.baseTarget = base;
	}
}
