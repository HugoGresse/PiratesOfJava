package fr.imac.javawars.engine;

public abstract class AbstractUnite implements Unite {
	int life;
	Point position;
	String texture;
	PlayerInfos player;
	
	//constructor
	public AbstractUnite(int life, Point position, String texture, PlayerInfos player){
		super();
		this.life = life;
		this.position = position;
		this.texture = texture;
		this.player = player;	
	}
	
	//methods
	public void loseLife() {
		// A REMPLIR

	}

	public void addLife() {
		// A REMPLIR

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

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public PlayerInfos getPlayer() {
		return player;
	}

	public void setPlayer(PlayerInfos player) {
		this.player = player;
	}
}
