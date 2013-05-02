package fr.imac.javawars.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

	/*
	 * Variables
	 * - Map
	 * - Distances de dŽplacement
	 * - Liste des tours
	 * - Liste des bases
	 * - Liste des joueurs
	 * 
	 * Class dans "Engine"
	 * - Tours/Bases/Agents
	 * - Information du joueur
	 * 
	 */

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;

public class Engine  implements Runnable{
	
	protected volatile boolean running = true;
	protected Thread engineThread;
	
	protected Dispatcher dispatcher;
	protected static PlayerEngine playerEngine;
	
	Iterator<Map.Entry<Integer, Player>> it;
	Map.Entry<Integer, Player> entry;
	
	//test arthur
	private BasesManager basesManager;
	private ArrayList<Base> bases;
	private ArrayList<Player> players;
	private Ground ground;
	
	public Engine() {
		dispatcher = JavaWars.getDispatcher();
		playerEngine = new PlayerEngine();
		engineThread = new Thread(this);
		engineThread.start();

		//on met à jour les Players
		dispatcher.updatePlayers();
		
		testArthur();
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
	 *	Initialize the game
	 */
	public void initializationOfTheGame(){

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
	
	
	/*TEST ARTHUR*/
	private void testArthur(){
		/*initialisation of the ground*/
		this.ground = new Ground("map/mapTest_3.gif");
		/*creation of the players*/
		int nbPlayers = ground.getNumberOfPlayers();
		System.out.println("number of players : " + nbPlayers);
		//creation of a list of players
		this.players = new ArrayList<Player>();
		//we have a human player
		Player player1 = new Human(1, "Player1");
		//adding the human player to our arraylist
		this.players.add(player1);
		//others are IA
		for(int i = 0; i < nbPlayers - 1 ; ++i){
			String nameIA = "IA" + i;
			//adding IAs to our arrayList
			this.players.add(new IA(i + 2, nameIA ));
		}
		System.out.println("size of array of players : " + players.size());
		/*creation of bases belonging to the players */
		this.bases = new ArrayList<Base>();
		Base base1 = new Base(new Point(50, 40), players.get(0));
		this.bases.add(base1);
		Base base2 = new Base(new Point(80, 50), players.get(1));
		this.bases.add(base2);
		Base base3 = new Base(new Point(60, 5), players.get(2));
		this.bases.add(base3);
		System.out.println("size of array of bases : " + bases.size());
		// test distanceMap
		this.bases.get(1).initializeDistanceMap(ground.getBitMap());
		this.bases.get(1).computeDistanceMap(ground.getBitMap());
		
		//initialisation of BasesManager with the list of the bases,
		// test influence Area of bases
		this.basesManager = new BasesManager(this.bases, ground.getBitMap());
	}
}
