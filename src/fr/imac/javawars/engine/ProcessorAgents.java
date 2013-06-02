package fr.imac.javawars.engine;

import java.util.Iterator;
import java.util.Map;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.player.IA;
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
			//the current agent
			Agent a;
			//precedentAgent used to know the position of the agent which left before this current
			Agent precedentAgent = null;
			while(itAgent.hasNext()){
				a = itAgent.next();
				
				if(a == null){
					System.out.println("agent null");
					return false;
				}
				
				//check life of the agent
				if(! a.isInLife()) {
					System.out.println("remove this agent");
					itAgent.remove();
					continue;
				}
				
				if(precedentAgent != null){//if there is a precedent agent, we move the agent only if there is enough space between the agents
					int positionPrecedentAgentX = (int) precedentAgent.getPosition().getX();
					int positionAgentX = (int) a.getPosition().getX();
					int positionPrecedentAgentY = (int) precedentAgent.getPosition().getY();
					int positionAgentY = (int) a.getPosition().getY();
					
					//if the distance between agents is not enough the agent doesn't move
					if(! (Math.abs(positionPrecedentAgentX - positionAgentX) >= 10 || Math.abs(positionPrecedentAgentY - positionAgentY) >= 10))
						continue;//pass to the next element of the loop
				}
				
				//Update the position of the agent if precendent tests have been passed, delete it if he is arrived
				if(a.updatePosition()){ //a.updatePosition() makes the agent moved and returns true if the agent is arrived at destination
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
								
//								a.getBaseTarget().setSpeedRegeneration((int)0.04 * a.getBaseTarget().getRadius());
//								a.getBaseTarget().autoAddLife();
//								a.getBaseTarget().addLife(1);
							}
						}
						//else, it's an enemy base
						else {
							a.getBaseTarget().loseLife(1);
							

							//if the life of the target becomes zero, base becomes neutral
							if(a.getBaseTarget().getLife() == 0) {
								a.getBaseTarget().setPlayer(null);
							}
							
							// If the player is not an IA
							if(!(a.getBaseTarget().getPlayer() instanceof IA)) 
								continue;
							
							// If the player is an IA, process to defence his base
							IA tempIA = (IA)a.getBaseTarget().getPlayer();
								tempIA.sendIaAgent(a.getBaseTarget(),a.getBaseStart());
						
						}
					}
					//if the base belongs to the player
					else {
						a.getBaseTarget().addLife(1);
						JavaWars.getEngine().checkEndGame();
					}
				}//end if agent has moved
				precedentAgent = a;
			}// end while iterator on agents  
			change = true;
		} //end while iterator on players
		return change;
	}//end function process
	
	
}//end of the class
