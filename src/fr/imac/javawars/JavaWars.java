package fr.imac.javawars;

import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.engine.Engine;
import fr.imac.javawars.player.Human;
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
	private static Human ihm;
	
	public static void main(String[] args){
		//creation of engine and dispatcher
		engine = new Engine();
		dispatcher = new Dispatcher();
		
		//creation of human player
		Player joueur1 = new Human(1, "Hugo");
		ihm = (Human)joueur1;
		
		//create ihm
		ihm.initIhm("Pirates of Java");
	}
	
	public static Dispatcher getDispatcher(){
		return dispatcher;
	}
	
	public static Engine getEngine(){
		return engine;
	}
	
	public static Human getHuman(){
		return ihm;
	}
	
}
