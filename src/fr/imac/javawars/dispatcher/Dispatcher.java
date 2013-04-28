package fr.imac.javawars.dispatcher;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;

public class Dispatcher {
	

	private Map<Integer, Player> players;
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public Dispatcher(Player p1, Player p2, Player p3, Player p4) {
		players = new Hashtable<Integer, Player>();
		
		players.put(new Integer(1), p1);
		players.put(new Integer(2), p2);
		players.put(new Integer(3), p3);
		players.put(new Integer(4), p4);
		
		
		//start treads for IA
		Iterator<Map.Entry<Integer, Player>> it = players.entrySet().iterator();
		while (it.hasNext()) {
			  Map.Entry<Integer, Player> entry = it.next();
			  
			  if (entry.getValue() instanceof IA ) {
				  threads.add(new Thread(  (IA)entry.getValue() ));
			  }
		}
		for(Thread t : threads)
			t.start();
		
	}


}
