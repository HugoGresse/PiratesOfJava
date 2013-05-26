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
	
	/*
	 * The price of the tower
	 */
	private int price;
	/**
	 * The damage/strengh of the tower (per projectile)
	 */
	private int strength;
	/**
	 * The upgrade of strengh the tower have
	 */
	private int upgradeStrengh;
	/**
	 * The upgrade of range/actionfield the tower have
	 */
	private int upgradeRange;
	/**
	 * how much rapidly the tower can send projectile (in ms)
	 */
	private int attackSpeed;
	/**
	 * The speed of the projectile
	 */
	private double projectileSpeed;
	/**
	 * The last time the tower attack !
	 */
	private double lastTimeSent;
	
	private ArrayList<Projectile> projectiles;
	


	//constructor
	public Tower(Player player,  Point position, int life, int price, double actionField , int strength, int attackSpeed, double projectileSpeed) {
		super(life, position, player, actionField);
		this.price = price;
		this.strength = strength;
		this.upgradeStrengh = 0;
		this.upgradeRange = 0;
		this.attackSpeed = attackSpeed;
		this.projectileSpeed = projectileSpeed;
		this.projectiles = new ArrayList<Projectile>();
		
		this.lastTimeSent = System.currentTimeMillis();
	}
	


	//methods
	public double sellTower(){
		double price = this.getPrice();
		
		projectiles.clear();
		return price;
	}
	
	public void increaseStrength(int val){
		System.out.println("implement this method increaseStrengh TOwer");
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
	
	
	public double getAttackSpeed() {
		return attackSpeed;
	}

	public double getProjectileSpeed() {
		return projectileSpeed;
	}
	
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
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
	
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectiles(Projectile p) {
		this.projectiles.add(p);
	}
	
	/**
	 * Order tower to send projectiles on the specified Point
	 * 
	 * @param target
	 * 				The target/Agent of the projectile
	 */
	public void attackAgent(Agent target){
		
		
		//if the tower cannot attack yet
		if(System.currentTimeMillis() - lastTimeSent < attackSpeed)
			return;
		
		System.out.println("attack ");
		lastTimeSent = System.currentTimeMillis();
		
		this.addProjectiles(new Projectile(this, (Point)this.getPosition().clone(), target));
	}
	
}
