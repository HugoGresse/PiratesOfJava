package fr.imac.javawars.player;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.imac.javawars.engine.Agent;

public abstract class Player {
	

	private String name;
	protected ConcurrentLinkedQueue<Agent> agents;
	private int numberOfAgents;
	private int money;
	protected int playerNumber = 0;
	protected String playerType;
	
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
	public Player(int pNum, String name, String type) {
		this.name = name;
		this.money = 20;
		this.agents = new ConcurrentLinkedQueue<Agent>();
		this.numberOfAgents = 0;
		
		this.playerNumber = pNum;
		this.playerType = type;
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
	
	
	public ConcurrentLinkedQueue<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ConcurrentLinkedQueue<Agent> agents) {
		this.agents = agents;
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
