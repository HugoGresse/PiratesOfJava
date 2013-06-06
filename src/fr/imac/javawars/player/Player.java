package fr.imac.javawars.player;

import java.util.concurrent.ConcurrentLinkedQueue;
import fr.imac.javawars.engine.Agent;

/**
 * Represents a player, can be a human or an IA
 * 
 *
 */
public abstract class Player {
	
	/**
	 * name of the player
	 */
	private String name;
	/**
	 * list of agents owned by the player
	 */
	protected ConcurrentLinkedQueue<Agent> agents;
	/**
	 * number of agents owned by the player
	 */
	private int numberOfAgents;
	/**
	 * money owned by the player
	 */
	private int money;
	/**
	 * number identifying the player
	 */
	protected int playerNumber = 0;
	/**
	 * type of the player (human or IA)
	 */
	protected String playerType;
	
	/**
	 * Create a player with given playernumber, a name and a type (IA or Human)
	 * 
	 * @param pNum
	 * 				an player number
	 * @param name
	 * 				A name for the player
	 * @param type
	 * 				IA if it's a IA or Human
	 */
	public Player(int pNum, String name, String type) {
		this.name = name;
		this.money = 20;
		this.agents = new ConcurrentLinkedQueue<Agent>();
		this.numberOfAgents = 0;
		
		this.playerNumber = pNum;
		this.playerType = type;
	}
	
	/**
	 * add money to the player
	 * @param money
	 * 		amount of money we add
	 */
	public void changeMoney(double money) {
		this.money += money;
	}

	// getters and setters
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
	
	public void setAgents(ConcurrentLinkedQueue<Agent> agents) {
		this.agents = agents;
	}
	
	public ConcurrentLinkedQueue<Agent> getAgents() {
		return agents;
	}

	public void addAgent(Agent agent){
		agents.add(agent);
		numberOfAgents++;
	}
	
	public int getNumberOfAgents(){
		return this.numberOfAgents;
	}
	
	public void setNumberOfAgents(int num){
		this.numberOfAgents = num;
	}
	
	public abstract void update();
	
}
