package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;

public class AgentDisplay extends JPanel{

	private static final long serialVersionUID = 1L;
	private Point position;
	
	AgentDisplay(Point position){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
		this.position = position;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		drawAgent(g);
	}
	
	public void drawAgent(Graphics g){
		int x = (int) this.position.getX();
		int y = (int) this.position.getY();
		g.setColor(new Color(255, 0, 0));
		g.fillOval(x, y, 15, 15);
	}

}
