package fr.imac.javawars.player;

public abstract class Player {
	

	private String name;
	private int money;
	protected int playerNumber = 0;
	protected String playerType;
	private int r;
	private int g;
	private int b;
	
	/**
	 * Create a player with given plyaernumber, a name and a type (IA or Human)
	 * 
	 * @param pNum
	 * 				an player number
	 * @param name
	 * 				A name for the player
	 * @param type
	 * 				IA if it's a IA or Human
	 */
	public Player(int pNum, String name, String type, int r, int g, int b) {
		this.name = name;
		playerNumber = pNum;
		this.playerType = type;
		this.money = 20;
		this.r = r;
		this.b = b;
		this.g = g;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}

	public void changeMoney(double money) {
		this.money += money;
	}


	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}
	public String getPlayerType(){
		return playerType;
	}

	public void setPlayerNumber(int n){
		playerNumber = n;
	}
	public int getPlayerNumber(){
		return playerNumber;
	}
	
	public int getR(){
		return r;
	}
	
	public int getG(){
		return g;
	}
	
	public int getB(){
		return b;
	}
	
	
	public abstract void update();
	
	
}
