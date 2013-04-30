package fr.imac.javawars.engine;

import fr.imac.javawars.player.Player;

public class PlayerEngine {

	public PlayerEngine() {
		// TODO Auto-generated constructor stub
	}

	public void changePlayerMoney(Player p, int money){
		p.setMoney(p.getMoney() + money);
		
	}
	
	
	
	
}
