package fr.imac.javawars.player;

public class Human extends Player{
	
	private Ihm ihm;
	
	public Human(String name) {
		super(name);
		ihm = new Ihm("JavaWars");
	}

	public String getPlayerType(){
		return "human";
	}
}
