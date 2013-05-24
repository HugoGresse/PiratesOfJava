package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;

public class AgentDisplay extends JPanel{
	private static final long serialVersionUID = 1L;
	private Point position;
	private int player;
	private Image image;
	
	AgentDisplay(Point position, int player){
		super();
		this.position = position;
		this.player = player;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		drawAgent(g);
	}
	
	public void drawAgent(Graphics g){
		int x = (int) this.position.getX();
		int y = (int) this.position.getY();

		String nameImage = null;
		switch(player){
			case 1 :
				nameImage = "res/img/bateauRg.png";
				break;
			case 2 :
				nameImage = "res/img/bateauBl.png";
				break;
			case 3:
				nameImage = "res/img/bateauVt.png";
				break;
			case 4:
				nameImage = "res/img/bateauOr.png";
				break;
		}
		
		try {
			image = ImageIO.read(new File(nameImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image, x - 9/2 , y - 11/2 , 9, 11,null);
	}

}
