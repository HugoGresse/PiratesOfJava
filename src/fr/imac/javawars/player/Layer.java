package fr.imac.javawars.player;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;

public class Layer extends JPanel{
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
					
					int lar = 700/map[0].length;
					int haut = 500/map.length;
					
					if(map[i][j] == 0){
						g.setColor(Color.GRAY); 
						g.fillRect(j*lar,i*haut,lar,haut);
					}
					else if(map[i][j] == 1){
						g.setColor(Color.GREEN); 
						g.fillRect(j*lar,i*haut,lar,haut);
					}
				}
			}
		}
		
		if(this.type == "bases"){
			g.setColor(Color.black);
			g.fillOval(650, 350, 25, 25);
			// TODO : Parcourir la map, mettre couleur quand chiffre autre que 0/1
		}
		
		if(this.type == "towers"){
			g.setColor(Color.white);
			g.fillOval(650, 340, 20, 20);
			// TODO : Parcourir liste des tours construites pour chaque base et les afficher
		}
		
		if(this.type == "agents"){
			g.setColor(Color.yellow);
			g.fillOval(550, 340, 20, 20);
			// TODO : Parcourir liste des agents et les afficher
		}	
	}

}