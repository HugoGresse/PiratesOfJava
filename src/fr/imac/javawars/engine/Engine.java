package fr.imac.javawars.engine;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.player.Player;

public class Engine  implements Runnable{
	
	protected volatile boolean running = true;
	protected Thread engineThread;
	
	protected Dispatcher dispatcher;
	protected static PlayerEngine playerEngine;
	
	Iterator<Map.Entry<Integer, Player>> it;
	Map.Entry<Integer, Player> entry;
	
	public Engine() {
		dispatcher = JavaWars.getDispatcher();
		playerEngine = new PlayerEngine();
		engineThread = new Thread(this);
		engineThread.start();
		
		//on met à jour les Players
		dispatcher.updatePlayers();
	}
	
	public void stopThread(){
		running = false;
	}
	
	@Override
	public void run() {
		while(running){
			try {
				//every 29ms minimum, we get actions from dispatcher and try to execute it
				
				//check if the something change
				boolean dataChange = false;
				
				// iterate on players
				// 
				it = dispatcher.getPlayers().entrySet().iterator();
				while (it.hasNext()) {
					entry = it.next();
					//pour chaque player : 
					switch (entry.getValue().getPlayerNumber()) {
						case 1:
							dataChange = processAction(entry.getValue(), dispatcher.getActionP1());
							break;
						case 2:
							
							break;
						case 3:
							
							break;
						case 4:
							
							break;
						default:
							break;
						}

					entry = null;
				}
				
				it = null;
				entry = null;
				
				//une fois les PlayerInfos modif, on les renvoie au dispatcher !
				// seulement si les données on changé
				if(dataChange)
					dispatcher.updatePlayers();
				
				
				Thread.sleep(29);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * process given Queue for the given player
	 * @param p
	 * 			the player to proccess action on
	 * @param actions
	 */
	private boolean processAction(Player p, ConcurrentLinkedQueue<Integer> actions){
		
		boolean change = false; 
		Iterator<Integer> itr = actions.iterator();
		while(itr.hasNext()){
			
			playerEngine.changePlayerMoney(p, itr.next());
			actions.poll();
			
			System.out.println(p.getMoney());
			
			change = true;
		}
		
		itr = null;
		return change;
	}
	

}
