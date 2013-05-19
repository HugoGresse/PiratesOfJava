package fr.imac.javawars.engine;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Action;
import fr.imac.javawars.dispatcher.ActionTowerCreate;

public class PlayerEngine {

	public PlayerEngine() {
		// TODO Auto-generated constructor stub
	}

	public void tryToAddTower(ActionTowerCreate action){
		System.out.println("addTowerToPlayer");
		//check if anough money : 
		if( action.getTower().getPrice() <  action.getPlayer().getMoney() ) {
			System.out.println("Pas assez d'argent pour créer la tour");
			return;
		}
		
		JavaWars.getEngine().addTower(action.getTower());
		
		
		
	}
	
	
	
	
}
