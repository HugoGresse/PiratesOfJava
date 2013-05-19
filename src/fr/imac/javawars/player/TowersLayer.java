package fr.imac.javawars.player;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;

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
	
	public void createTower(){
		System.out.println("click");
		repaint();
	}


}