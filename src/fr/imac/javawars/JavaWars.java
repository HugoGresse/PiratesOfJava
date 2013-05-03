package fr.imac.javawars;

import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.engine.Engine;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;


public class JavaWars {
	
	private static Dispatcher dispatcher; 
	private static Engine engine; 
	
	public static void main(String[] args){
		//Opening JavaWars
		System.out.println("Opening JavaWars");
		
		Player joueur1 = new Human(1, "Hugo");
		Player joueur2 = new IA(2, "IA 1");
		Player joueur3 = new IA(3, "AI 2");
		Player joueur4 = new IA(4, "AI 3");
		

		//une fois les joueurs crées, on créer le dispatcher en lui donnant les joueurs 
		dispatcher = new Dispatcher();

		engine = new Engine(joueur1, joueur2, joueur3, joueur4);
		

		//on met à jour les Players
		dispatcher.updatePlayers();
		
	}
	
	
	public static Dispatcher getDispatcher(){
		return dispatcher;
	}
	
	public static Engine getEngine(){
		return engine;
	}
	
	

	
}
