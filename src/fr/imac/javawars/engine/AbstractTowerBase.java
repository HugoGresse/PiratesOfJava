package fr.imac.javawars.engine;

import fr.imac.javawars.player.Player;

public abstract class AbstractTowerBase extends AbstractUnite {
	private double actionField;
	
	//constructor
	public AbstractTowerBase(int life, Point position, String texture,Player player, double actionField) {
		super(life, position, texture, player);
		this.actionField = actionField;
	}
	
	//methods
	public void changePlayer(Player player){
		//A REMPLIR
	}
	
	//getters/setters
	public double getActionField() {
		return actionField;
	}

	public void setActionField(double actionField) {
		this.actionField = actionField;
	}
}
