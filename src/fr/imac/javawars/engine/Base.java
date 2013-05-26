package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import fr.imac.javawars.player.Player;
/**
 * <b> Base represents a military base </b>
 * 
 *
 */

public class Base extends AbstractTowerBase {
	private int capacity;
	private double speedRegeneration; //if = 1 means 1 agent produce by second ?
	private LinkedList<Tower> towers;
	private Power power;
	/* table containing distances of the base
 	imagine a map of boxes but here stocked in a tab 1d
 	every element represents the distance of the box to the base */
	private int[] distanceMap;
	/**
	 * Rayon de la base
	 */
	private int radius;
	public enum Power{
		NORMAL,
		SPEED_UP,
		LIFE_UP,
		RESISTANCE;
	}
	
	// CONSTRUCTORS

	public Base(int life, Point position,Player player, double actionField, int capacity, double speedRegeneration, int radius) {
		super(life, position, player, actionField);
		this.capacity = capacity;
		this.speedRegeneration = speedRegeneration;
		//this.towers = new LinkedList<Tower>();
		this.power = Power.NORMAL;
		this.radius = radius;
		
		//call autoAddLife for incrementing life of the bases 
		//autoAddLife();
	}
	
	//this constructor is used to generate Bases Neutral from the map
	public Base(int life, Point position, double speedRegeneration,  int radius){
		// by default capacity of 50 agents ?
		this(life, position, null, 0.0, 50, speedRegeneration, radius);
	}
	
	//this constructor is used to generate Bases Player from the map
	public Base(Point point, Player player, double speedRegeneration, int radius){
		this(50, point, player, 0.0, 50, speedRegeneration, radius);
	}

	public Base(Point position, Player player, int radius){
		this(50, position, player, 0.0, 50, 1, radius);
	}
	
	public Base(Point position){
		this(50, position, null, 0.0, 50, 1, 10);
	}
	
	public Base(){
		this(0, null, null, 0, 0, 0, 0);
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
	/*WARNING : bitMap[indexOfTheLine][indexOfTheColumn] = distanceMap[indexOfTheColumn + indexOfTheLine * width]*/
	/*WARNING : at the moment a base placed in (1,2) means she is in the first column and second line*/
	// in bitMap[i][j], i represents the number of lines, j the number of columns
	public void initializeDistanceMap(int[][] bitMap){
		if(bitMap.length <= 0){
			return;
		}
		int height = bitMap.length;
		int width = bitMap[0].length;
		this.distanceMap = new int[height * width];
		//we initialize all boxes
		for(int i = 0; i < height; ++i){
			for(int j = 0; j < width; ++j){
				if(bitMap[i][j] == -2){
					// we initialize unaccessible zone for agents to a distance of -2, for the moment a wall is representing by a 1 in the map
					this.distanceMap[j + i * width] = - 2;
				}
				else{
					// we initialize other to a distance of -1 meaning the distance as not been calculated yet
					this.distanceMap[j + i * width] = - 1;
				}
			}
		}
		// we initialize also the outline of the map with -2 
		// it's a trick to avoid an unwanted access later
		//the line at the top of the map
		for(int j = 0; j < width ; ++j){
			this.distanceMap[j] = -2;
		}
		//the line at the left of the map
		for(int i = 1; i < height; ++i){
			this.distanceMap[i*width] = -2;
		}
		//the line at the right of the map
		for(int i = 1; i <= height; ++i){
			this.distanceMap[i*width - 1] = -2;
		}
		//the line at the bottom of the map
		for(int j = (height * width) - width; j < (height * width) - 1 ; ++j){
			this.distanceMap[j] = -2;
		}

		//the box corresponding to the position of the base is at 0 of distance
		//WARNING : X OR Y X associated to a line, and Y to a column
		this.distanceMap[(int) (this.getPosition().getX() + this.getPosition().getY() * width)] = 0;
		
		//debug
		//writeInXMLInfluenceMap(bitMap, distanceMap, "map/initializeDistanceBaseTest");
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
	public void computeDistanceMap(int[][] bitMap){
		if(bitMap.length <= 0){
			System.out.println("problem with the bitmap in computeDistanceMap");
			return;
		}
		int width = bitMap[0].length;
		//creation of a queue of the boxes , for the moment a LinkedList
		LinkedList<Integer> boxesQueue = new LinkedList<Integer>();
		// use addLast to add the element at the end of the list
		// currentTestedPosition corresponding to the box whose neighbors boxes are tested
		// starting point is the box corresponding to the position of the base
		int currentTestedPosition = (int) (this.getPosition().getX() + this.getPosition().getY() * width);
		// currentDistance represents the current distance to the base
		boxesQueue.addLast(currentTestedPosition);
		// while the queue isn't empty
		while (!boxesQueue.isEmpty()){
			//test every neighbor boxes of the tested box, here we are testing diagonals too
			// if the value of the distance is -1, so we change it, if it's an other number, it means agents are not authorize to go there and so we don't change
			// the value to keep it negative, which will help us when we will move agents
			// use removeFirst to take the first element of the queue which retrieves and removes the first element of this list
			// deleting the first box of the queue for testing his neighbors
			currentTestedPosition = boxesQueue.removeFirst();
			// test left box
			if(this.distanceMap[currentTestedPosition - 1] == -1) {
				//giving the distance value to the base for the box
				this.distanceMap[currentTestedPosition - 1] = this.distanceMap[currentTestedPosition] + 1;
				// adding the box to the queue to test his neighbors after
				boxesQueue.addLast(currentTestedPosition - 1);
			}
			// test right box
			if(this.distanceMap[currentTestedPosition + 1] == -1) {
				this.distanceMap[currentTestedPosition + 1] = this.distanceMap[currentTestedPosition] + 1;
				boxesQueue.addLast(currentTestedPosition + 1);
			}
			// test above box
			if(this.distanceMap[currentTestedPosition - width] == -1) {
				this.distanceMap[currentTestedPosition - width] = this.distanceMap[currentTestedPosition] + 1;
				boxesQueue.addLast(currentTestedPosition - width);
			}
			// test below box
			if(this.distanceMap[currentTestedPosition + width] == -1) {
				this.distanceMap[currentTestedPosition + width] = this.distanceMap[currentTestedPosition] + 1;
				boxesQueue.addLast(currentTestedPosition + width);
			}
			// test above left box
			if(this.distanceMap[currentTestedPosition - width -1] == -1) {
				this.distanceMap[currentTestedPosition - width -1] = this.distanceMap[currentTestedPosition] + 1;
				boxesQueue.addLast(currentTestedPosition - width -1);
			}
			// test above right box
			if(this.distanceMap[currentTestedPosition - width + 1] == -1) {
				this.distanceMap[currentTestedPosition - width + 1] = this.distanceMap[currentTestedPosition] + 1;
				boxesQueue.addLast(currentTestedPosition - width + 1);
			}
			// test below left box
			if(this.distanceMap[currentTestedPosition + width - 1] == -1) {
				this.distanceMap[currentTestedPosition + width - 1] = this.distanceMap[currentTestedPosition] + 1;
				boxesQueue.addLast(currentTestedPosition + width - 1);
			}
			// test below right box
			if(this.distanceMap[currentTestedPosition + width + 1] == -1) {
				this.distanceMap[currentTestedPosition + width + 1] = this.distanceMap[currentTestedPosition] + 1;
				boxesQueue.addLast(currentTestedPosition + width + 1);
			}
		}
		//writeInXMLInfluenceMap(bitMap, distanceMap, "map/distanceBaseTest");
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
	
	/*public void addTower(Tower t){
		towers.add(t);
	}
	
	public void deleteTower(Tower t){
		// A REMPLIR
	}*/
	
	//GETTERS-SETTERS
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getSpeedRegeneration() {
		return speedRegeneration;
	}

	public void setSpeedRegeneration(int speedRegeneration) {
		this.speedRegeneration = speedRegeneration;
	}

	/*public LinkedList<Tower> getTowers() {
		return towers;
	}

	public void setTowers(LinkedList<Tower> towers) {
		this.towers = towers;
	}*/

	public Power getPower() {
		return power;
	}

	public void setPower(Power power) {
		this.power = power;
	}
	
	public int getRadius(){
		return this.radius;
	}
	

	public int[] getDistanceMap() {
		return distanceMap;
	}

	public void setDistanceMap(int[] distanceMap) {
		this.distanceMap = distanceMap;
	}

	/**
	 * Write in a file under the form of a tab2D of numbers the map of distances  
	 * 
	 * @param bitMap
	 * 		the map which generates our ground
	 * @param distanceMap
	 * 		the map representing the distance to the base
	 * @param nameFile
	 * 		the name we want to give to our file
	 */
	//used for debug
	private void writeInXMLInfluenceMap(int[][] bitMap, int[] distanceMap, String nameFile){
		int[][] bitMapInfluenceArea = bitMap;
		for(int i = 0; i < bitMapInfluenceArea.length; ++i){
			for(int j = 0; j < bitMapInfluenceArea[0].length; ++j){
				bitMapInfluenceArea[i][j] = distanceMap[j + i * bitMapInfluenceArea[0].length];
			}
		}
		Ground.saveAsXML(bitMapInfluenceArea, nameFile);
	}
	
}
