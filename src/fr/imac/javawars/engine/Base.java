package fr.imac.javawars.engine;

import java.util.LinkedList;

import fr.imac.javawars.player.Player;
/**
 * <b> Base represent a military base </b>
 * 
 *
 */

public class Base extends AbstractTowerBase {
	private int capacity;
	private int speedRegeneration;
	private LinkedList<Tower> towers;
	private Power power;
	/* table containing distances of the base
 	imagine a map of boxes but here stocked in a tab 1d
 	every element represents the distance of the box to the base */
	private int[] distanceMap;
	
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
		// for the moment values of H and W are not initialise with the map, should be
		int height = 4;
		int width = 4;
		// initialization of the map of the distances of the base
		this.initialiseDistanceMap(height, width);
	}
	
	public Base(){
		this(0, null, null, null, 0, 0, 0);
	}
	
	// METHODS
	/**
	 * Initialise the map distance of the base
	 * 
	 * @param height
	 * 		height of the map of the game
	 * @param width
	 * 		width of the map of the game
	 * 
	 * @see Base#distanceMap
	 */
	public void initialiseDistanceMap(int height, int width){
		// CHANGE WITH REAL VALUES
		//initialization should be done with a file (-2 for walls, -1 when not decided)
		this.distanceMap = new int[height*width];
		for(int i=0; i < height * width; i++ ){
			this.distanceMap[i] = -1;
		}
		//TEST VALUES
		this.distanceMap[0] = -2;
		this.distanceMap[1] = -2;
		this.distanceMap[2] = -2;
		this.distanceMap[3] = -2;
		this.distanceMap[4] = -2;
		this.distanceMap[7] = -2;
		this.distanceMap[8] = -2;
		this.distanceMap[11] = -2;
		this.distanceMap[12] = -2;
		this.distanceMap[13] = -2;
		this.distanceMap[14] = -2;
		this.distanceMap[15] = -2;
		
		// problem with the unite of position, need to think to it
		//this.distanceMap[(int) (this.position.getX() + this.position.getY() * width)] = 0;
		// test with the base in (1,2) (map corner is in 0,0)
		// CHANGE WITH REAL VALUES
		this.distanceMap[1+2*width] = 0;
	}
	
	/**
	 * Compute the distance map to the base
	 * 
	 * @param height
	 * 		height of the map of the game
	 * @param width
	 * 		width of the map of the game
	 * 
	 * @see Base#distanceMap
	 */
	public void computeDistanceMap(int height, int width){
		//creation of a queue of the boxes , for the moment a LinkedList
		LinkedList<Integer> boxesQueue = new LinkedList<Integer>();
		// use addLast to add the element at the end of the list
		// currentTestedPosition corresponding to the box whose neighbors boxes are tested
		// starting point is the box corresponding to the position of the base
		// CHANGE WITH REAL VALUES
		int currentTestedPosition = 1+2*width;
		// currentDistance represents the current distance to the base
		int currentDistance = 0;
		boxesQueue.addLast(currentTestedPosition);
		// while the queue isn't empty
		while (boxesQueue.size()>0){
			// every loop the distance to the base increases
			currentDistance ++;
			//test every neighbor boxes of the tested box, here we are testing diagonals too
			// if the value of the distance is -1, so we change it, if it's an other number, it means agents are not authorize to go there and so we don't change
			// the value to keep it negative, which will help us when we will move agents
			// use removeFirst to take the first element of the queue which retrieves and removes the first element of this list
			// deleting the first box of the queue for testing his neighbors
			currentTestedPosition = boxesQueue.removeFirst();
			// test left box
			if(distanceMap[currentTestedPosition - 1] == -1) {
				//giving the distance value to the base for the box
				this.distanceMap[currentTestedPosition - 1] = currentDistance;
				// adding the box to the queue to test his neighbors after
				boxesQueue.addLast(currentTestedPosition - 1);
			}
			// test right box
			if(distanceMap[currentTestedPosition + 1] == -1) {
				this.distanceMap[currentTestedPosition + 1] = currentDistance;
				boxesQueue.addLast(currentTestedPosition + 1);
			}
			// test above box
			if(distanceMap[currentTestedPosition - width] == -1) {
				this.distanceMap[currentTestedPosition - width] = currentDistance;
				boxesQueue.addLast(currentTestedPosition - width);
			}
			// test below box
			if(distanceMap[currentTestedPosition + width] == -1) {
				this.distanceMap[currentTestedPosition + width] = currentDistance;
				boxesQueue.addLast(currentTestedPosition + width);
			}
			// test above left box
			if(distanceMap[currentTestedPosition - width -1] == -1) {
				this.distanceMap[currentTestedPosition - width -1] = currentDistance;
				boxesQueue.addLast(currentTestedPosition - width -1);
			}
			// test above right box
			if(distanceMap[currentTestedPosition - width + 1] == -1) {
				this.distanceMap[currentTestedPosition - width + 1] = currentDistance;
				boxesQueue.addLast(currentTestedPosition - width + 1);
			}
			// test below left box
			if(distanceMap[currentTestedPosition + width - 1] == -1) {
				this.distanceMap[currentTestedPosition + width - 1] = currentDistance;
				boxesQueue.addLast(currentTestedPosition + width - 1);
			}
			// test below right box
			if(distanceMap[currentTestedPosition + width + 1] == -1) {
				this.distanceMap[currentTestedPosition + width + 1] = currentDistance;
				boxesQueue.addLast(currentTestedPosition + width + 1);
			}
		}
	}
	
	//test d'affichage
	public void displayDistanceMap(){
		for(int i=0; i<this.distanceMap.length; i++){
			System.out.println(this.distanceMap[i]);
		}

	}
	
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
