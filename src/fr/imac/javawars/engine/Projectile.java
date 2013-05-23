package fr.imac.javawars.engine;

import java.awt.Point;

/**
 * A projectile is the weapons launch by a tower
 * 
 * @author Hugo
 *
 */
public class Projectile {
	private double speed;
	private Point position;
	private Point targetTest;
	private Agent target;
	
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
	public Projectile(Tower t, Point origin, Point targetTest){
		t.addProjectiles(this);
		this.position = origin;
		this.targetTest = targetTest;
		this.speed = 1;
		
		//Calculate the variables used (y=ax+b)
		calcDestination();
		
		numPro++;
	}
	
	public double getSpeed() {
		return speed;
	}

	public Point getPosition() {
		return position;
	}

	public Point getTargetTest() {
		return targetTest;
	}

	public Agent getTarget() {
		return target;
	}

	
	public void setPosition(int x, int y) {
		this.position.setLocation(x, y);
	}
	public void setPosition(Point position) {
		this.position = position;
	}

	public void setTargetTest(Point targetTest) {
		this.targetTest = targetTest;
	}

	public void setTarget(Agent target) {
		this.target = target;
	}
	
	/**
	 * Calculate the straight of the projectile.
	 */
	private void calcDestination(){
		// yb-ya / xb-xa
		this.a = (targetTest.getY() - this.position.getY())/(targetTest.getX() - this.position.getX());
		this.b = this.position.getY() - this.a*this.position.getX();
		
		//calculate if we have to go on left or right
		if(this.targetTest.getX() - this.position.getX() >=0)
			this.direction  = 1;
		else this.direction = -1;
	}
	
	/**
	 * Update the position of the projectile.
	 * @return 
	 * 			return true is the projectile is arrived
	 */
	public boolean updateProjectile(){
		System.out.println(this.getPosition().getX()+" - "+ this.getPosition().getY());
		
		
		// If the projectile is arrived at it's destination : 
		if(this.position.distance(this.targetTest) <=0){
			System.out.println("Should delete this projectile now");
			return true;
		}
		
		//FIXME calcDestination();
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
