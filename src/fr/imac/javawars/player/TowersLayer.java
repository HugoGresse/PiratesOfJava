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
import fr.imac.javawars.engine.TowerBombe;
import fr.imac.javawars.engine.TowerBounce;
import fr.imac.javawars.engine.TowerFreeze;
import fr.imac.javawars.engine.TowerLaser;
import fr.imac.javawars.engine.TowerMachinegun;
import fr.imac.javawars.engine.TowerMissile;
import fr.imac.javawars.engine.TowerPoison;
import fr.imac.javawars.engine.TowerSniper;

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
	public void createTower(int x, int y, String type){	
		//TODO passer le type à l'action pour différencier les tours lors de la construction
		Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
		
		ActionTowerCreate myAction = null;
		
		if( type.equals("freeze")) {
			myAction = new ActionTowerCreate(player,new TowerFreeze(new Point(x, y), player));
		} else if(type.equals("laser")){
			myAction = new ActionTowerCreate(player,new TowerLaser(new Point(x, y), player));
		} else if(type.equals("missile")){
			myAction = new ActionTowerCreate(player,new TowerMissile(new Point(x, y), player));
		} else if(type.equals("machinegun")){
			myAction = new ActionTowerCreate(player,new TowerMachinegun(new Point(x, y), player));
		} else if(type.equals("bomb")){
			myAction = new ActionTowerCreate(player,new TowerBombe(new Point(x, y), player));
		} else if(type.equals("bounce")){
			myAction = new ActionTowerCreate(player,new TowerBounce(new Point(x, y), player));
		} else if(type.equals("sniper")){
			myAction = new ActionTowerCreate(player,new TowerSniper(new Point(x, y), player));
		} else if(type.equals("poison")){
			myAction = new ActionTowerCreate(player,new TowerPoison(new Point(x, y), player));
		} else {
			System.out.println("Erreur Grave - tour inconnue - TowersLayers. Action not created");
			return;
		}
		
		
		JavaWars.getDispatcher().addAction(myAction);
		
		repaint();
	}


}