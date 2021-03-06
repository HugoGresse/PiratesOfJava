package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

public abstract class AbstractTowerBase extends AbstractUnite {
	protected double actionField;
	
	//constructor
	public AbstractTowerBase(int life, Point position, Player player, double actionField) {
		super(life, position, player);
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
	
	public void changeActionField(double num) {
		this.actionField += num;
	}
	
	public void setActionField(double actionField) {
		this.actionField = actionField;
	}
}
