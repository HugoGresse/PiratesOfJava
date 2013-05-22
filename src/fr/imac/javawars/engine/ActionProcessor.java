package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Action;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.dispatcher.ActionTowerUpgrade;

/**
 * The class which are in charge of processing Actions
 * 
 * @author Hugo
 *
 */
public class ActionProcessor {

	public ActionProcessor() {
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
			
			change = true;
			System.out.println("processAction : "+i);
			i++;
		}
		return change;
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
		
		//check if anough money : 
		if( action.getTower().getPrice() >  action.getPlayer().getMoney() ) {
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
					JavaWars.getEngine().setError("Impossible de créer la tour ici.");
					return;
				}
			}
			
		}
		itr = null;
		
		//check if the tower is on a fort
		for(int x = (int) (newPoint.getX()-sizeTower); x <= (int) (newPoint.getX() + sizeTower); x++){

			for(int y = (int) (newPoint.getY()-sizeTower); y <= (int) (newPoint.getY() + sizeTower); y++){
				
				if(JavaWars.getEngine().getGround().getGroundPosition(y, x) != -2) {
					JavaWars.getEngine().setError("Vous devez créer la tour sur un contrefort.");
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
		
		//check if anough money : 
		if( action.getPrice() >  action.getPlayer().getMoney() ) {
			JavaWars.getEngine().setError("Pas assez d'argent pour améliorer la tour");
			// TODO check object null to destroy it
			return;
		}
		
		if(action.getTowerUpgrade() == 1) action.getTower().changeActionField(2);
		else if( action.getTowerUpgrade() == 2) action.getTower().changeStrength(2);
		
		action.getPlayer().changeMoney( - action.getPrice());
			
	}	
	
}
