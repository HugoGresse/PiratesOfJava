package fr.imac.javawars.player;


import java.awt.Graphics;

import java.util.Iterator;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionAgentSend;
import fr.imac.javawars.engine.Agent;
import fr.imac.javawars.engine.Base;


/**
 * Class AgentsLayer: manage & display agents 
 */
public class AgentsLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage bufferedImage = null;
	private boolean isEmptyBufferedImage;
	
	private Image imagePlayer1;
	private Image imagePlayer2;
	private Image imagePlayer3;
	private Image imagePlayer4;
	
	/**
	 * Constructor
	 */
	AgentsLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
		
		try {
			imagePlayer1 = ImageIO.read(new File("res/img/bateauRg.png"));
			imagePlayer2 = ImageIO.read(new File("res/img/bateauBl.png"));
			imagePlayer3 = ImageIO.read(new File("res/img/bateauVt.png"));
			imagePlayer4 = ImageIO.read(new File("res/img/bateauOr.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bufferedImage =  new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		isEmptyBufferedImage = true;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		if(!isEmptyBufferedImage)
			g.drawImage(bufferedImage, 0, 0, null);
		else {
			drawBufferedImage();
			g.drawImage(bufferedImage, 0, 0, null);
		}
	}
	
	
	/**
	 * Draw the new stuff on a buffered image insteed of the default graphics
	 */
	public void drawBufferedImage(){
		
		//bufferedImage.setData( bufferedImageClear.getRaster()); -- fait chuté les fps
		bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		//System.out.println("Draw AgentsLayersBuffer");
		Graphics2D g = bufferedImage.createGraphics();
		
		//Antialiasing ON
		((Graphics2D)  g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// need to run trough all agents created
		//getting players from engine
		Map<Integer, Player> players = JavaWars.getDispatcher().getPlayers();
		Iterator<Map.Entry<Integer, Player>> it = players.entrySet().iterator();
	
		
		while(it.hasNext()){
			drawAgentsOfPlayer(it.next().getValue(), g);
		}
		
		isEmptyBufferedImage = false;
		
		//After the calculation of "new" image, we display it
		repaint();
	}

	/**
	 * Draw agents of one player
	 */
	public void drawAgentsOfPlayer(Player player, Graphics g){
		Image icon = null;
		switch (player.getPlayerNumber()) {
		case 1:
			icon = imagePlayer1;
			break;
		case 2:
			icon = imagePlayer2;
			break;
		case 3:
			icon = imagePlayer3;
			break;
		case 4:
			icon = imagePlayer4;
			break;
		default:
			break;
		}
		
		
		ConcurrentLinkedQueue<Agent> agents = player.getAgents();
		if (agents == null)
			return;
		
		Iterator<Agent> it = agents.iterator();
		while(it.hasNext()){

			Agent agent = it.next();
			
			int x = (int) agent.getPosition().getX();
			int y = (int) agent.getPosition().getY();
			
			g.drawImage(icon, x - 9/2 , y - 11/2 , 9, 11,null);
		}

	}
	
	public void createAndSendAgent(Player player, Base start, Base target){	
		ActionAgentSend myAction = new ActionAgentSend(player, start, target);
		JavaWars.getDispatcher().addAction(myAction);
	}
}