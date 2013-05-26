package fr.imac.javawars.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.engine.Base;
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
		/*ActionTowerCreate myAction = null;
		myAction = new ActionTowerCreate(this,new TowerBombe(new Point(600, 400), this));
		
		JavaWars.getDispatcher().addAction(myAction);*/
	}

	private void createAction(double currentTime, Random rnd){
		if (beginTime == 0)
			beginTime = currentTime;
		
		// All min 6s maxi 12s create new "action"
		double numRnd = rnd.nextDouble()+1;
		if (currentTime - beginTime < 6000*numRnd)
			return;
		
		// Calculate random type of action to not all AI create same action in the same times
		int type = rnd.nextInt(7)+1;
		switch(type){
			case 1:
				System.out.println("update tower");
				break;
			case 4:
				System.out.println("create tower");
				createTowers(rnd);
				break;
			case 6:
				System.out.println("attaque base");
				break;
			default :
				System.out.println("no action");
				break;
		}
		
		//System.out.println(this);
		beginTime = currentTime;
	}
	
	private void createTowers(Random rnd){
		
		// Find position(x,y) of the base who belong to the current AI
		Iterator<Base> itBase = JavaWars.getDispatcher().getBases().iterator();
		Point center = new Point();
		int playerNumber = 0;
		while (itBase.hasNext()) {
			Base base = itBase.next();
			if (base.getPlayer() != null && base.getPlayer().getPlayerNumber() == this.getPlayerNumber()){
				center = base.getPosition();
				playerNumber = base.getPlayer().getPlayerNumber();
				break;
			}
		}
		
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
		
		System.out.println(currentListTower.get(locationTower));
		
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
	

}

