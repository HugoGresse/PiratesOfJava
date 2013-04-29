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
	public BasesManager(ArrayList<Base> bases) {
		this.bases = bases;
		this.determineInfluenceAreaOfBases();
		//NEED TO INITIALIZE THE MAP WITH A VALUE DIFFERENT FOR EVERY BASE
	}
	
	public void initializeInfluenceAreaOfBases(int height, int width){
		// CHANGE WITH REAL VALUES
		//initialization should be done with a file (-2 for walls, -1 when not decided)
		this.influenceAreaMap= new int[height*width];
		// first we initialize every box to -1 (belongs to no bases
		for(int i=0; i < height * width; i++ ){
			this.influenceAreaMap[i] = -1;
		}
		// we initialize the box which corresponds to a base position with the number of the player possessing the base
		for(Base b: bases){
			int basePosition = (int) (this.bases.get(0).getPosition().getX() + this.bases.get(0).getPosition().getY());
			this.influenceAreaMap[basePosition] = b.getPlayer().getIdentifier();
		}
	}
	
	/**
	 * Compute the table representing the influence area of every base according to every base position
	 * 
	 * @see BasesManager#influenceAreaMap
	 */
	public void determineInfluenceAreaOfBases() {
		//initialization of the queue of the boxes (designed by a number calculated with their coordinates) (i,j) becomes in one dimension i + j*width
		LinkedList<Integer> boxesQueue = new LinkedList<Integer>();
		//SHOULD CHANGE
		int widthOfTheMap = 4;
		// this expression corresponds to i + j, initialization with the box corresponding to the first base
		int currentBoxTested = (int) (this.bases.get(0).getPosition().getX() + this.bases.get(0).getPosition().getY()) * widthOfTheMap;
		
		//we add all bases to the queue
		for (Base b : this.bases){
			//cast from double to integer
			boxesQueue.addLast((int) (b.getPosition().getX() + b.getPosition().getY() * widthOfTheMap));
		}
		// while the queue is not empty
		while(!boxesQueue.isEmpty()){
			currentBoxTested = boxesQueue.removeFirst();
			// test <0 and not == -1 because area influence take walls in it
			// test left box
			if(this.influenceAreaMap[currentBoxTested - 1] < 0) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested - 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested - 1);
			}
			// test right box
			if(this.influenceAreaMap[currentBoxTested + 1] < 0) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested + 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested + 1);
			}
			// test above box
			if(this.influenceAreaMap[currentBoxTested - widthOfTheMap] < 0) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested - widthOfTheMap] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested - widthOfTheMap);
			}
			// test below box
			if(this.influenceAreaMap[currentBoxTested + widthOfTheMap] < 0) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested + widthOfTheMap] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested + widthOfTheMap);
			}
			// test above left box
			if(this.influenceAreaMap[currentBoxTested - widthOfTheMap - 1] < 0) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested - widthOfTheMap - 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested - widthOfTheMap - 1);
			}
			// test above right box
			if(this.influenceAreaMap[currentBoxTested - widthOfTheMap + 1] < 0) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested - widthOfTheMap + 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested - widthOfTheMap + 1);
			}
			// test below left box
			if(this.influenceAreaMap[currentBoxTested + widthOfTheMap - 1] < 0) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested + widthOfTheMap - 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested + widthOfTheMap - 1);
			}
			// test below right box
			if(this.influenceAreaMap[currentBoxTested + widthOfTheMap + 1] < 0) {
				// if the box tested has a neighbor which doesn't belong to another base, we associate the neighbor box to the same base that the box tested
				this.influenceAreaMap[currentBoxTested + widthOfTheMap + 1] = influenceAreaMap[currentBoxTested];
				boxesQueue.addLast(currentBoxTested + widthOfTheMap + 1);
			}
		}
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

}
