package fr.imac.javawars.ihm;

public class Player {
	
	private String name;
	private int life;
	private boolean isIa;
	
	public Player(String nn){
		this(nn, 0, false);
		
	}	
	
	public Player(String nn, int life, boolean isia){
		super();
		name = nn;
		this.life = life;
		isIa = isia; 
	}	
	
	//remove "i" life from this player
	public void looseLife(int i){
		this.life -= i;
	}
	//add "i" life from this player
	public void addLife(int i){
		this.life += i;
	}
	
	
}
