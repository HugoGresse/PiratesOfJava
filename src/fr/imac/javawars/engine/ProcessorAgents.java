package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.Iterator;
import java.util.Map;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base.Power;
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
					//if the agent comes from a base which owns the multiplication power, we create 3 new agents with less life for one who died
					if(a.getPower() == Power.MULT){
						for(int i = -1; i <2; i++){
							//create an agent with 5 point of life 
							p.addAgent(new Agent(1, new Point((int)a.getPosition().getX()+ 10*i, (int)a.getPosition().getY() + 10*i), a.getPlayer(), 1, a.getBaseStart(), a.getBaseTarget(), Power.NORMAL));
						}
					}
					itAgent.remove();
					a = null;
					continue;
				}
				
				//if there is a precedent agent which goes to the same base target, we move the agent only if there is enough space between the agents
				if(precedentAgent != null && precedentAgent.getBaseTarget().equals(a.getBaseTarget())){
					int positionPrecedentAgentX = (int) precedentAgent.getPosition().getX();
					int positionAgentX = (int) a.getPosition().getX();
					int positionPrecedentAgentY = (int) precedentAgent.getPosition().getY();
					int positionAgentY = (int) a.getPosition().getY();
					
					//if the distance between agents is not enough the agent doesn't move
					if(! (Math.abs(positionPrecedentAgentX - positionAgentX) >= 10 || Math.abs(positionPrecedentAgentY - positionAgentY) >= 10))
						continue;//pass to the next element of the loop
				}
				
				//Update the position of the agent if precedent tests have been passed, delete it if he is arrived

				//if the agent is more fast (speed power), we call the function several times
				for(int i =0; i < a.getSpeed(); ++i){
					// we pass in the function updatePosition, warning to break at the end of the if to leave the for loop when the agent is arrived
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
								}
							}
							//else, it's an enemy base
							else {
								if(a.getBaseTarget().getLife() > 0)
									a.getBaseTarget().loseLife(1);
								
	
								//if the life of the target becomes zero, base becomes neutral
								if(a.getBaseTarget().getLife() <= 0){
									a.getBaseTarget().setPlayer(null);
									a.getBaseTarget().setLife(0);
								}
								
								
								// If the player is not an IA
								if(!(a.getBaseTarget().getPlayer() instanceof IA)) 
									continue;
								
								// If the player is an IA, process to defense his base
								IA tempIA = (IA)a.getBaseTarget().getPlayer();
									tempIA.sendIaAgent(a.getBaseTarget(),a.getBaseStart());
							
							}
						}
						//if the base belongs to the player
						else {
							a.getBaseTarget().addLife(1);
							JavaWars.getEngine().checkEndGame();
						}
						//warning, this break is essential to avoid to access the iterator which has been removed just before
						break;
					}//end if agent has moved
				}				
				precedentAgent = a;
			}// end while iterator on agents  
			change = true;
		} //end while iterator on players
		return change;
	}//end function process
}//end of the class
