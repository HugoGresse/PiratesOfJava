package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.engine.Base.Power;

/**
 * A projectile is the weapons launch by a tower
 * 
 * @author Hugo
 *
 */
public class Projectile {
	private double speed;
	private Point position;
	private Agent target;
	private int strength;
	
	//int for any effect
	private int optionInt;
	
	//Variable used to calculate positon
	private double a;
	private double b;
	private int direction;
	
	private static int numPro = 0;
	
	/**
	 * Create a projectile.
	 * 
	 * @param origin
	 * 				THe first position of the projectile, should be the tower
	 * @param targetTest
	 * 				THe point the projectile should go
	 */
	public Projectile(Tower t, Point origin, Agent target){
		t.addProjectiles(this);
		this.position = origin;
		this.target = target;
		this.speed = t.getProjectileSpeed();
		this.strength = t.getStrength();
		
		//Calculate the variables used (y=ax+b)
		calcDestination();
		optionInt = 1;
		numPro++;
	}
	
	/**
	 * Getters/setters
	 */
	public double getSpeed() {
		return speed;
	}

	public Point getPosition() {
		return position;
	}

	public Agent getAgent() {
		return target;
	}

	public int getOptionInt() {
		return optionInt;
	}

	
	public void setPosition(int x, int y) {
		this.position.setLocation(x, y);
	}
	public void setPosition(Point position) {
		this.position = position;
	}

	public void setAgent(Agent target) {
		this.target = target;
	}
	
	public void setOptionInt(int optionInt) {
		this.optionInt = optionInt;
	}
	public void addOptionInt() {
		this.optionInt++;
	}
	
	
	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	/**
	 * Remove the projectile strengh from agent after applied Power
	 * @param projectile
	 */
	public void impactAgentLife() {
		if(this.getAgent().getBaseStart().getPower() == Power.RESISTANCE){
			this.getAgent().loseLife(this.getStrength());
		}else{
			this.getAgent().loseLife(this.getStrength());
		}

	}
	
	/**
	 * Calculate the straight of the projectile.
	 */
	private void calcDestination(){
		// yb-ya / xb-xa
		this.a = (target.getPosition().getY() - this.position.getY())/(target.getPosition().getX() - this.position.getX());
		this.b = this.position.getY() - this.a*this.position.getX();
		
		//calculate if we have to go on left or right
		if(target.getPosition().getX() - this.position.getX() >=0)
			this.direction  = 1;
		else this.direction = -1;
	}
	
	/**
	 * Update the position of the projectile.
	 * @return 
	 * 			return true is the projectile is arrived
	 */
	public boolean updateProjectile(){
		//System.out.println(this.getPosition().getX()+" - "+ this.getPosition().getY());
		
		if(this.position.distance(this.target.getPosition()) <= 50){
			//System.out.println(this.position.distance(this.target.getPosition()));
		}
		// If the projectile is arrived at it's destination : 
		if(this.position.distance(this.target.getPosition()) <= 10){
			//System.out.println("Should delete this projectile now - id " + numPro);
			return true;
		}
		
		//Update the direction/straight
		calcDestination();
		
		int x;
		if(direction == -1)
			x = (int) (this.position.getX() - speed);
		else
			x = (int) (this.position.getX() + speed);
		
		
		//y = a * x +b
		int y = (int) (this.a * x + this.b) ; 
		
		this.setPosition(x, y);
		
		return false;
	}
}
