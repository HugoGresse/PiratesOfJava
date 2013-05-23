package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.ArrayList;

import fr.imac.javawars.player.Player;

/**
 * The base of all tower. 
 * 
 * 
 * @author Hugo
 * @see AbstractTowerBase
 */
public abstract class Tower extends AbstractTowerBase {
	private int price;
	private int strength;
	private int upgradeStrengh;
	private int upgradeRange;
	private double attackSpeed;
	
	private ArrayList<Projectile> projectiles;
	
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	//constructor
	public Tower(Player player,  Point position, int life, int price, double actionField , int strength, double attackSpeed) {
		super(life, position, player, actionField);
		this.price = price;
		this.strength = strength;
		this.upgradeStrengh = 0;
		this.upgradeRange = 0;
		this.attackSpeed = attackSpeed;
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

	public void changeStrength(int strength) {
		this.strength += strength;
		this.upgradeStrengh++;
	}
	
	@Override
	public void changeActionField(double num){
		this.actionField+=num;
		this.upgradeRange++;
	}

	/**
	 * Order tower to send projectiles on the specified Point
	 * 
	 * @param target
	 * 				The target of the projectile
	 */
	public void attackPoint(Point target){
		
	}
	
	
	
	
	
}
