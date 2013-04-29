package fr.imac.javawars.engine;

public class PlayerInfos {
	// number used to identify the player and in the algorythm of area influence of the base
	private int identifier; 
	private String name;
	private double money;
	
	//constructor
	public PlayerInfos(int identifier, String name, double money) {
		super();
		this.identifier = identifier;
		this.name = name;
		this.money = money;
	}
	
	public PlayerInfos() {
		this(0, "", 0.0);
	}
	
	//getters/setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
}
