package fr.imac.javawars.dispatcher;

import java.awt.Point;

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

	Base baseStart;
	Base baseTarget;
	
	/**
	 * Create an action creating an agent and sending him to a base
	 * @param player
	 * 				The player who owns the agent
	 * @param start
	 * 				the start base, required to access to the distance map of the base
	 * @param target
	 * 				the target base, required to access to the distance map of the base
	 */
	public ActionAgentSend(Player player, Base start, Base target) {
		super(player);
		//the agent will be created in the engine when the action is received
		this.baseStart = start;
		this.baseTarget = target;
	}

	public Base getBaseTarget() {
		return this.baseTarget;
	}
	
	public Base getBaseStart() {
		return this.baseStart;
	}

}
