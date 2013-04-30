package fr.imac.javawars.engine;

import java.util.LinkedList;

import fr.imac.javawars.player.Player;

public class Base extends AbstractTowerBase {
	private int capacity;
	private int speedRegeneration;
	private LinkedList<Tower> towers;
	private Power power;
	
	public enum Power{
		NORMAL,
		SPEED_UP,
		LIFE_UP,
		RESISTANCE;
	}
	
	// CONSTRUCTOR
	public Base(int life, Point position, String texture, Player player,double actionField, int capacity, int speedRegeneration) {
		super(life, position, texture, player, actionField);
		this.capacity = capacity;
		this.speedRegeneration = speedRegeneration;
		this.towers = new LinkedList<Tower>();
		this.power = Power.NORMAL;
	}
	
	// METHODS
	public void regeneration(){
		// A REMPLIR
	}
	
	public void increaseCapacity(){
		// A REMPLIR
	}
	
	public void sendAgents(){
		// A REMPLIR
	}
	
	public void receiveAgents(){
		// A REMPLIR
	}
	
	public void addTower(Tower t){
		towers.add(t);
	}
	
	public void deleteTower(Tower t){
		// A REMPLIR
	}
	
	//GETTERS-SETTERS
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getSpeedRegeneration() {
		return speedRegeneration;
	}

	public void setSpeedRegeneration(int speedRegeneration) {
		this.speedRegeneration = speedRegeneration;
	}

	public LinkedList<Tower> getTowers() {
		return towers;
	}

	public void setTowers(LinkedList<Tower> towers) {
		this.towers = towers;
	}

	public Power getPower() {
		return power;
	}

	public void setPower(Power power) {
		this.power = power;
	}
}
