package fr.imac.javawars.engine;

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
	public boolean process(CopyOnWriteArrayList<Tower> towers){
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
		}
		return change;
	}
	
	/**
	 * Check if there are any agents in the actionField of the tower
	 */
	private void checkAgentInRangeAndAttack(Tower t){
		itPlayer = JavaWars.getEngine().getPlayers().entrySet().iterator();
		

		if(t instanceof TowerPoison || t instanceof TowerFreeze)
			return;
		
        while (itPlayer.hasNext()) {
                entry = itPlayer.next();
                //if the player is the same as the tower
                if(entry.getKey() == t.getPlayer().getPlayerNumber())
                	continue;
                
                //run trough agents
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
					
					
				} // end while agents  
        }
	}
	
	/**
	 * Process projectiles (move it, delete it, etc)
	 * @param tower
	 * 				the tower who should have projectiles
	 */
	private void processProjectile(Tower tower){
		// ======= PROJECTILE
		//if the tower have projectiles : moves them
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
			projectile.impactAgentLife();

			if (!projectile.getAgent().isInLife() && !projectile.getAgent().isDead()) {
				tower.getPlayer().addMoney(1);
				projectile.getAgent().setDead(true);
			}
			
			//process end effect on projectil, if true, delete it
			if(processProjectile(tower, projectile)){
				projectile = null;
				itProj.remove();	
			}
			
			
			
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
	private boolean processProjectile(Tower tower, Projectile projectile){
		if(tower instanceof TowerBombe){
			return ((TowerBombe)tower).processProjectileArrived(tower, projectile);
		} else if (tower instanceof TowerBounce){
			return ((TowerBounce)tower).processProjectileArrived(tower, projectile);
		}
		
		return true;
	}
	
}
