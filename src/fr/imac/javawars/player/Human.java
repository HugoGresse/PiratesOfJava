package fr.imac.javawars.player;

public class Human extends Player{
	
	protected Ihm ihm;
	
	public Human(int pNum, String name) {
		super(pNum, name);
		ihm = new Ihm("JavaWars", this);
	}
	

	public String getPlayerType(){
		return "human";
	}
	
}
