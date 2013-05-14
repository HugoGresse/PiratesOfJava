package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

public class Agent extends AbstractUnite {
	private int speed;

	//constructor
	public Agent(int life, Point position, String texture, Player player, int speed) {
		super(life, position, texture, player);
		this.speed = speed;
	}
	
	//methods
	public void changePosition(Point destination){
		//A REMPLIR
	}
	
	public void destroyAgent(){
		// A REMPLIR
	}
	
	//getters/setters
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
