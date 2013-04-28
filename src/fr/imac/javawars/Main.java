package fr.imac.javawars;

import fr.imac.javawars.dispatcher.Dispatcher;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.IA;
import fr.imac.javawars.player.Player;


public class Main {
	
	public static void main(String[] args){
		//Opening JavaWars
		System.out.println("Opening JavaWars");
		
		Player joueur1 = new Human("Hugo");
		Player joueur2 = new IA("IA 1");
		Player joueur3 = new IA("AI 2");
		Player joueur4 = new IA("AI 3");
		
		
		
		//une fois les joueurs crées, on créer le dispatcher en lui donnant les joueurs 
		Dispatcher dispacher = new Dispatcher(joueur1, joueur2, joueur3, joueur4);
		
	}
	

	
}
