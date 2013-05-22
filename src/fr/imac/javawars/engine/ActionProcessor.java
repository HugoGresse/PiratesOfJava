package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Action;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.dispatcher.ActionTowerUpgrade;

public class ActionProcessor {

	public ActionProcessor() {
	}
	
	/**
	 * process given Queue
	 * @param actions
	 * 					the only one action queue
	 */
	public boolean process(ConcurrentLinkedQueue<Action> actions){
		// remove i before production
		int i = 1;
		
		boolean change = false; 
		Iterator<Action> itr = actions.iterator();
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
			
			
			System.out.println("processAction : "+i);
			i++;
			
			change = true;
		}
		
		
		return change;
	}

	
	private void tryToAddTower(ActionTowerCreate action){
		
		double sizeTower = 7.5;
		
		Point newPoint = action.getTower().getPosition();
		Point point;
		
		//check if anough money : 
		if( action.getTower().getPrice() >  action.getPlayer().getMoney() ) {
			System.out.println("Pas assez d'argent pour créer la tour");
			// TODO check object null to destroy it
			return;
		}
		
		//CHeck if a tower is already on this place or too close
		Iterator<Tower> itr = JavaWars.getEngine().getTowers().iterator();
		while(itr.hasNext()){
			point = itr.next().getPosition();
			
			if(point.getX() + sizeTower > newPoint.getX() - sizeTower && point.getX() - sizeTower < newPoint.getX() + sizeTower){
				if(point.getY() + sizeTower > newPoint.getY() - sizeTower && point.getY() - sizeTower < newPoint.getY() + sizeTower){
					System.out.println("Impossible de créer la tour ici.");
					return;
				}
			}
			
		}
		itr = null;
		
		//check if the tower is on a fort
		for(int x = (int) (newPoint.getX()-sizeTower); x <= (int) (newPoint.getX() + sizeTower); x++){

			for(int y = (int) (newPoint.getY()-sizeTower); y <= (int) (newPoint.getY() + sizeTower); y++){
				
				if(JavaWars.getEngine().getGround().getGroundPosition(y, x) != -2) {
					System.out.println("Vous devez créer la tour sur un contrefort.");
					return;
				}
				
			
			}
		}
		
		//If all OK : 
		JavaWars.getEngine().addTower(action.getTower());
		action.getPlayer().changeMoney( - action.getTower().getPrice());
		
	}
	
	
	private void tryToUpgradeTower(ActionTowerUpgrade action){
		
		//check if anough money : 
		if( action.getPrice() >  action.getPlayer().getMoney() ) {
			System.out.println("Pas assez d'argent pour améliorer la tour");
			// TODO check object null to destroy it
			return;
		}
		
		if(action.getTowerUpgrade() == 1) action.getTower().changeActionField(2);
		else if( action.getTowerUpgrade() == 2) action.getTower().changeStrength(2);
		
		action.getPlayer().changeMoney( - action.getPrice());
		
		
		
	}
	
	
	
	
	
	
}
