package fr.imac.javawars.engine;

public class PlayerInfos {
	private String name;
	private double money;
	
	//constructor
	public PlayerInfos(String name) {
		super();
		this.name = name;
		this.money = 0.0;
	}
	
	//getters/setters
	public String getName() {
		return name;
	}
	
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

}
