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
	
	public void testHugo(){
		//TEST
		JavaWars.getEngine().getPlayers().get(2).getAgents().addLast(new Agent(new Point(400,300), JavaWars.getEngine().getPlayers().get(2)));

		//Projectile ptest = new Projectile(this, (Point)position.clone(), new Point(200,200));
	}
	
	
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
			
			//if the tower have projectiles : mooves them
			if( ! e.getProjectiles().isEmpty()) {
				
				Iterator<Projectile> itProj = e.getProjectiles().iterator();
				Projectile projectile;
				while(itProj.hasNext()){
					projectile = itProj.next();
					
					//Update the position of the projectile, delete it if arrived and remove lfie to agent
					if(projectile.updateProjectile()) {
						projectile.getAgent().addLife(- e.getStrength());
						itProj.remove();
					}
						
				}

				change = true;
			}
			
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
                //Si le joueur est le m�me que celui de la tour
                if(entry.getKey() == t.getPlayer().getPlayerNumber())
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
					
					//on ajoute un projectile
					//t.attackAgent(agent);
					
					
				} // end whilte agents
                
        }
        
        
        
        
		
	}
	
	
	
	
	
	
	
}
