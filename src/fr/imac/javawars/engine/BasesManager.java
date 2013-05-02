package fr.imac.javawars.engine;

import java.util.ArrayList;
import java.util.LinkedList;

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
	 * List of the bases present on the map
	 * 
	 * @see BasesManager#BasesManager(ArrayList)
	 */
	private ArrayList<Base> bases;
	
	/**
	 * Table associating a box of the map to a base thanks to a integer
	 * 
	 * @see BasesManager#determineInfluenceAreaOfBases()
	 */
	private int[] influenceAreaMap;
	
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
	public BasesManager(ArrayList<Base> bases, int[][] bitMap) {
		this.bases = bases;
		this.initialiseInfluenceAreaMap(bitMap);
		this.determineInfluenceAreaOfBases(bitMap);
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
		int k = 0;
		for(Base b: bases){
			int basePosition1D = (int) (this.bases.get(k).getPosition().getX() + this.bases.get(k).getPosition().getY() * width);
			this.influenceAreaMap[basePosition1D] = b.getPlayer().getPlayerNumber();
			k++;
			//debug
			System.out.println("basePosition :" + basePosition1D);
			System.out.println("player Number :" + this.influenceAreaMap[basePosition1D]);	
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
		if(bitMap.length <= 0){
			System.out.println("problem with the bitmap in determineInfluenceAreaOfBases");
			return;
		}
		if(this.bases.isEmpty()){
			System.out.println("list of bases is empty, can't calculate area influence");
			return;
		}
		//initialization of the queue of the boxes (designed by a number calculated with their coordinates) (i,j) becomes in one dimension i + j*width
		LinkedList<Integer> boxesQueue = new LinkedList<Integer>();
		int width = bitMap.length;
		int basePosition1D = 0;
		//we add all bases positions to the queue
		for (Base b : this.bases){
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
		while(!boxesQueue.isEmpty() && testNotToManyLoop < 2073600){
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
		}
		//debug, create a xml map in the same format that the original to see if the algorithm works
		//writeInXMLInfluenceMap(bitMap, this.influenceAreaMap, "map/influenceAreaOfBasesCalculated");
	}

	/**
	 * Update the list of the bases
	 * 
	 * @param bases
	 * 		list of bases present on the map
	 * 
	 * @see Base
	 */
	public void setBases(ArrayList<Base> bases) {
		this.bases = bases;
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
				bitMapInfluenceArea[i][j] = influenceAreaMap[i + j * bitMapInfluenceArea[0].length];
			}
		}
		Ground.saveAsXML(bitMapInfluenceArea, nameFile);
	}
	
}
