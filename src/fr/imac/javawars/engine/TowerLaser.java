package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * A Laser Tower attacks louder when attacking the same agent.
 * The speed of increasing damage is related to the increaseDamageSpeed
 * 
 * @author Hugo
 *
 */
public class TowerLaser extends Tower{
	
	/**
	 * how much fast does strengh up
	 */
	private int increaseAttackDamage;
	
	/**
	 * Create a Laser Tower
	 * 
	 * @param position
	 * 				position (Point)
	 * @param player
	 * 				player who own the tower
	 */
	
	public TowerLaser(Point position, Player player) {
		///// player, pos,      life, prix, actionField, strengh, attackspeed
		super(player, position, 20,   6,    70,          4,       1);
		this.increaseAttackDamage = 1;
		
	}

	public int getIncreaseDamageSpeed() {
		return increaseAttackDamage;
	}

	public void setIncreaseDamageSpeed(int increaseDamageSpeed) {
		this.increaseAttackDamage = increaseDamageSpeed;
	}
	
	
	
	
}	
