package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

public class Tower extends AbstractTowerBase {
	private int price;
	private int strength;
	private int upgradeStrengh;
	private int upgradeRange;
	
	//constructor
	public Tower(int life, Point position, Player player, double actionField ,int price, int strength ) {
		super(life, position, player, actionField);
		this.price = price;
		this.strength = strength;
		this.upgradeStrengh = 0;
		this.upgradeRange = 0;
		
	}
	
	//methods
	public double sellTower(){
		double price = this.getPrice();
		return price;
	}
	
	
	public void increaseStrength(int val){
		
	}
	
	public void attackAgents(){
		
	}
	
	public int getUpgradeStrengh() {
		return upgradeStrengh;
	}

	public int getUpgradeRange() {
		return upgradeRange;
	}

	public void setUpgradeStrengh(int upgradeStrengh) {
		this.upgradeStrengh = upgradeStrengh;
	}

	public void setUpgradeRange(int upgradeRange) {
		this.upgradeRange = upgradeRange;
	}

	//getters/setters
	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public void changeStrength(int strength) {
		this.strength += strength;
	}

	
}
