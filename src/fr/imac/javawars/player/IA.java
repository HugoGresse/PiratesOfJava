package fr.imac.javawars.player;

public class IA extends Player implements Runnable{
	
	protected volatile boolean running = true;
	
	public IA(int pNum, String name, int r, int g, int b) {
		super(pNum, name, "ia", r,g,b);
		
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
		
	}

}
