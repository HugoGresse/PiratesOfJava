package fr.imac.javawars.player;

public class Human extends Player{
	
	protected Ihm ihm;
	
	/**
	 * create a physical player : with an interface and button etc.
	 * also call the UI
	 * @param pNum
	 * @param name
	 */
	
	public Human(int pNum, String name) {
		super(pNum, name, "human");
	}

	/**
	 * initialize ihm
	 */
	public void initIhm(String name){
		ihm = new Ihm(name);
	}
	
	/**
	 * update player
	 */
	public void update(){
		ihm.getSidebar().updateMoney(this.getMoney());
		ihm.getBottomBar().updateTowersLabel();
		ihm.getBottomBar().updateBasesLabel();
		ihm.getBottomBar().updateError();
	}
	
	/**
	 * Getters
	 */
	public String getPlayerType(){
		return "human";
	}
	
	public Ihm getIhm(){
		return ihm;
	}
}
