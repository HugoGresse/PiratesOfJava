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
	 * @param baseTarget
	 * 				the target base, required to access to the distance map of the base
	 */
	public ActionAgentSend(Player player, Base start, Base target) {
		super(player);
		// Base start is just used to initialize agents so it is not needed to stock it
		// WARNING : not passed start.getPosition() because we don't want to modify coordinates of the base
		//super(player, new Agent(100, new Point(start.getPosition()), player, 1, start, target)); // add the agent to the player's agents list
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
