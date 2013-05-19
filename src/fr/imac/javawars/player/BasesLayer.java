package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;

public class BasesLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//constructor
	BasesLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
		addListener();
	}
	
	
	//add listener on Bases (rem : call this method whenever the bases list changes)
	void addListener(){
		//getting bases from engine
		final CopyOnWriteArrayList<Base> bases = JavaWars.getDispatcher().getBases();
		
		this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	//initialize bases list
        		CopyOnWriteArrayList<Base> tmpBases = bases;
        		final Iterator<Base> it = tmpBases.iterator();
        		//iterate on bases list
            	while(it.hasNext()){
            		Base b = it.next();
            		int radius = b.getRadius();
            		
            		//create an oval and test if the click of the mouse is in it.
            		Ellipse2D oval = new Ellipse2D.Double((int)b.getPosition().getX()- radius, (int)b.getPosition().getY()-radius, radius*2, radius*2);
    				if ((e.getButton() == 1) && oval.contains(e.getX(), e.getY()) ) {
    					System.out.println("base clicked!");
    				}
            	}
            }
		});
	}

	//Painting layers
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  

		//getting bases from engine
		CopyOnWriteArrayList<Base> bases = JavaWars.getDispatcher().getBases();
		Iterator<Base> it = bases.iterator();
		
		while(it.hasNext()){
			
			Base b = it.next();
			
			int radius = b.getRadius();
			
			//first circle : under (border)
			if(b.getPlayer()== null){
				g.setColor(new Color(198, 198, 198));
			}
			else if(b.getPlayer().getPlayerNumber() == 1){
				g.setColor(new Color(197, 54, 59));
			}
			else{
				g.setColor(new Color(27, 116, 169));		
			}
			
			g.fillOval((int)b.getPosition().getX()- radius, (int)b.getPosition().getY()-radius, radius*2, radius*2);
			
			//second circle : above
			if(b.getPlayer()== null){
				g.setColor(new Color(229, 229, 229));
			}
			else if(b.getPlayer().getPlayerNumber() == 1){
				g.setColor(new Color(158, 41, 45));
			}
			else{
				g.setColor(new Color(23, 100, 145));
			}
			
			g.fillOval( (int)(b.getPosition().getX()- radius*1.5/2), (int)(b.getPosition().getY()-radius*1.5/2), (int)(radius*1.5), (int)(radius*1.5));
		}
		
	}

}