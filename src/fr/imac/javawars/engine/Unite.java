package fr.imac.javawars.engine;

public interface Unite {
	
	//methods
	public void loseLife();
	public void addLife();
	
	//getters/setters
	public void setLife(int life);
	public int getLife();
	public void setPosition(Point p);
	public Point getPosition();
	public PlayerInfos getPlayer();
	public void setPlayer(PlayerInfos p);
	public void setTexture(String texture);
	public String getTexture();
}
