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
		//Opening JavaWars
		System.out.println("Opening JavaWars");
		
		//creation of engine et dispatcher
		engine = new Engine();
		dispatcher = new Dispatcher();
		
		//creation of players and initialization in the engine
		Player joueur1 = new Human(1, "Hugo", 197,54,59);
		Player joueur2 = new IA(2, "IA 1",23,100,145);
		Player joueur3 = new IA(3, "AI 2",131,195,25);
		Player joueur4 = new IA(4, "AI 3",255,136,0);
		
		//send the players to engine
		engine.initializationOfTheGame(joueur1, joueur2, joueur3, joueur4);
		
		//create ihm
		((Human)joueur1).initIhm("JavaWars");
		
		//on met � jour les Players
		dispatcher.updatePlayers();
		
		
	}
	
	
	public static Dispatcher getDispatcher(){
		return dispatcher;
	}
	
	public static Engine getEngine(){
		return engine;
	}
	
	

	
}
