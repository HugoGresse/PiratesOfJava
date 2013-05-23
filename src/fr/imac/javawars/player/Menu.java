package fr.imac.javawars.player;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;

/**
 * Class Menu : display menu
 *  
 */
public class Menu extends JPanel {
	Image backgroundMenu;
	Rectangle2D boutonPlay;
	
	/**
	 * CONSTRUCTOR
	 */
	Menu(){
		super();
		this.setBounds(0,0,900,600);
		this.setOpaque(false);
		try {
			backgroundMenu = ImageIO.read(new File("res/img/menu.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boutonPlay = new Rectangle2D.Double(650,0,250,115);
		
		addBoutonListener();
	}
	
	/**
	 * Adding listener on bouton, if pressed : game begins
	 */
	public void addBoutonListener(){
		this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if ((e.getButton() == 1) && boutonPlay.contains(e.getX(), e.getY()) ) {
            		
            		Human player =(Human)JavaWars.getEngine().getPlayers().get(1);
            		//display game
            		player.getIhm().CreatePanel();
            		//Auto-update bases life
            		player.getIhm().getCenterPanel().getBasesLayer().autoUpdate();
            		JavaWars.getDispatcher().setAutoAddLife();
            	}
            }
		});
	}
	
	/**
	 * Paint layer
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(backgroundMenu, 0, 0, 900, 600, null);	
	}
}
