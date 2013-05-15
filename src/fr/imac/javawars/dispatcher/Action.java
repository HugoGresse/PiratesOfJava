package fr.imac.javawars.dispatcher;

import fr.imac.javawars.player.Player;

/**
 * An action is when a Player want to do anything in the game (ex : create a tower) 
 * 
 * @author Hugo
 *
 */
public abstract class Action {
	
	Player player;
	
	public Action(Player player){
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
	
	
}
