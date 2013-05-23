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
		this.position = position;
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		g.setColor(Color.white);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
		drawAgent(g);
	}
	
	public void drawAgent(Graphics g){
		int x = (int) this.position.getX();
		int y = (int) this.position.getY();
		g.setColor(Color.red);
		g.fillOval(x, y, 15, 15);
	}

}
