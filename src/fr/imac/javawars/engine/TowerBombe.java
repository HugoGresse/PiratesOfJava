package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * Attack strong and in aoe
 * @author Hugo
 *
 */
public class TowerBombe extends Tower{
	
	/**
	 * Area of effect (damage Zone)
	 */
	private int aoe;
	
	/**
	 * Create a Bombe Tower
	 * 
	 * @param position
	 * 				position (Point)
	 * @param player
	 * 				player who own the tower
	 */
	
	public TowerBombe(Point position, Player player) {
		///// player, pos,      life, prix, actionField, strengh, attackspeed
		super(player, position, 20,   4,    20,          15,       4);
		this.aoe = 5;
		
	}

	public int getAoe() {
		return aoe;
	}

	public void setAoe(int aoe) {
		this.aoe = aoe;
	}
	
	
}	
