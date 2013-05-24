package fr.imac.javawars.engine;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;
/*
	 * Variables
	 * - Map
	 * - Distances de d�placement
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
	
	//Processor
	private static ProcessorAction actionProcessor;
	private static ProcessorTower towerProcessor;
	
	//Game data, replace by a class 
	private Map<Integer, Player> playersData;
	private ArrayList<Thread> threadsPlayers = new ArrayList<Thread>();
	Iterator<Map.Entry<Integer, Player>> it;
	Map.Entry<Integer, Player> entry;
	
	// collections :
	private CopyOnWriteArrayList<Base> bases;
	private CopyOnWriteArrayList<Tower> towers;
	
	//test arthur
	private BasesManager basesManager;
	private Ground ground;
	
	//stock erreurs � envoyer � l'IHM
	private String error = null;
	
	/* CONSTRUCTOR */
	public Engine() {
	
		actionProcessor = new ProcessorAction();
		towerProcessor = new ProcessorTower();
		
		//init engine thread, which is started in the initialisation of the game
		engineThread = new Thread(this);
		
	}
	
	/* GETTERS // SETTERS */
	public BasesManager getBasesManager() {
		return basesManager;
	}

	public void setBasesManager(BasesManager basesManager) {
		this.basesManager = basesManager;
	}

	public CopyOnWriteArrayList<Base> getBases() {
		return bases;
	}

	public void setBases(CopyOnWriteArrayList<Base> bases) {
		this.bases = bases;
	}
	
	public CopyOnWriteArrayList<Tower> getTowers() {
		return towers;
	}

	public void setTowers(CopyOnWriteArrayList<Tower> towers) {
		this.towers = towers;
	}
	
	public void addTower(Tower t){
		this.towers.add(t);
	}

	public Ground getGround() {
		return ground;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}
	
	public void stopThread(){
		running = false;
	}
	
	public void setError(String error){
		this.error = error;
	}
	
	public String getError(){
		return error;
	}
	
	/**
	 * Get the list of players
	 * @return a map/hashtable with the playerNumber and the Player
	 */
	public Map<Integer, Player> getPlayers(){
		return playersData;
	}
	
	
	@Override
	public void run() {
		
		//check if the something change
		boolean playerChange;
		boolean ihmChange;
		
		while(running){
			try {
				//every 29ms minimum, we get actions from dispatcher and try to execute it
				
				playerChange = ihmChange = true;
				playerChange =  actionProcessor.process(dispatcher.getAction());
				
				ihmChange = towerProcessor.process(towers);
				
				
				//when the action is processed, updatePlayers trough dispatcher
				if(playerChange)
					dispatcher.updatePlayers();
				
				//When something change on ihm : 
				if(ihmChange)
					dispatcher.repaintIhm();
				
				Thread.sleep(29);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 *	Initialize the game, the ground, towers and bases
	 */
	public void initializationOfTheGame(Player p1, Player p2, Player p3, Player p4){
		
		initializationOfPlayers(p1,p2,p3,p4);
		

		/*initialisation of the ground*/
		this.ground = new Ground("map/mapCool_2.xml");
		//this.ground.printGround();
		
		// compute the map of distance for every map
		Iterator<Base> itBases = this.bases.iterator();
		while(itBases.hasNext()){
			Base b = itBases.next();
			b.initializeDistanceMap(this.ground.getBitMap());
			b.computeDistanceMap(this.ground.getBitMap());
		}
		
		//initialisation of and towers
		towers = new CopyOnWriteArrayList<Tower>();
		
		

		// on stocke le dispatcher histoire de ne pas le rapeller tout le temps
		dispatcher = JavaWars.getDispatcher();
		
	}
	
	/**
	 * Initialize the player to save it and to start thread
	 * Should be call in JavaWars after the dispatcher init
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	public void initializationOfPlayers(Player p1, Player p2, Player p3, Player p4){
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
	 * Start the engine thread
	 * @see JavaWars
	 */
	public void startThread(){
		//On d�marre ENgine
		running = true;
		engineThread.start();
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
	
}
