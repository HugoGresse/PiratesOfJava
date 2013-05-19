package fr.imac.javawars.engine;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Action;
import fr.imac.javawars.dispatcher.ActionTowerCreate;

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
			
			if(e instanceof ActionTowerCreate){
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
		//check if anough money : 
		if( action.getTower().getPrice() <  action.getPlayer().getMoney() ) {
			System.out.println("Pas assez d'argent pour créer la tour");
			return;
		}
		
		JavaWars.getEngine().addTower(action.getTower());
		
		
		
	}
	
	
	
}
