package fr.imac.javawars.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionAgentSend;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.dispatcher.ActionTowerUpgrade;
import fr.imac.javawars.engine.Agent;
import fr.imac.javawars.engine.Base;
import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.engine.TowerBombe;
import fr.imac.javawars.engine.TowerBounce;
import fr.imac.javawars.engine.TowerFreeze;
import fr.imac.javawars.engine.TowerLaser;
import fr.imac.javawars.engine.TowerMachinegun;
import fr.imac.javawars.engine.TowerMissile;
import fr.imac.javawars.engine.TowerPoison;
import fr.imac.javawars.engine.TowerSniper;




public class IA extends Player implements Runnable{
	
	protected volatile boolean running = true;
	private double beginTime = 0;

	
	public IA(int pNum, String name) {
		super(pNum, name, "ia");
		
	}
	
	public String getPlayerType(){
		return "ia";
	}
	
	public void stopThread(){
		running = false;
	}
	
	
	@Override
	public void run() {
		while(running){
			try {
				Random rnd = new Random();
				createAction(System.currentTimeMillis(), rnd);
				//System.out.println(this.pInfos);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void update(){
	}

	private void createAction(double currentTime, Random rnd){
		if (beginTime == 0)
			beginTime = currentTime;
		
		// All min 6s maxi 12s create new "action"
		double numRnd = rnd.nextDouble()+1;
		if (currentTime - beginTime < 6000*numRnd)
			return;
		
		
		// Calculate random type of action to not all AI create same action in the same times
		int type = rnd.nextInt(10)+1;
		//int type = 4;
		if(type>0 && type <4)
			sendIaAgent(rnd);
		else if (type>3 && type<6)
			createTowers(rnd);
		else if (type>5 && type<8)
			updateTower(rnd);
		else
			System.out.println("no action");

		//System.out.println(this);
		beginTime = currentTime;
	}
	
	private void createTowers(Random rnd){
		
		// Find number of current AI and Bases
		Base base = getBaseIA();
		if (base == null)
			return;
		int playerNumber = base.getPlayer().getPlayerNumber();
		
		// Select ArrayList associate the player
		ArrayList<Point> currentListTower = new ArrayList<Point>();
		switch(playerNumber){
			case 2:
				currentListTower = JavaWars.getEngine().getBasesManager().getInfluenceJ2();
				break;
			case 3:
				currentListTower = JavaWars.getEngine().getBasesManager().getInfluenceJ3();
				break;
			case 4:
				currentListTower = JavaWars.getEngine().getBasesManager().getInfluenceJ4();
				break;	
			default:
				break;
		}
		
		//System.out.println("Size of Player "+ playerNumber +" : "+currentListTower.size());
		int locationTower = rnd.nextInt(currentListTower.size());
		
		int typeTower = rnd.nextInt(8);
		createTower(typeTower, currentListTower.get(locationTower));
	
	}
	
	private void createTower(int typeTower, Point locationTower){
		
		ActionTowerCreate myAction = null;
		
		switch (typeTower) {
		case 0:
			myAction = new ActionTowerCreate(this,new TowerBombe(locationTower, this));
			break;
		case 1:
			myAction = new ActionTowerCreate(this,new TowerBounce(locationTower, this));
			break;
		case 2:
			myAction = new ActionTowerCreate(this,new TowerFreeze(locationTower, this));
			break;
		case 3:
			myAction = new ActionTowerCreate(this,new TowerLaser(locationTower, this));
			break;
		case 4:
			myAction = new ActionTowerCreate(this,new TowerMachinegun(locationTower, this));
			break;
		case 5:
			myAction = new ActionTowerCreate(this,new TowerMissile(locationTower, this));
			break;
		case 6:
			myAction = new ActionTowerCreate(this,new TowerPoison(locationTower, this));
			break;
		case 7:
			myAction = new ActionTowerCreate(this,new TowerSniper(locationTower, this));
			break;

		default:
			break;
		}
		JavaWars.getDispatcher().addAction(myAction);
	}
	
	
	private void sendIaAgent(Random rnd){
		
		Base base = getBaseIA();
		if (base == null)
			return;
		int playerNumber = base.getPlayer().getPlayerNumber();
		
		
		Base baseTarget = getRndBase(playerNumber, rnd);
		
		// For send several agent
		int numAgentSend = rnd.nextInt(base.getCapacity()/2)+1;
		for(int i = 0; i< numAgentSend; ++i) {
			ActionAgentSend myAction = new ActionAgentSend( base.getPlayer(), base, baseTarget);
			JavaWars.getDispatcher().addAction(myAction);
		}
		
	}
	
	
	/**
	 * Send Agent to defense him
	 * @param start
	 * @param target
	 */
	public void sendIaAgent(Base start, Base target){
		Random rnd = new Random();
		if(rnd.nextInt(10)>2)
			return;
		ActionAgentSend myAction = new ActionAgentSend( start.getPlayer(), start, target);
		JavaWars.getDispatcher().addAction(myAction);
		
	}
	
	// Update a tower of the current AI player
	private void updateTower(Random rnd){
		// Find current Base of the AI player
		Base base = getBaseIA();
		if (base == null)
			return;
		int playerNumber = base.getPlayer().getPlayerNumber();
		
		LinkedList<Tower> towersCurrentPlayer = towersCurrentPlayer(playerNumber);
		
		// If AI player hasn't got tower --> quit the method
		if (towersCurrentPlayer.size()==0)
			return;
		
		// Else, select with random a tower and type of update
		Tower towerToUp = towersCurrentPlayer.get(rnd.nextInt(towersCurrentPlayer.size()));
		int typeUp = rnd.nextInt(2)+1;
		if (typeUp == 1) {
			if(towerToUp.getUpgradeRange()>5)
				return;
		}
		if (typeUp == 2) {
			if(towerToUp.getUpgradeStrength()>5)
				return;
		}

		ActionTowerUpgrade myAction = new ActionTowerUpgrade(base.getPlayer(), towerToUp,  typeUp);
		JavaWars.getDispatcher().addAction(myAction);    	
	}
	
	// Create a list of Tower belong to current AI player
	private LinkedList<Tower> towersCurrentPlayer(int playerNumber){
		LinkedList<Tower> towersCurrentPlayer = new LinkedList<Tower>();
		
		Iterator<Tower> itTower = JavaWars.getEngine().getTowers().iterator();
		while (itTower.hasNext()) {
			Tower currentTower = itTower.next();
			if (currentTower.getPlayer().getPlayerNumber() == playerNumber)
				towersCurrentPlayer.add(currentTower);
		}
		return towersCurrentPlayer;
		
	}
	
	/**
	 * Find a random Base
	 * @param playerNumber : number of the current AI player
	 * @param rnd
	 * @return Base
	 */
	private Base getRndBase(int playerNumber, Random rnd){
		CopyOnWriteArrayList<Base> bases = JavaWars.getEngine().getBases();
		
		// Random "location" in the list to select just 1 base
		int locationBase=rnd.nextInt(bases.size());
		
		// Check if the base select is not the base of the current AI
		if (bases.get(locationBase).getPlayer() != null && bases.get(locationBase).getPlayer().getPlayerNumber() == playerNumber)
			return bases.get(locationBase+1);
		
		return bases.get(locationBase);
		
		
	}
	
	/**
	 * Find the base of the current IA
	 * @return Base
	 */
	private Base getBaseIA(){
		Iterator<Base> itBase = JavaWars.getDispatcher().getBases().iterator();
		while (itBase.hasNext()) {
			Base base = itBase.next();
			if (base.getPlayer() != null && base.getPlayer().getPlayerNumber() == this.getPlayerNumber()){
				return base;
			}
		}
		return null;
		
	}

}

