package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
	private int upgradeStrength;
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
	
	/**
	 * The hash map of projectiles
	 */
	private ConcurrentHashMap<Integer, Projectile> projectiles;
	private int numberOfProjectiles;
	

	//constructor
	public Tower(Player player,  Point position, int life, int price, double actionField , int strength, int attackSpeed, double projectileSpeed) {
		super(life, position, player, actionField);
		this.price = price;
		this.strength = strength;
		this.upgradeStrength = 0;
		this.upgradeRange = 0;
		this.attackSpeed = attackSpeed;
		this.projectileSpeed = projectileSpeed;
		this.projectiles = new ConcurrentHashMap<Integer, Projectile>();
		
		this.lastTimeSent = System.currentTimeMillis();
		this.numberOfProjectiles = 0;
	}
	
	@Override
	public boolean equals(Object o){
		if(getClass()== o.getClass()){
			if(getPosition().getX() == ((Tower)o).getPosition().getX() && getPosition().getY() == ((Tower)o).getPosition().getY() ){
				return true;
			}
			else return false;
		}
		else return false;
	}

	public void increaseStrength(int val){
		System.out.println("implement this method increaseStrengh TOwer");
	}
		
	public int getUpgradeStrength() {
		return upgradeStrength;
	}

	public int getUpgradeRange() {
		return upgradeRange;
	}

	public double getAttackSpeed() {
		return attackSpeed;
	}

	public double getProjectileSpeed() {
		return projectileSpeed;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public ConcurrentHashMap<Integer, Projectile> getProjectiles() {
		return projectiles;
	}
	
	public double getLastTimeSent() {
		return lastTimeSent;
	}

	public void setLastTimeSent(double lastTimeSent) {
		this.lastTimeSent = lastTimeSent;
	}

	public void setUpgradeStrength(int upgradeStrengh) {
		this.upgradeStrength = upgradeStrengh;
	}

	public void setUpgradeRange(int upgradeRange) {
		this.upgradeRange = upgradeRange;
	}
	
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	public void changeStrength(int strength) {
		this.strength += strength;
		this.upgradeStrength++;
	}
	
	@Override
	public void changeActionField(double num){
		this.actionField+=num;
		this.upgradeRange++;
	}

	public void addProjectiles(Projectile p) {
		numberOfProjectiles++;
		this.projectiles.put(numberOfProjectiles, p);
	}
	

	//methods
	public double sellTower(){
		double price = this.getPrice();
		
		projectiles.clear();
		return price;
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
		
		lastTimeSent = System.currentTimeMillis();
		
		this.addProjectiles(new Projectile(this, (Point)this.getPosition().clone(), target));
	}
	

	
}
