package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.Iterator;
import java.util.Map;
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
			Tower e = itr.next();
			
			checkAgentInRangeAndAttack(e);
			
			//if no projectile, continue !
			if(e.getProjectiles().isEmpty())
				continue;
			
			//if the tower have projectiles : mooves them
			Iterator<Projectile> itProj = e.getProjectiles().iterator();
			Projectile projectile;
			while(itProj.hasNext()){
				projectile = itProj.next();
				
				if(projectile.getAgent() == null)
					System.out.println("Agent supprime : projectile ?");
				
				//Update the position of the projectile, delete it if arrived and remove life to agent
				if(projectile.updateProjectile()) {
					projectile.getAgent().addLife(- e.getStrength());
					//System.out.println(projectile.getAgent().getLife());
					itProj.remove();
				}
					
			}

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
	
	
	
}
