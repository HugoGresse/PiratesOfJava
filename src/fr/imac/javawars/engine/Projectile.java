package fr.imac.javawars.engine;

import java.awt.Point;

/**
 * A projectile is the weapons launch by a tower
 * 
 * @author Hugo
 *
 */
public abstract class Projectile {
	private double speed;
	private Point position;
	private Point targetTest;
	private Agent target;
	
	//Variable used to calculate positon
	private double a;
	private double b;
	
	/**
	 * Create a projectile.
	 * 
	 * @param origin
	 * 				THe first position of the projectile, should be the tower
	 * @param targetTest
	 * 				THe point the projectile should go
	 */
	public Projectile(Point origin, Point targetTest){
		this.position = origin;
		this.targetTest = targetTest;
		this.speed = 0.01;
		
		//Calculate the variables used (y=ax+b)
		calcDestination();
		
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
		this.b = this.position.getY();
	}
	
	/**
	 * Update the position of the projectile.
	 * @return 
	 */
	public void updateProjectile(){
		
		// If the projectile is arrived at it's destination : 
		if(this.position.distance(this.targetTest) <=0){
			System.out.println("Should delete this projectile now");
			return;
		}
		
		//FIXME calcDestination();
		
		int x = (int) (this.position.getX() + speed);
		//y = a * (previous x) +b
		int y = (int) (this.a * this.position.getX() + this.b) ; 
		
		this.setPosition(x, y);
		
		
		//TODO : delete it when arrived.
	}
}
