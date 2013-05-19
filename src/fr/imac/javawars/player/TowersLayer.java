package fr.imac.javawars.player;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.engine.Base;
import fr.imac.javawars.engine.Tower;

public class TowersLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//constructor
	TowersLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);

	}

	//Painting layers
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
	}
	
	public void createTower(int x, int y){
		System.out.println("Action with new tower created");
		
		Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
		Point positionTower = new Point(x,y);
		Tower newTower = new Tower(10, positionTower, player , 20, 20, 5);
		ActionTowerCreate myAction = new ActionTowerCreate( player, newTower);
		JavaWars.getDispatcher().addAction(myAction);
		
		// Je suis pas sur que ce soit utile de faire un repaint la, car la tour n'est pas encore enregistré par l'Engine 
		repaint();
	}


}