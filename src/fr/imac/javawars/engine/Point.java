package fr.imac.javawars.engine;

public class Point {
	private double x;
	private double y;
	
	//constructor
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	//getters/setters
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
