package fr.imac.javawars.dispatcher;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.engine.PlayerInfos;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;
/**
 * 
 * @author Hugo
 *
 */
public class Dispatcher {
	

	private Map<Integer, Player> players;
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	private ConcurrentLinkedQueue<Integer> actionp1 = new ConcurrentLinkedQueue<Integer>();
	
	public Dispatcher(Player p1, Player p2, Player p3, Player p4) {
		players = new Hashtable<Integer, Player>();
		
		// add the players in a map 
		// TODO replace map by an arraylist
		players.put(p1.getPlayerNumber(), p1);
		players.put(p2.getPlayerNumber(), p2);
		players.put(p3.getPlayerNumber(), p3);
		players.put(p4.getPlayerNumber(), p4);
		
		
		//start treads for IA and save it
		Iterator<Map.Entry<Integer, Player>> it = players.entrySet().iterator();
		while (it.hasNext()) {
			  Map.Entry<Integer, Player> entry = it.next();
			  if (entry.getValue() instanceof IA ) 
				  threads.add(new Thread(  (IA)entry.getValue() ));
		}
		
		//Start threads
		for(Thread t : threads)
			t.start();
		
	}
	
	
	/**
	 * Get the list of players
	 * @return a map/hashtable with the playerNumber and the Player
	 */
	public Map<Integer, Player> getPlayers(){
		return players;
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
	
	
	public void updateIHM(){
		
	}
	
	
}
