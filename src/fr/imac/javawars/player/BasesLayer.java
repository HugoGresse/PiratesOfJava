package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;

/**
 * Class BasesLayer: manage & display bases
 *  
 */
public class BasesLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	BasesLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
	}


	/**
	 * Painting layer (display bases)
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  

		//getting bases from engine
		CopyOnWriteArrayList<Base> bases = JavaWars.getDispatcher().getBases();
		Iterator<Base> it = bases.iterator();
		
		while(it.hasNext()){
			
			Base b = it.next();
			
			int radius = b.getRadius();
			int playerNumber = b.getPlayer().getPlayerNumber();
			
			//first circle : under (border)
			//neutral bases
			if(b.getPlayer()== null){
				g.setColor(new Color(198, 198, 198));
			}
			
			//players bases
			switch(playerNumber){
				case 1 : 
					g.setColor(new Color(197, 54, 59));
					break;
				case 2 :
					g.setColor(new Color(27, 116, 169));
					break;
				case 3:
					g.setColor(new Color(27, 116, 169));
					break;
				case 4 :
					g.setColor(new Color(27, 116, 169));
					break;
				default:
					g.setColor(new Color(27, 116, 169));
					break;
			}
			
			g.fillOval((int)b.getPosition().getX()- radius, (int)b.getPosition().getY()-radius, radius*2, radius*2);
			
			//second circle : above
			if(b.getPlayer()== null){
				g.setColor(new Color(229, 229, 229));
			}
			else if(playerNumber == 1){
				g.setColor(new Color(158, 41, 45));
			}
			else{
				g.setColor(new Color(23, 100, 145));
			}
			
			g.fillOval( (int)(b.getPosition().getX()- radius*1.5/2), (int)(b.getPosition().getY()-radius*1.5/2), (int)(radius*1.5), (int)(radius*1.5));
			
			//number of alien
			g.setColor(Color.black);
			g.drawString(String.valueOf(b.getLife()), (int)(b.getPosition().getX()-6), (int)(b.getPosition().getY()+4));
		}
		
	}

}