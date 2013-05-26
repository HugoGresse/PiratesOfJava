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
	Rectangle2D boutonReplay;
	
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
		boutonReplay = new Rectangle2D.Double(255,385,350,100);
		
		addPlayListener();
	}
	
	/**
	 * Adding listener on playBouton, if pressed : game begins
	 */
	public void addPlayListener(){
		this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if ((e.getButton() == 1) && boutonPlay.contains(e.getX(), e.getY()) ) {
            		
            		Human player =(Human)JavaWars.getEngine().getPlayers().get(1);
            		//display game
            		player.getIhm().CreatePanel();
            		
            		// Starting engine
            		JavaWars.getEngine().startThread();
            	}
            }
		});
	}
	
	
	/**
	 * setting background for the end of the game
	 * @param gagne : boolean true = win/false = loose
	 */
	public void setBackgroundEnd(boolean gagne){
		//resetting btn with new coordinates
		boutonPlay = null;
		Human player =(Human)JavaWars.getEngine().getPlayers().get(1);
		
		//erasing game's layers
		//works
		player.getIhm().getCenterPanel().getGroundLayer().setBufferedImage(null);
		//doesn't work
		player.getIhm().getCenterPanel().getBasesLayer().setBufferedImage(null);
		player.getIhm().getCenterPanel().getTowersLayer().setBufferedImage(null);
		
		try {
			//display img
			if(gagne)
				this.backgroundMenu =  ImageIO.read(new File("res/img/win.png"));
			else
				this.backgroundMenu =  ImageIO.read(new File("res/img/loose.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//setting ihm content to this screen
		Ihm ihm = player.getIhm();
		ihm.setContentPane(this);
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
