package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.Iterator;
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
	
	public ProcessorTower(){
		
		
	};
	
	public void testHugo(){
		//TEST
		JavaWars.getEngine().getPlayers().get(2).getAgents().addLast(new Agent(new Point(100,100), JavaWars.getEngine().getPlayers().get(2)));

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
			
			//if the tower have projectiles : mooves them
			if( ! e.getProjectiles().isEmpty()) {
				
				Iterator<Projectile> itProj = e.getProjectiles().iterator();
				Projectile p;
				while(itProj.hasNext()){
					p = itProj.next();
					
					//Update the position of the projectile, delete it if arrived and remove lfie to agent
					if(p.updateProjectile()) {
						
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
	private void checkAgentInRange(Player p){
		
	}
	
}
