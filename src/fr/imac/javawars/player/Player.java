package fr.imac.javawars.player;

import fr.imac.javawars.engine.PlayerInfos;

public abstract class Player {
	
	
	protected PlayerInfos pInfos;
	
	public Player(String name) {
		pInfos = new PlayerInfos(name);
		
	}

	public PlayerInfos getpInfos() {
		return pInfos;
	}
	
	public void setpInfos(PlayerInfos pInfos) {
		this.pInfos = pInfos;
	}
	
	public abstract String getPlayerType();
	
	
}
