package fr.imac.javawars.engine;

public class PlayerInfos {
	private String name;
	private double money;
	
	//constructor
	public PlayerInfos(String name) {
		super();
		this.name = name;
		this.money = 20;
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
	
	public void reduceMoney(double money) {
		this.money = this.money - money;
	}

	@Override
	public String toString() {
		return "PlayerInfos [name=" + name + ", money=" + money + "]";
	}
	
	
}
