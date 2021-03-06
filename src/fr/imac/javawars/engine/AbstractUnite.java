package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

/**
 * Abstract class representing the caracteristics of a unit
 * A unit has life, a position and a player who owns it
 *
 */
public abstract class AbstractUnite implements Unite {
	/**
	 * life of the unit
	 */
	float life;
	/**
	 * position on the map of the unit
	 */
	Point position;
	/**
	 * player who owns the unit
	 */
	Player player;
	
	/**
	 * Constructor
	 * 
	 * @param life
	 * 		life of the unit
	 * @param position
	 * 		position of the unit
	 * @param player
	 * 		player who owns the unit
	 */
	public AbstractUnite(float life, Point position, Player player){
		super();
		this.life = life;
		this.position = position;
		this.player = player;	
	}
	
	//methods
	/**
	 * Removed life
	 * 
	 * @param lifeLose
	 * 		life removed
	 */
	public void loseLife(float lifeLose) {
		if (this.life>0)
			this.life -= lifeLose;
	}

	/**
	 * Add life
	 * 
	 * @param lifeAdd
	 * 		life added
	 */
	public void addLife(float lifeAdd) {
		this.life += lifeAdd;
	}

	// Getters/setters
	public float getLife() {
		return life;
	}

	public void setLife(float life) {
		this.life = life;
	}
	
	/**
	 * Check if the unit is alive
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
		result = prime * result + (int)life;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
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
		return true;
	}
	
	
}
