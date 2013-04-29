package fr.imac.javawars.player;

import fr.imac.javawars.engine.PlayerInfos;

public abstract class Player {
	
	
	protected PlayerInfos pInfos;
	protected int playerNumber = 0;
	
	public Player(int pNum, String name) {
		pInfos = new PlayerInfos(name);
		playerNumber = pNum;
	}

	
	public PlayerInfos getpInfos() {
		return pInfos;
	}
	
	public void setpInfos(PlayerInfos pInfos) {
		this.pInfos = pInfos;
	}
	
	public void setPlayerNumber(int n){
		playerNumber = n;
	}
	
	public int getPlayerNumber(){
		return playerNumber;
	}
	
	public abstract String getPlayerType();
	
	
}
