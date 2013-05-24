package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * Attack far way, strong but not speed
 * @author Hugo
 *
 */
public class TowerSniper extends Tower{
	
	
	/**
	 * Create a Sniper Tower
	 * 
	 * @param position
	 * 				position (Point)
	 * @param player
	 * 				player who own the tower
	 */
	
	public TowerSniper(Point position, Player player) {
		///// player, pos,      life, prix, actionField, strengh, attackspeed
		super(player, position, 20,   5,    1000,          20,       20);
		
	}

	
}	
