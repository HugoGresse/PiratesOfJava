package fr.imac.javawars.engine;

public class Engine {

	/**
	 * Variables
	 * - Map
	 * - Distances de d�placement
	 * - Liste des tours
	 * - Liste des bases
	 * - Liste des joueurs
	 * 
	 * Class dans "Engine"
	 * - Tours/Bases/Agents
	 * - Information du joueur
	 * 
	 */

	public Engine() {
		
		
	}

	//engine manages the bases
	private BasesManager basesManager;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("On va faire un super projet !!");
		
	}
	
	/**
	 *	Initialize the game
	 */
	public void initializationOfTheGame(){
		// give to basesManager the list of the bases (ArrayList), determine with the file ?
		//this.basesManager.setBases(bases);
		this.basesManager.determineInfluenceAreaOfBases();
	}
}
