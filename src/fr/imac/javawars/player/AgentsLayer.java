package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Class AgentsLayer: manage & display agents 
 */
public class AgentsLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	AgentsLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
	}

	/**
	 * Painting layer (display agents)
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
	}

}