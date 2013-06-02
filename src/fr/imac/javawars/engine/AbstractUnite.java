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
	
	/**
	 * Check if the life of unite
	 */
	public boolean isInLife(){
		if(this.life <= 0)
			return false;
		else return true;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + life;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((texture == null) ? 0 : texture.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractUnite))
			return false;
		AbstractUnite other = (AbstractUnite) obj;
		if (life != other.life)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (texture == null) {
			if (other.texture != null)
				return false;
		} else if (!texture.equals(other.texture))
			return false;
		return true;
	}
	
	
}
