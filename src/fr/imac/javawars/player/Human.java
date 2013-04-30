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
		ihm = new Ihm("JavaWars", this);
	}
	

	public String getPlayerType(){
		return "human";
	}
	
	public Ihm getIhm(){
		return ihm;
	}
	
	public void update(){
		ihm.updateTestLabel(this.getMoney());
	}
}
