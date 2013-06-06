package fr.imac.javawars.dispatcher;

import fr.imac.javawars.engine.Base;
import fr.imac.javawars.engine.Base.Power;
import fr.imac.javawars.player.Player;

public class ActionBaseUpgrade extends ActionBase{
	private Power power;
	private int price;
	

	public ActionBaseUpgrade(Player player, Base base, Power power) {
		super(player, base);
		this.power = power;

		switch (power){
			// speed regeneration upgrade
			case NORMAL : this.price = 4;
				break;
			case MULT : this.price = 10;
				break;
			case SPEED_UP : this.price = 10;
				break;
			case LIFE_UP : this.price = 10;
				break;
			case RESISTANCE : this.price = 5;
				break;
			
			default: 
				break;			
		}
	}
	
	public Power getPower(){
		return this.power;
	}

	public int getPrice(){
		return this.price;
	}
}
