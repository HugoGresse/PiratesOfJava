package fr.imac.javawars.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;
/**
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
	private volatile boolean threadSuspended = false;
	protected Thread engineThread;
	private long fpsTarget = 1000/30;
	
	protected Dispatcher dispatcher;
	
	//Processors
	private static ProcessorAction actionProcessor;
	private static ProcessorTower towerProcessor;
	private static ProcessorAgents agentsProcessor;
	private static ProcessorBase baseProcessor;
	
	//Game data, replace by a class 
	private Map<Integer, Player> playersData;
	private ArrayList<Thread> threadsPlayers = new ArrayList<Thread>();
	Iterator<Map.Entry<Integer, Player>> it;
	Map.Entry<Integer, Player> entry;
	
	// collections :
	private CopyOnWriteArrayList<Base> bases;
	private CopyOnWriteArrayList<Tower> towers;
	
	private BasesManager basesManager;
	private Ground ground;
	
	//stock error to send to ihm
	private String error = null;
	
	/**
	 * Constructor
	 */
	public Engine() {
	
		actionProcessor = new ProcessorAction();
		towerProcessor = new ProcessorTower();
		agentsProcessor = new ProcessorAgents();
		baseProcessor = new ProcessorBase();
		
		//init engine thread, which is started in the initialisation of the game
		engineThread = new Thread(this);
	}
	
	/**
	 * Getters/setters
	 */
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
	
	public void setError(String error){
		this.error = error;
	}
	
	public String getError(){
		return error;
	}
	
	public void stopThread(){
		running = false;
	}
	
	public void setFpsTarget(long num){
		fpsTarget = num;
	}
	
	/**
	 * Get the list of players
	 * @return a map/hashtable with the playerNumber and the Player
	 */
	public Map<Integer, Player> getPlayers(){
		return playersData;
	}
	
	/**
	 * remove a tower
	 * @param tower to remove
	 */
	public void removeTower(Tower t){
		Iterator<Tower> it = towers.iterator();
		while(it.hasNext()){
			Tower tower = it.next();
			
			if(t.equals(tower)){
				towers.remove(tower);
			}
		}
	}
	
	
	/**
	 * Run method of the thread
	 */
	@Override
	public void run() {
		
		//check if something change
		boolean playerChange;
		boolean towerChange;
		boolean agentChange;
		boolean baseChange;
		
		//TIME and FPS STUF
		int sleepTime;
		long beginTime;
		long endTime;
		final long FPS_TARGET = 1000/30;
		
		while(running){
			try {
				beginTime = System.currentTimeMillis();
				
				//every 29ms minimum, we get actions from dispatcher and try to execute it
				
				playerChange = towerChange = agentChange = true;

				playerChange =  actionProcessor.process(dispatcher.getAction());
				
				towerChange = towerProcessor.process(towers);
				
				agentChange = agentsProcessor.process(playersData);
				
				baseChange = baseProcessor.process(bases, System.currentTimeMillis());
								
				if(towerChange || playerChange || baseChange) {
					dispatcher.repaintBases();
					dispatcher.repaintTowers();
				}
				if(agentChange) dispatcher.repaintAgents();
				
				//if any data change
				if(playerChange || agentChange || towerChange || baseChange)
					dispatcher.updatePlayers();
				
				endTime = System.currentTimeMillis() - beginTime;
				//display fps if too bad
				if(endTime > FPS_TARGET) System.out.println("fps (ms) : "+ endTime);
				sleepTime = (int) (FPS_TARGET - endTime);
				
				Thread.sleep(sleepTime<0 ? 0 : sleepTime);
				
				//if game is in pause
				if (threadSuspended) {
                    synchronized(this) {
                        while (threadSuspended)
                            wait();
                    }
                }
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	
	/**
	 *	Initialize the game, the ground, towers and bases
	 */
	public void initializationOfTheGame(Player p1, Player p2, Player p3, Player p4,boolean random, File file){
		initializationOfPlayers(p1,p2,p3,p4);

		//initialization of the ground
		if(random)
			this.ground = new Ground();
		else
			this.ground = new Ground(file);
		
		// compute the map of distance for every map
		Iterator<Base> itBases = this.bases.iterator();
		while(itBases.hasNext()){
			Base b = itBases.next();
			b.initializeDistanceMap(this.ground.getBitMap());
			b.computeDistanceMap(this.ground.getBitMap());
		}
		
		//compute the influence area of bases
		this.basesManager = new BasesManager(this.ground.getBitMap());
		this.basesManager.createInfluenceList();
		
		//initialization of and towers
		towers = new CopyOnWriteArrayList<Tower>();
		
	
		//getGround().saveAsXML(this.basesManager.getInfluenceAreaBitMap(), "map/test");

		// Stock dispatcher to avoid to call it every time
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
	}
	
	/**
	 * Start the engine thread
	 * @see JavaWars
	 */
	public void startThread(){
		//On démarre ENgine
		running = true;
		engineThread.start();
	}
	
	/**
	 * Start Thread of IA
	 */
	public void startThreadIA(){
		//Start threads
		for(Thread t : threadsPlayers)
			t.start();
	}
	
	/**
	 * Pausing the game
	 */
	public synchronized void pausingGame(){
		threadSuspended = true;
		
		Iterator<Map.Entry<Integer, Player>> it = playersData.entrySet().iterator();
		while(it.hasNext()){
			Player p = it.next().getValue();
			if(p.getClass() != Human.class)
				((IA)p).setThreadSuspended(true);
		}
		
		notify();
	}
	
	/**
	 * Resuming the game
	 */
	public synchronized void resumingGame(){
		threadSuspended = false;
		
		Iterator<Map.Entry<Integer, Player>> it = playersData.entrySet().iterator();
		while(it.hasNext()){
			Player p = it.next().getValue();
			if(p.getClass() != Human.class)
				((IA)p).setThreadSuspended(false);
		}
		
		notify();
	}
	
	/**
	 * stop IA&Engine threads
	 */
	public void stopGame(){

		//stop Engine
		running = false;
		
		//stop IA
		Iterator<Map.Entry<Integer, Player>> it = playersData.entrySet().iterator();
		while(it.hasNext()){
			Player p = it.next().getValue();
			if(p.getClass() != Human.class)
				((IA)p).stopThread();
		}

	}
	
	/**
	 *	End of game
	 */
	public void checkEndGame(){
		boolean stillAgents = false;

		Map<Integer, Player> players = playersData;
		
		//check if there is still agents on paths
		Iterator<Map.Entry<Integer, Player>> it = players.entrySet().iterator();
		
		while(it.hasNext()){
			Player p = it.next().getValue();
			
			//check if players have agents
			if(p.getNumberOfAgents() != 0){
				stillAgents = true;
				break;
			}
		}
		
		//if there is agents, return
		if(stillAgents)
			return;
		
		// ======= no agent anymore, check bases
		
		Iterator<Base> itBase = bases.iterator();
		boolean isMechant = false;
		boolean isHuman = false;
		
		//check mechant and human base
		while(itBase.hasNext()){
			Base b = itBase.next();
			
			//neutral base, next !
			if(b.getPlayer() == null) continue;
			
			if(b.getPlayer().getPlayerNumber() == 1 ){
				isHuman = true;
			} else if(b.getPlayer().getPlayerNumber() >1)
				isMechant = true;
		}
		
		//if player has bases and mechant not one 
		if(isHuman &&  !isMechant){
			stopGame();
			((Human)players.get(1)).getIhm().getMenu().setBackgroundEnd(true);
		}
		//if there are no more human base
		else if(!isHuman){
			stopGame();
			((Human)players.get(1)).getIhm().getMenu().setBackgroundEnd(false);
		}
		
		
		
	}
		
}
