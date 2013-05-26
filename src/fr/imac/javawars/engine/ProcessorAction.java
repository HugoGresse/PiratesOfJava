package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Action;
import fr.imac.javawars.dispatcher.ActionAgentSend;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.dispatcher.ActionTowerUpgrade;

/**
 * The class which are in charge of processing Actions
 * 
 * @author Hugo
 *
 */
public class ProcessorAction {

	public ProcessorAction() {
	}
	
	/**
	 * process given Queue. Add method related to a specific action here
	 * @param actions
	 * 					the only one action queue
	 */
	public boolean process(ConcurrentLinkedQueue<Action> actions){
		// remove i before production
		int i = 1;
		Iterator<Action> itr = actions.iterator();
		boolean change = false;
		while(itr.hasNext()){
			Object e = itr.next();
			
			if(e instanceof ActionTowerUpgrade){
				this.tryToUpgradeTower((ActionTowerUpgrade)e);
				actions.poll();
			} 
			else if(e instanceof ActionTowerCreate){
				this.tryToAddTower((ActionTowerCreate)e);
				actions.poll();
			}
			else if(e instanceof ActionAgentSend){
				this.createAgent((ActionAgentSend)e);
				//this.sendAgent((ActionAgentSend)e);
				actions.poll();
			}
			
			change = true;
			//System.out.println("processAction : "+i);
			i++;
		}
		return change;
	}

	/**
	 * Create a new agent sent from a base to another (see ProcessorAgents)
	 * @param e
	 * 			The action we need to treat
	 */
	private void createAgent(ActionAgentSend e){
		//creation of the agent, it's in the agentsProcessor that his displacement is managed
		e.getPlayer().addAgent(new Agent(100, new Point(e.getBaseStart().getPosition()), e.getPlayer(), 1, e.getBaseStart(), e.getBaseTarget()));
		//the base which sends an agent loses a point of life (an agent)
		e.getBaseStart().loseLife(1);
	}


	/**
	 * Check if a player can crate a tower related to :
	 * 	- the position (on contrefort and not too near other Tower)
	 * 	- the player's money
	 * And if ok, add the tower and change player money
	 * @param action
	 * 				A ActionTowerCreate action
	 */
	private void tryToAddTower(ActionTowerCreate action){
		
		double sizeTower = 7.5;
		
		Point newPoint = action.getTower().getPosition();
		Point point;
		
		//check if player has enough money : 
		if( action.getTower().getPrice() >  action.getPlayer().getMoney() ) {
			if(action.getPlayer().getPlayerNumber() == 1)
				JavaWars.getEngine().setError("Pas assez d'argent pour créer la tour");
			// TODO check object null to destroy it
			return;
		}
		
		//CHeck if a tower is already on this place or too close
		Iterator<Tower> itr = JavaWars.getEngine().getTowers().iterator();
		while(itr.hasNext()){
			point = itr.next().getPosition();
			
			if(point.getX() + sizeTower > newPoint.getX() - sizeTower && point.getX() - sizeTower < newPoint.getX() + sizeTower){
				if(point.getY() + sizeTower > newPoint.getY() - sizeTower && point.getY() - sizeTower < newPoint.getY() + sizeTower){
					if(action.getPlayer().getPlayerNumber() == 1)
						JavaWars.getEngine().setError("Impossible de créer la tour ici.");
					return;
				}
			}
			
		}
		itr = null;
		
		//check if the tower is on a fort AND on his influence area
		for(int x = (int) (newPoint.getX()-sizeTower); x <= (int) (newPoint.getX() + sizeTower); x++){

			for(int y = (int) (newPoint.getY()-sizeTower); y <= (int) (newPoint.getY() + sizeTower); y++){
				if(JavaWars.getEngine().getGround().getGroundPosition(y, x) != -2 || JavaWars.getEngine().getBasesManager().getPositionInfluenceArea(y, x) != 1) {
					if(action.getPlayer().getPlayerNumber() == 1)
						JavaWars.getEngine().setError("Vous devez créer la tour sur un contrefort autour de votre base principale");
					return;
				}
				
			
			}
		}
		
		//If all OK : 
		JavaWars.getEngine().addTower(action.getTower());
		action.getPlayer().changeMoney( - action.getTower().getPrice());
		
	}
	
	/**
	 * Verify if the player can upgrade a tower if : 
	 * 	- player have anough money
	 * If ok : upgrade the tower (range/ActionField and strengh/damage) and change tower money
	 * @param action
	 */
	private void tryToUpgradeTower(ActionTowerUpgrade action){
		
		//check if player has enough money : 
		if( action.getPrice() >  action.getPlayer().getMoney() ) {
			if(action.getPlayer().getPlayerNumber() == 1)
				JavaWars.getEngine().setError("Pas assez d'argent pour améliorer la tour");
			// TODO check object null to destroy it
			return;
		}
		
		if(action.getTowerUpgrade() == 1) action.getTower().changeActionField(20);
		else if( action.getTowerUpgrade() == 2) action.getTower().changeStrength(1);
		
		action.getPlayer().changeMoney( - action.getPrice());
			
	}
	
	
	
	/*private void sendAgent(ActionAgentSend e) {
		if(e.getAgent() == null){
			System.out.println("agent null");
			return;
		}
		else{
			//the base of start loses an agent
			e.getBaseStart().loseLife(1);
			//this agent is sent to the target base
			e.getAgent().sendToBase(e.getBaseTarget());
			//Now we manage lifes of bases in consequence of the agent displacement
			// if the target base doesn't belong to the player
			if(e.getBaseTarget().getPlayer() != e.getBaseStart().getPlayer()){
				//if the base is neutral, the player takes it when his agent arrived
				if(e.getBaseTarget().getPlayer() == null){
					//if the base has more life than 0 we have to decrease it
					if(e.getBaseTarget().getLife() > 0){
						e.getBaseTarget().loseLife(1);
					}
					else{
						//the base belongs now to the player of the starting base
						e.getBaseTarget().setPlayer(e.getBaseStart().getPlayer());
						e.getBaseTarget().addLife(1);
					}
				}
				//else, it's an enemy base
				else {
					e.getBaseTarget().loseLife(1);
					//if the life of the target becomes zero, base becomes neutral
					if(e.getBaseTarget().getLife() == 0) {
						e.getBaseTarget().setPlayer(null);
					}
				}
			}
			else {
				e.getBaseTarget().addLife(1);
			}
		}
		//once the agent is arrived to destination, we delete it (he exists on the base)
		e.getBaseStart().getPlayer().deleteAgent(e.getAgent());
	}*/
	
}
