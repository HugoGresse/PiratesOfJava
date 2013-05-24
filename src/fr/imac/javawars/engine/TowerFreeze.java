package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * A freeze tower : attack an agent an other near him related to the tower radius
 * @author Hugo
 *
 */
public class TowerFreeze extends Tower{
	private int aoe;
	
	/**
	 * Create a Freeze Tower
	 * 
	 * @param position
	 * 				position (Point)
	 * @param player
	 * 				player who own the tower
	 */
	
	public TowerFreeze(Point position, Player player) {
		///// player, pos,      life, prix, actionField, strengh, attackspeed
		super(player, position, 20,   4,    70,          4,       6);
		this.aoe = 5;
		
	}

	public int getRadius() {
		return aoe;
	}

	public void setRadius(int radius) {
		this.aoe = radius;
	}
	
	
}	
