package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;

public class AgentsLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//constructor
	AgentsLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
	}

	//Painting layers
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  

	}

}