package fr.imac.javawars.player;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;

public class Layer extends JPanel {
	private static final long serialVersionUID = 1L;
	private String type;
	
	Layer(String type){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
		this.type = type;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
		if(this.type == "map"){
			int[][] map = JavaWars.getDispatcher().getGround();
			for(int j=0; j<map[0].length;j++){
				for(int i=0; i<map.length; i++){
					if(map[i][j] == -1){
						g.setColor(Color.GRAY); 
						g.fillRect(j,i,1,1);
					}
					else if(map[i][j] == -2){
						g.setColor(Color.GREEN); 
						g.fillRect(j,i,1,1);
					}
				}
			}
		}
		
		if(this.type == "bases"){
			g.setColor(Color.black);
			
			
			ArrayList<Base> bases = JavaWars.getDispatcher().getBases();
			for(Base b:bases){
				if(b.getPlayer()== null){
					g.setColor(Color.white);
				}
				else{
					switch(b.getPlayer().getPlayerNumber()){
						case 1:
							g.setColor(Color.cyan);
							break;
						case 2:
							g.setColor(Color.orange);
							break;
						case 3:
							g.setColor(Color.red);
							break;
						case 4:
							g.setColor(Color.blue);
							break;
					}
				}
				g.fillOval((int)b.getPosition().getX()- b.getRadius(), (int)b.getPosition().getY()-b.getRadius(), b.getRadius()*2, b.getRadius()*2);
			}
		}
		
		if(this.type == "towers"){
			
		}
		
		if(this.type == "agents"){
			
		}	
	}

}