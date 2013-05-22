package fr.imac.javawars.player;


public class Human extends Player{
	
	protected Ihm ihm;
	
	/**
	 * create a physical player : with an interface and button etc.
	 * also call the UI
	 * @param pNum
	 * @param name
	 */
	public Human(int pNum, String name, int r, int g, int b) {
		super(pNum, name, "human", r,g,b);
		
	}

	/**
	 * initialize ihm
	 */
	public void initIhm(String name){
		ihm = new Ihm(name, this);
	}
	
	/**
	 * update player's money
	 */
	public void update(){
		ihm.getSidebar().updateMoney(this.getMoney());
		ihm.getBottomBar().updateTowersLabel();
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
