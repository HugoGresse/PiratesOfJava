package fr.imac.javawars.engine;

public abstract class AbstractTowerBase extends AbstractUnite {
	private double actionField;
	
	//constructor
	public AbstractTowerBase(int life, Point position, String texture,PlayerInfos player, double actionField) {
		super(life, position, texture, player);
		this.actionField = actionField;
	}
	
	//methods
	public void changePlayer(PlayerInfos player){
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
