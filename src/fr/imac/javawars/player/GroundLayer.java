package fr.imac.javawars.player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;


public class GroundLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image background;
	private Image wall;
	
	//constructor 
	GroundLayer(String background,String wall){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
		try {
			this.background = ImageIO.read(new File(background));
			this.wall = ImageIO.read(new File(wall));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// painting the map
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  

		//Antialiasing ON
		((Graphics2D)  g).setRenderingHint( 
				RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		        
		//Get the map with numbers
		int[][] map = JavaWars.getDispatcher().getGround();
			
		//Drawing background (equal to path)
		g.drawImage(background, 0, 0, 700, 500, null); 
		
		//Drawing wall (-2)
		for(int j=0; j<map[0].length;j++){
			for(int i=0; i<map.length; i++){
				if(map[i][j] == -2){
					g.drawImage(wall, j,i, 1, 1, null);
				}
			}
		}
	}

}