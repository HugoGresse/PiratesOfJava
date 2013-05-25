package fr.imac.javawars.player;

import java.awt.Point;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.engine.TowerBombe;

public class IA extends Player implements Runnable{
	
	protected volatile boolean running = true;
	
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

}
