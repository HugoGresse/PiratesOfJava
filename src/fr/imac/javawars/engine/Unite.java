package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

public interface Unite {
	
	//methods
	public void loseLife();
	public void addLife();
	
	//getters/setters
	public void setLife(int life);
	public int getLife();
	public void setPosition(Point p);
	public Point getPosition();
	public Player getPlayer();
	public void setPlayer(Player p);
	public void setTexture(String texture);
	public String getTexture();
}
