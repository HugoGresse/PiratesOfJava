package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.engine.Tower;

/**
 * Class TowersLayer: manage & display towers 
 */
public class TowersLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	TowersLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);	
	}

	/**
	 * Painting layer (display towers)
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//getting bases from engine
		CopyOnWriteArrayList<Tower> towers = JavaWars.getDispatcher().getTowers();
		Iterator<Tower> it = towers.iterator();
		
		g.setColor(new Color(0,0,0));
		
		while(it.hasNext()){
			Tower t = it.next();
			g.fillRect((int)(t.getPosition().getX()-7.5), (int)(t.getPosition().getY()-7.5),15,15);
			
		}
	}
	
	/**
	 * Create a tower/send info to dispatcher
	 */
	public void createTower(int x, int y){
		System.out.println("Action with new tower created");
		
		Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
		Point positionTower = new Point(x,y);
		Tower newTower = new Tower(10, positionTower, player , 20, 2, 5);
		ActionTowerCreate myAction = new ActionTowerCreate( player, newTower);
		JavaWars.getDispatcher().addAction(myAction);
		
		repaint();
	}


}