package fr.imac.javawars.dispatcher;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Ground;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;
/**
 * 
 * @author Hugo
 *
 */
public class Dispatcher {
	

	private ConcurrentLinkedQueue<Integer> actionp1 = new ConcurrentLinkedQueue<Integer>();
	
	public Dispatcher() {
		
	}
		
	
	/**
	 * Remove money from given Player
	 * @param playerInfos
	 * 			The player you want to remove money
	 * @param money
	 * 			The amount of money
	 */
	public void removeSomeMoney(Player p, int money){
		actionp1.add(money);
	}
	/**
	 * Get the actions list (to perform, should be called on Engine)
	 * @return An integer list with action 
	 */
	public ConcurrentLinkedQueue<Integer> getActionP1(){
		return actionp1;
	}
	
	
	public void updatePlayers(){
		Iterator<Map.Entry<Integer, Player>> it = JavaWars.getEngine().getPlayers().entrySet().iterator();
		
		while (it.hasNext()) {
			  Map.Entry<Integer, Player> entry = it.next();
			  entry.getValue().update();
		}
	}
	
	public int[][] getGround(){
		int[][] map = JavaWars.getEngine().getGround().getBitMap();
	
		return map;
	}

}
