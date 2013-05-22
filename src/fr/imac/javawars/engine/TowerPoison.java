package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * Make dommage every 0.5s
 * @author Hugo
 *
 */
public class TowerPoison extends Tower{

	/**
	 * Create a Poison Tower
	 * 
	 * @param position
	 * 				position (Point)
	 * @param player
	 * 				player who own the tower
	 */
	
	public TowerPoison(Point position, Player player) {
		///// player, pos,      life, prix, actionField, strengh, attackspeed
		super(player, position, 20,   4,    15,          6,       8);
		
	}

	
}	
