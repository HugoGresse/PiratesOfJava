package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

public interface Unite {
	
	//methods
	public void loseLife(float loseLife);
	public void addLife(float addLife);
	
	//getters/setters
	public void setLife(float life);
	public float getLife();
	public void setPosition(Point p);
	public Point getPosition();
	public Player getPlayer();
	public void setPlayer(Player p);
}
