package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import sun.swing.SwingUtilities2;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;

/**
 * Class BasesLayer: manage & display bases
 *  
 */
public class BasesLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	BasesLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
	}

	/**
	 * Auto update label on base every 1s
	 */
	public void autoUpdate(){
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				repaint();
			}	
		};
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);
	}

	/**
	 * Painting layer (display bases)
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
		//Antialiasing ON
		((Graphics2D)  g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//getting bases from engine
		CopyOnWriteArrayList<Base> bases = JavaWars.getDispatcher().getBases();
		Iterator<Base> it = bases.iterator();
		
		while(it.hasNext()){
			Base b = it.next();
			drawBase(b, g);
		}
	}
	
	private void drawBase(Base b, Graphics g){
		int radius = b.getRadius();
		Player player = b.getPlayer();
		Color color1 = null;
		Color color2 = null;
		
		//defining color for each player
		if(player == null){
			color1 = new Color(198, 198, 198);
			color2 = new Color(229, 229, 229);
		}
		else{
			switch(player.getPlayerNumber()){
				case 1 : 
					color1 = new Color(197,54,59,170);
					color2 = new Color(197,54,59,255);
					break;
				case 2 :
					color1 = new Color(23,100,145,170);
					color2 = new Color(23,100,145,255);
					break;
				case 3:
					color1 = new Color(131,195,25,170);
					color2 = new Color(131,195,25,255);
					break;
				case 4 :
					color1 = new Color(255,136,0,170);
					color2 = new Color(255,136,0,255);
					break;
			}
		}
		
		//first circle : under (border)
		g.setColor(color1);
		g.fillOval((int)b.getPosition().getX()- radius, (int)b.getPosition().getY()-radius, radius*2, radius*2);
		
		//second circle : above
		g.setColor(color2);
		g.fillOval( (int)(b.getPosition().getX()- radius*1.5/2), (int)(b.getPosition().getY()-radius*1.5/2), (int)(radius*1.5), (int)(radius*1.5));
		
		//number of alien
		if(player == null)g.setColor(Color.gray);
		else g.setColor(Color.white);
		g.drawString(String.valueOf(b.getLife()), (int)(b.getPosition().getX()-6), (int)(b.getPosition().getY()+4));
	}

}