package fr.imac.javawars.dispatcher;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;
import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.player.BottomBar;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.Player;
/**
 * 
 * @author Hugo
 *
 */
public class Dispatcher {
	

	private ConcurrentLinkedQueue<Action> actions = new ConcurrentLinkedQueue<Action>();
	
	public Dispatcher() {
		
	}
		
	
	/**
	 * Add action 
	 * @param action
	 * 				The action to add
	 */
	public void addAction(Action action){
		
		actions.add(action);
	}
	
	/**
	 * Get the actions queue (to perform, should be called on Engine)
	 * @return An action linked queue with action 
	 */
	public ConcurrentLinkedQueue<Action> getAction(){
		return actions;
	}
	
	/**
	 * update player's infos
	 */
	public void updatePlayers(){
		Iterator<Map.Entry<Integer, Player>> it = JavaWars.getEngine().getPlayers().entrySet().iterator();
		
		while (it.hasNext()) {
			  Map.Entry<Integer, Player> entry = it.next();
			  entry.getValue().update();
		}
		
	}
	
	/**
	 * Getting the map for the initialization of the game
	 */
	public int[][] getGround(){
		return JavaWars.getEngine().getGround().getBitMap();
	}
	
	/**
	 * Getting bases of the game
	 */
	public CopyOnWriteArrayList<Base> getBases(){
		return JavaWars.getEngine().getBases();
	}
	
	/**
	 * Getting towers of the game
	 */
	public CopyOnWriteArrayList<Tower> getTowers(){
		return JavaWars.getEngine().getTowers();
	}
	
	public String getError(){
		return JavaWars.getEngine().getError();
	}

}
