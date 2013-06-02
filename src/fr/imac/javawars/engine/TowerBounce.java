package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

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
		///// player, pos,      life, prix, actionField, strengh, attackspeed, projectilespeed
		super(player, position, 20,   4,    70,          8,       400,          2);
		this.bounce = 4;
		
	}

	public int getBounce() {
		return bounce;
	}

	public void setBounce(int bounce) {
		this.bounce = bounce;
	}
	
	public void removeOneBounce() {
		this.bounce --;
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
		boolean atackNextAgent = false;
		
		ConcurrentLinkedQueue<Agent> agents = tower.getPlayer().getAgents();
		Iterator<Agent> itAgent = agents.iterator();
		//iterate on all agent to find the destination of the last projectile and send a new one to the next agent
		while(itAgent.hasNext()){
			Agent agent = itAgent.next();
			
			//If whe have found 
			if(agent.equals(projectile.getAgent())){
				atackNextAgent = true;
				
				//if there are no other agent
				if(!itAgent.hasNext()){
					projectile = null;
					return true;
				}
					
				continue;
			}
			
			// If we have to bounce the projectile, to set a new target
			if(atackNextAgent && !projectile.getAgent().equals(agent) && projectile.getOptionInt() <= 4){
				projectile.addOptionInt();
				projectile.setAgent(agent);
				return false;
			}
		}//end while agents
		return true;
	}
	
	
}	
