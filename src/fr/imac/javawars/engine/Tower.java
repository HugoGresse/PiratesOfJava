package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

public class Tower extends AbstractTowerBase {
	private double price;
	private int strength;
	
	//constructor
	public Tower(int life, Point position, Player player, double actionField,double price, int strength ) {
		super(life, position, player, actionField);
		this.price = price;
		this.strength = strength;
	}
	
	//methods
	public double sellTower(){
		double price = this.getPrice();
		return price;
	}
	
	public void changeActionField(){
		
	}
	
	public void increaseStrength(int val){
		
	}
	
	public void attackAgents(){
		
	}
	
	//getters/setters
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

}
