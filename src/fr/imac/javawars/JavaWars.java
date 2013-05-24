package fr.imac.javawars;

import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.engine.Engine;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;

/**
 * Main class which create player, dispatcher and game. 
 * The ordre is also important !
 * @author Hugo
 *
 */
public class JavaWars {
	
	private static Dispatcher dispatcher; 
	private static Engine engine; 
	
	public static void main(String[] args){
		//creation of engine et dispatcher
		engine = new Engine();
		dispatcher = new Dispatcher();
		
		//creation of players and initialization in the engine
		Player joueur1 = new Human(1, "Hugo");
		Player joueur2 = new IA(2, "IA 1");
		Player joueur3 = new IA(3, "AI 2");
		Player joueur4 = new IA(4, "AI 3");
		
		//send the players to engine
		engine.initializationOfTheGame(joueur1, joueur2, joueur3, joueur4);
		
		//create ihm
		((Human)joueur1).initIhm("Pirates of Java");
		
		//Engine Thread start when click on Jouer
	}
	
	
	public static Dispatcher getDispatcher(){
		return dispatcher;
	}
	
	public static Engine getEngine(){
		return engine;
	}
	
}
