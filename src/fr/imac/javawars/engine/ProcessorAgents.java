package fr.imac.javawars.engine;

import java.util.Iterator;
import java.util.Map;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.player.Player;



/**
 * This class process tower, and so projectiles
 * 
 * @author Hugo
 *
 */
public class ProcessorAgents {
	
	public ProcessorAgents(){ };
	
	/**
	 * Process the given collections for move agents 
	 * Call in run() of Engine
	 * 
	 * @return
	 * 			return true if position changes for any agent
	 */
	public boolean process(Map<Integer, Player> players){
		boolean change = false;
		//for all agents
		
		Iterator<Map.Entry<Integer, Player>> itr = players.entrySet().iterator();
	
		while(itr.hasNext()){
			Player p = itr.next().getValue();
			
			//if the player has agents : move them
			if( p.getAgents().isEmpty())
				continue;
				
			Iterator<Agent> itAgent = p.getAgents().iterator();
			Agent a;
			while(itAgent.hasNext()){
				a = itAgent.next();
				
				if(a == null){
					System.out.println("agent null");
					return false;
				}
				
				//check life of the agent
				if(! a.isInLife()) {
					itAgent.remove();
					a = null;
					continue;
				}
					
				
				
				//
				//Update the position of the agent, delete if he is arrived
				if(a.updatePosition()){
					// We enter in this loop just when the agent is arrived to destination
					
					//once the agent is arrived to destination, we delete it
					itAgent.remove();
					p.setNumberOfAgents(p.getNumberOfAgents()-1);
					
					//we manage lifes of target base in function of his type
					
					// if the target base doesn't belong to the player
					if(a.getBaseTarget().getPlayer() != a.getBaseStart().getPlayer()){
						//if the base is neutral
						if(a.getBaseTarget().getPlayer() == null){
							//if the base has more life than 0 we have to decrease it
							if(a.getBaseTarget().getLife() > 0){
								a.getBaseTarget().loseLife(1);
							}
							else{
								//the base belongs now to the player of the starting base
								a.getBaseTarget().setPlayer(a.getBaseStart().getPlayer());
								JavaWars.getEngine().checkEndGame();
								
								/*a.getBaseTarget().setSpeedRegeneration((int)0.04 * a.getBaseTarget().getRadius());
								a.getBaseTarget().autoAddLife();
								a.getBaseTarget().addLife(1);*/
							}
						}
						//else, it's an enemy base
						else {
							a.getBaseTarget().loseLife(1);
							//if the life of the target becomes zero, base becomes neutral
							if(a.getBaseTarget().getLife() == 0) {
								a.getBaseTarget().setPlayer(null);
							}
						}
					}
					//if the base belongs to the player
					else {
						a.getBaseTarget().addLife(1);
						JavaWars.getEngine().checkEndGame();
					}
					
				}
			}
			change = true;
		} //end while
		
		return change;
	}
	

}
