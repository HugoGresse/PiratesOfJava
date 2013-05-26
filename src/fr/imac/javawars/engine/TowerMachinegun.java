package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * Tower which attack really fast but projectiles are not really strong
 * @author Hugo
 *
 */
public class TowerMachinegun extends Tower{
	
	/**
	 * Create a Machinegun Tower
	 * 
	 * @param position
	 * 				position (Point)
	 * @param player
	 * 				player who own the tower
	 */
	
	public TowerMachinegun(Point position, Player player) {
		///// player, pos,      life, prix, actionField, strengh, attackspeed, , projectilespeed
		super(player, position, 20,   3,    85,          5,       200,             3);
		
	}
	
	
}	
