package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * Attack one agents and (number of bounce) other agent around
 * @author Hugo
 *
 */
public class TowerBounce extends Tower{
	
	/**
	 * Number of bounce
	 */
	private int bounce;
	
	/**
	 * Create a Bounce Tower
	 * 
	 * @param position
	 * 				position (Point)
	 * @param player
	 * 				player who own the tower
	 */
	
	public TowerBounce(Point position, Player player) {
		///// player, pos,      life, prix, actionField, strengh, attackspeed
		super(player, position, 20,   4,    80,          8,       12);
		this.bounce = 4;
		
	}

	public int getBounce() {
		return bounce;
	}

	public void setBounce(int bounce) {
		this.bounce = bounce;
	}
	
	
}	
