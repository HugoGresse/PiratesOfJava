package fr.imac.javawars.engine;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.player.Player;



/**
 * This class process tower, and so projectiles
 * 
 * @author Hugo
 *
 */
public class ProcessorTower {
	
	Map.Entry<Integer, Player> entry;
	Iterator<Map.Entry<Integer, Player>> itPlayer;
	Iterator<Agent> itAg;
	
	public ProcessorTower(){
		
		
	};
	
	
	/**
	 * Process the given collections for moove projectiles 
	 * Call in run() of Engine
	 * 
	 * @return
	 * 			return true if something changes for any tower (and projectiles)
	 */
	@SuppressWarnings("unchecked")
	public boolean process(CopyOnWriteArrayList<Tower> towers){
		int i = 1;
		Iterator<Tower> itr = towers.iterator();
		boolean change = false;
		//for all towers
		while(itr.hasNext()){
			Tower tower = itr.next();
			
			checkAgentInRangeAndAttack(tower);
			
			//if no projectile, continue !
			if(tower.getProjectiles().isEmpty())
				continue;
			
			processProjectile(tower);

			change = true;
			
			//System.out.println("processTower : "+i);
			i++;
		}
		return change;
	}
	
	/**
	 * Check if there are any agents in the actionField of the tower
	 */
	private void checkAgentInRangeAndAttack(Tower t){
		
		itPlayer = JavaWars.getEngine().getPlayers().entrySet().iterator();
		
		
        while (itPlayer.hasNext()) {
                entry = itPlayer.next();
                //Si le joueur est le même que celui de la tour
                //TODO : change sign
                if(entry.getKey() != t.getPlayer().getPlayerNumber())
                	continue;
                
                //On parcour les agents
				itAg = entry.getValue().getAgents().iterator();
				
				while(itAg.hasNext()){
					Agent agent = itAg.next();
					//if distance between the tower and the agent if more than tower ActionField, continue, else, create a projectile
					if(agent.getPosition().distance(t.getPosition()) > t.getActionField())
						continue;
					
					//if(t.getAttackSpeed())
					//	continue;
					
					//we attack the agent !
					t.attackAgent(agent);
					
					
				} // end whilte agents
                
        }
        
        
        
        
		
	}
	
	
	/**
	 * Process projectiles (moove it, delet it, etc)
	 * @param tower
	 * 				the tower who should have projectiles
	 */
	private void processProjectile(Tower tower){
		// ======= PROJECTILE
		//if the tower have projectiles : mooves them
		Iterator<Map.Entry<Integer, Projectile>> itProj = tower.getProjectiles().entrySet().iterator();
		Projectile projectile;
		
		while(itProj.hasNext()){
			Map.Entry<Integer, Projectile> entryProj = itProj.next();
			projectile = entryProj.getValue();
			if(projectile.getAgent() == null)
				System.out.println("Agent supprime : projectile ? should correct this");
			
			//Update the position of the projectile, delete it if arrived and remove life to agent
			if(! projectile.updateProjectile())
				continue;
			
			//PROJECTILE ARRIVED !
			projectile.getAgent().addLife(- tower.getStrength());
			
			//process end effect on projectil, if true, delet it
			if(processProjectileWhenArriveToDestination(tower, projectile))
				itProj.remove();	
			
			
				
		}
		
	}
	
	/**
	 *  Process the projectile and the effect to it (bounce for example) for the given projectile
	 * @param tower
	 * 				the tower who owns the projectiles
	 * @param projectile
	 * 				the specified projectile (for create the dummie effect)
	 * @return true if the agent should be remove
	 */
	private boolean processProjectileWhenArriveToDestination(Tower tower, Projectile projectile){
		if(tower instanceof TowerBounce){
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
		} else {
			projectile = null;
			return true;
		}
		
	}
}
