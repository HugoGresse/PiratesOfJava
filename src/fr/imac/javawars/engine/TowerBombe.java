package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

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
		///// player, pos,      life, prix, actionField, strengh, attackspeed,  projectilespeed
		super(player, position, 20,   4,    80,          15,       14,          2 );
		this.aoe = 20;
		
	}

	public int getAoe() {
		return aoe;
	}

	public void setAoe(int aoe) {
		this.aoe = aoe;
	}

	/**
	 *  Process the projectile and the effect to it (bounce) for the given projectile
	 * @param tower
	 * 				the tower who owns the projectiles
	 * @param projectile
	 * 				the specified projectile (for create the dummie effect)
	 * @return true if the agent should be remove
	 */
	@Override
	public boolean processProjectileArrived(Tower tower, Projectile projectile){
		
		return true;
	}
}	
