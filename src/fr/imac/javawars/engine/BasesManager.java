package fr.imac.javawars.engine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.Action;

/**
 * <b>BasesManager is a class which manage bases.</b>
 * <p>
 * BasesManager contains :
 * <ul>
 * <li> The list of the bases present on the map </li>
 * <li> A table of integer determined with the game map describing the influence area of the bases </li>
 * </ul>
 * </p>
 * 
 * @see Base
 * 
 * @author TOURNERET
 */

public class BasesManager {
	/**
	 * Table associating a box of the map to a base thanks to a integer
	 * 
	 * @see BasesManager#determineInfluenceAreaOfBases()
	 */
	private int[] influenceAreaMap;
	
	private ArrayList<Point> influenceJ2;
	private ArrayList<Point> influenceJ3;
	private ArrayList<Point> influenceJ4;
	/**
	 * BasesManager constructor
	 * <p>
	 * BasesManager is constructed from the bases present on the map
	 * </p>
	 * 
	 * @param bases
	 * 		list of the bases present on the map
	 * 
	 * @see BasesManager#bases
	 */
	public BasesManager(int[][] bitMap) {
		this.initialiseInfluenceAreaMap(bitMap);
		this.determineInfluenceAreaOfBases(bitMap);
		this.influenceJ2 = new ArrayList<Point>();
		this.influenceJ3 = new ArrayList<Point>();
		this.influenceJ4 = new ArrayList<Point>();
	}
	
	private void initialiseInfluenceAreaMap(int[][] bitMap) {
		if(bitMap.length <= 0){
			System.out.println("problem with the bitmap in initializeInfluenceAreaOfBases");
			return;
		}
		int height = bitMap.length;
		int width = bitMap[0].length;
		this.influenceAreaMap= new int[height * width];
		// first we initialize every box to -1 (belongs to no bases)
		for(int i=0; i < height * width; i++ ){
			this.influenceAreaMap[i] = -1;
		}
		// we initialize the outline of the map with -2 
		// it's a trick to avoid an unwanted access later .
		//the line at the top of the map
		for(int i = 0; i < width ; ++i){
			this.influenceAreaMap[i] = -2;
		}
		//the line at the left of the map
		for(int i = 1; i < height; ++i){
			this.influenceAreaMap[i*width] = -2;
		}
		//the line at the right of the map
		for(int i = 1; i <= height; ++i){
			this.influenceAreaMap[i*width - 1] = -2;
		}
		//the line at the bottom of the map
		for(int i = (height * width) - width; i < (height * width) - 1 ; ++i){
			this.influenceAreaMap[i] = -2;
		}
		
		// we initialize the box which corresponds to a base position with the number of the player possessing the base
		Iterator<Base> itBases = JavaWars.getEngine().getBases().iterator();
		while(itBases.hasNext()){
			Base b = itBases.next();
			int basePosition1D = (int) (b.getPosition().getX() + b.getPosition().getY() * width);
			//if the base is neutral
			if(b.getPlayer() == null){
				this.influenceAreaMap[basePosition1D] = 0;
			}
			//if the base isn't neutral
			else {
				this.influenceAreaMap[basePosition1D] = b.getPlayer().getPlayerNumber();
				System.out.println("player number " + b.getPlayer().getPlayerNumber());
			}
		}
		//debug, create a xml map in the same format that the original to see if the initialization works
		//writeInXMLInfluenceMap(bitMap, this.influenceAreaMap, "map/influenceAreaOfBasesInitialized");
	}
		
	/**
	 * Compute the table representing the influence area of every base according to every base position
	 * 
	 * @see BasesManager#influenceAreaMap
	 */
	public void determineInfluenceAreaOfBases(int[][] bitMap) {
		if(JavaWars.getEngine().getBases().isEmpty()){
			System.out.println("list of bases is empty, can't calculate area influence");
			return;
		}
		//initialization of the queue of the boxes (designed by a number calculated with their coordinates) (i,j) becomes in one dimension i + j*width
		LinkedList<Integer> boxesQueue = new LinkedList<Integer>();
		int width = bitMap[0].length;
		int basePosition1D = 0;
		
		//we add all bases positions to the queue
		Iterator<Base> itBases = JavaWars.getEngine().getBases().iterator();
		while(itBases.hasNext()){
			Base b = itBases.next();
			//cast from double to integer, basePosition corresponds to an index int the tab1D
			basePosition1D = (int) (b.getPosition().getX() + b.getPosition().getY() * width);
			boxesQueue.addLast(basePosition1D);
		}
		
		if(boxesQueue.isEmpty()){
			return;
		}
		//initialize variables for the loop
		int currentBoxTested = 0;
		int testNotToManyLoop = 0;
		// while the queue is not empty and not to many loop (uncontrolled), limit is 2 073 600 (1920 * 1080) pixels visited
		while(!boxesQueue.isEmpty() && testNotToManyLoop < 2073600 ) {
			//we take one box of the queue and test all his neighbors
			currentBoxTested = boxesQueue.removeFirst();
			// test left box
			if(this.influenceAreaMap[currentBoxTested - 1] == -1) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested - 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested - 1);
			}
			// test right box
			if(this.influenceAreaMap[currentBoxTested + 1] == -1) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested + 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested + 1);
			}
			// test above box
			if(this.influenceAreaMap[currentBoxTested - width] == -1) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested - width] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested - width);
			}
			// test below box
			if(this.influenceAreaMap[currentBoxTested + width] == -1) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested + width] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested + width);
			}
			// test above left box
			if(this.influenceAreaMap[currentBoxTested - width - 1] == -1) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested - width - 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested - width - 1);
			}
			// test above right box
			if(this.influenceAreaMap[currentBoxTested - width + 1] == -1) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested - width + 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested - width + 1);
			}
			// test below left box
			if(this.influenceAreaMap[currentBoxTested + width - 1] == -1) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested + width - 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested + width - 1);
			}
			// test below right box
			if(this.influenceAreaMap[currentBoxTested + width + 1] == -1) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested + width + 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested + width + 1);
			}
			testNotToManyLoop++;
			if(testNotToManyLoop >= 2073600)
				System.out.println("boucle infinie dans determineInfluenceBase");
		}
		System.out.println("determine influence of bases finished !");
		//debug, create a xml map in the same format that the original to see if the algorithm works
		//writeInXMLInfluenceMap(bitMap, this.influenceAreaMap, "map/influenceAreaBasesCalculated");
	}

	/**
	 * Returns the map of area influence of bases
	 * 
	 * @return 
	 * 		the map of area influence of bases
	 */
	public int[] getInfluenceAreaMap() {
		return influenceAreaMap;
	}
	
	public int[][] getInfluenceAreaBitMap() {
		int[][] bitMapInfluenceArea = new int[Ground.getWinHeight()][Ground.getWinWidth()];
		for(int i = 0; i < bitMapInfluenceArea.length; ++i){
			for(int j = 0; j < bitMapInfluenceArea[0].length; ++j){
				bitMapInfluenceArea[i][j] = influenceAreaMap[j + i * bitMapInfluenceArea[0].length];
			}
		}
		return bitMapInfluenceArea;
	}
	
	public int getPositionInfluenceArea(int x, int y){
		int bitMap[][] = this.getInfluenceAreaBitMap();
		return bitMap[x][y];
	}

	/**
	 * Write in a file under the form of a tab2D of numbers the areas of influence of each base
	 * 
	 * @param bitMap
	 * 		the map which generates our ground
	 * @param influenceAreaMap
	 * 		the map representing the influence of every base on the ground
	 * @param nameFile
	 * 		the name we want to give to our file
	 */
	//used for debug
	private void writeInXMLInfluenceMap(int[][] bitMap, int[] influenceAreaMap, String nameFile){
		int[][] bitMapInfluenceArea = bitMap;
		for(int i = 0; i < bitMapInfluenceArea.length; ++i){
			for(int j = 0; j < bitMapInfluenceArea[0].length; ++j){
				bitMapInfluenceArea[i][j] = influenceAreaMap[j + i * bitMapInfluenceArea[0].length];
			}
		}
		Ground.saveAsXML(bitMapInfluenceArea, nameFile);
	}
	
	/**
	 * Create List for IA 2, 3 and 4 with point who can create a tower ( used for action of the IA)
	 */
	public void createInfluenceList(){
		int[][] bitMap = this.getInfluenceAreaBitMap();
		
		for(int i = 13; i < bitMap.length-13; ++i){
			for(int j = 13; j < bitMap[0].length-13; ++j){
				
				int currentBitMap = bitMap[i][j];
				
				// If its borders of map or influence of neutral bases
				if (currentBitMap == -2 || currentBitMap == 0)
					continue;
				
				// If no enought space to make a tower
				if (!JavaWars.getEngine().getGround().checkSpaceTower(j, i))
					continue;
				
				Point influencePoint = new Point(j, i);
				
				// Not do this for player 1 because its the human
				
				// IA 2
				if (currentBitMap == 2){
					influenceJ2.add(influencePoint);
					continue;
				}
				
				// IA 3
				if (currentBitMap == 3){
					influenceJ3.add(influencePoint);
					continue;
				}
				
				//IA 4
				if (currentBitMap == 4){
					influenceJ4.add(influencePoint);
					continue;
				}
			}
			
		}
			
	}

	public ArrayList<Point> getInfluenceJ2() {
		return influenceJ2;
	}

	public ArrayList<Point> getInfluenceJ3() {
		return influenceJ3;
	}

	public ArrayList<Point> getInfluenceJ4() {
		return influenceJ4;
	}
	
	
}
