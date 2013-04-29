package fr.imac.javawars.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;

public class Engine  implements Runnable{
	
	protected volatile boolean running = true;
	protected Thread engineThread;
	protected Dispatcher dispatcher;
	
	public Engine() {
		dispatcher = JavaWars.getDispatcher();
		engineThread = new Thread(this);
		engineThread.start();
	}
	
	public void stopThread(){
		running = false;
	}
	
	@Override
	public void run() {
		while(running){
			try {
				//every 29ms minimum, we get actions from dispatcher and try to execute it
				
				
				// iterate on players
				Iterator<Map.Entry<Integer, Player>> it = dispatcher.getPlayers().entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Integer, Player> entry = it.next();
					//pour chaque player : 
					switch (entry.getValue().getPlayerNumber()) {
						case 1:
							processAction(entry.getValue(), dispatcher.getActionP1());
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
				}
				
				
				//System.out.println(" - Eng - ");
				
				
				//une fois les PlayerInfos modif, on les renvoie au dispatcher !
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
	private void processAction(Player p, ConcurrentLinkedQueue<Integer> actions){

		
		Iterator<Integer> itr = actions.iterator();
		while(itr.hasNext()){
			
			p.getpInfos().reduceMoney(itr.next());
			actions.poll();
			
			System.out.println(p.getpInfos().getMoney());
			
		}
	}
	

}
