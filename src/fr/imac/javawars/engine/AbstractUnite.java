package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

public abstract class AbstractUnite implements Unite {
	int life;
	Point position;
	String texture;
	Player player;
	
	//constructor
	public AbstractUnite(int life, Point position, Player player){
		super();
		this.life = life;
		this.position = position;
		this.player = player;	
	}
	
	//methods
	public void loseLife(int lifeLose) {
		this.life -= lifeLose;
	}

	public void addLife(int lifeAdd) {
		this.life += lifeAdd;
	}

	// Getters/setters
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
