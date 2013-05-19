package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
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
	}

	//Painting layers
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  

		//getting bases from engine
		ArrayList<Base> bases = JavaWars.getDispatcher().getBases();
		for(Base b:bases){
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
			
			g.fillOval((int)(b.getPosition().getX()- radius*1.5/2), (int)(b.getPosition().getY()-radius*1.5/2), (int)(radius*1.5), (int)(radius*1.5));
		}
		
	}

}