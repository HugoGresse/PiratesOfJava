package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * A basic tower which send little missile to a unit. Little but powerfull.
 * @author Hugo
 *
 */
public class TowerMissile extends Tower{
	
	/**
	 * Create a Missile Tower
	 * 
	 * @param position
	 * 				position (Point)
	 * @param player
	 * 				player who own the tower
	 */
	public TowerMissile(Point position, Player player) {
		///// player, pos,      life, prix, actionField, strengh, attackspeed
		super(player, position, 20,   2,    100,          10,       10);
		
	}
	
}	
