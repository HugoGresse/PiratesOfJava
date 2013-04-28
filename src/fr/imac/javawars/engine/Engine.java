package fr.imac.javawars.engine;

import java.util.ArrayList;

public class Engine  implements Runnable{
	
	protected volatile boolean running = true;
	
	public Engine() {
		
	}
	
	public void stopThread(){
		running = false;
	}
	
	@Override
	public void run() {
		while(running){
			try {
				//System.out.println(this.pInfos);
				Thread.sleep(29);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
