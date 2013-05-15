package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Action;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;
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

public class Engine  implements Runnable{
	
	protected volatile boolean running = false;
	protected Thread engineThread;
	
	protected Dispatcher dispatcher;
	protected static PlayerEngine playerEngine;
	
	//Game data, replace by a class 
	private Map<Integer, Player> playersData;
	private ArrayList<Thread> threadsPlayers = new ArrayList<Thread>();
	Iterator<Map.Entry<Integer, Player>> it;
	Map.Entry<Integer, Player> entry;
	
	
	//test arthur
	private BasesManager basesManager;
	private ArrayList<Base> bases;
	private ArrayList<Player> players;
	private Ground ground;
	
	
	/* CONSTRUCTOR */
	public Engine() {
		// on stocke le dispatcher histoire de ne pas le rapeller tout le temps
		dispatcher = JavaWars.getDispatcher();
		
		
		playerEngine = new PlayerEngine();
		engineThread = new Thread(this);
		engineThread.start();

		
		initializationOfTheGame();
	}
	
	/* GETTERS // SETTERS */
	public BasesManager getBasesManager() {
		return basesManager;
	}

	public void setBasesManager(BasesManager basesManager) {
		this.basesManager = basesManager;
	}

	public ArrayList<Base> getBases() {
		return bases;
	}

	public void setBases(ArrayList<Base> bases) {
		this.bases = bases;
	}

	public Ground getGround() {
		return ground;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
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
				it = playersData.entrySet().iterator();
				while (it.hasNext()) {
					entry = it.next();
					//pour chaque player : 
					switch (entry.getValue().getPlayerNumber()) {
						case 1:
							dataChange = processAction(entry.getValue(), dispatcher.getAction());
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
	 *	Initialize the game, the ground
	 */
	public void initializationOfTheGame(){

		/*initialisation of the ground*/
		this.ground = new Ground();
		ground.printGround();
	}
	
	
	public void initializationOfPlayers(Player p1, Player p2, Player p3, Player p4){
		running = true;
		playersData = new Hashtable<Integer, Player>();
		
		// add the players in a map 
		// TODO replace map by an arraylist
		playersData.put(p1.getPlayerNumber(), p1);
		playersData.put(p2.getPlayerNumber(), p2);
		playersData.put(p3.getPlayerNumber(), p3);
		playersData.put(p4.getPlayerNumber(), p4);
		
		
		//start treads for IA and save it
		Iterator<Map.Entry<Integer, Player>> itTemp = playersData.entrySet().iterator();
		while (itTemp.hasNext()) {
			  Map.Entry<Integer, Player> entry = itTemp.next();
			  if (entry.getValue() instanceof IA ) 
				  threadsPlayers.add(new Thread(  (IA)entry.getValue() ));
		}
		
		//Start threads
		for(Thread t : threadsPlayers)
			t.start();
		
	}
	
	/**
	 * process given Queue for the given player
	 * @param p
	 * 			the player to proccess action on
	 * @param actions
	 */
	private boolean processAction(Player p, ConcurrentLinkedQueue<Action> actions){
		
		boolean change = false; 
		Iterator<Action> itr = actions.iterator();
		while(itr.hasNext()){
			
			if(itr.next() instanceof ActionTowerCreate){
				playerEngine.createTower(itr.next());
			}
			
			actions.poll();
			
			System.out.println(p.getMoney());
			
			change = true;
		}
		
		itr = null;
		return change;
	}
	
	
	/*TEST ARTHUR*/
	private void testArthur(){
		
		/*creation of the players*/
        //int nbPlayers = ground.getNumberOfPlayers();
        //System.out.println("number of players : " + nbPlayers);
        //creation of a list of players
        //this.players = new ArrayList<Player>();
        //we have a human player
        //Player player1 = new Human(1, "Player1");
        //adding the human player to our arraylist
        //this.players.add(player1);
        //others are IA
       // for(int i = 0; i < nbPlayers - 1 ; ++i){
                //String nameIA = "IA" + i;
                //adding IAs to our arrayList
                //this.players.add(new IA(i + 2, nameIA ));
        //}
        //System.out.println("size of array of players : " + players.size());
        /*creation of bases belonging to the players */
        //this.bases = new ArrayList<Base>();
        //Base base1 = new Base(new Point(50, 40), players.get(0));
        //this.bases.add(base1);
        //Base base2 = new Base(new Point(80, 50), players.get(1));
        //this.bases.add(base2);
        //Base base3 = new Base(new Point(60, 5), players.get(2));
       // this.bases.add(base3);
        //System.out.println("size of array of bases : " + bases.size());
        // test distanceMap
        //this.bases.get(1).initializeDistanceMap(ground.getBitMap());
        //this.bases.get(1).computeDistanceMap(ground.getBitMap());
        
        //initialisation of BasesManager with the list of the bases,
        // test influence Area of bases
        //this.basesManager = new BasesManager(this.bases, ground.getBitMap());
	}
	
	
	

	/**
	 * Get the list of players
	 * @return a map/hashtable with the playerNumber and the Player
	 */
	public Map<Integer, Player> getPlayers(){
		return playersData;
	}
}
