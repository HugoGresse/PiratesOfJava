package fr.imac.javawars.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerUpgrade;
import fr.imac.javawars.engine.Tower;

/**
 * Class Menu : display menu
 *  
 */
public class Menu extends JPanel {
	//swing needed
	private static final long serialVersionUID = 1L;
	Image backgroundMenu;
	Rectangle2D boutonPlay;
	
	JPanel randomChoice = new JPanel();
	JPanel fileChoice = new JPanel();
	File map = null;
	
	
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
	
		addPlayListener();
		addChoiceTypeGame();
	}
	
	/**
	 * Interface to choose what type of map we want (random or from a file or level)
	 */
	public void addChoiceTypeGame(){
		randomChoice.setOpaque(false);
		fileChoice.setOpaque(false);
		randomChoice.setVisible(false);
		fileChoice.setVisible(false);
		
		this.setLayout(new BorderLayout());
		
		JPanel south = new JPanel();
		south.setOpaque(false);
		south.setPreferredSize(new Dimension(900,150));
		this.add(BorderLayout.SOUTH,south);
		
		south.setLayout(new BorderLayout());
		fileChoice.setPreferredSize(new Dimension(450,150));
		south.add(BorderLayout.WEST,fileChoice);
		randomChoice.setPreferredSize(new Dimension(450,150));
		south.add(BorderLayout.CENTER,randomChoice);
		
		displayFileChoice();
		displayRandomChoice();	
	}
	
	/**
	 * Listener for the level choice
	 */
	public void displayLevelChoice(){
		this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	boolean fireGame = false;
            	String map = null;
            	
            	Rectangle2D rect1 = new Rectangle2D.Double(100,80,200,150);
            	Rectangle2D rect2 = new Rectangle2D.Double(350,80,200,150);
            	Rectangle2D rect3 = new Rectangle2D.Double(600,80,200,150);
            	Rectangle2D rect4 = new Rectangle2D.Double(100,250,200,150);
            	Rectangle2D rect5 = new Rectangle2D.Double(350,250,200,150);
            	Rectangle2D rect6 = new Rectangle2D.Double(600,250,200,150);
            	
            	if ((e.getButton() == 1) && rect1.contains(e.getX(), e.getY()) ) {
            		fireGame=true;
            		map = "map/mapCool.png";
            	}
            	else if((e.getButton() == 1) && rect2.contains(e.getX(), e.getY()) ) {
            		fireGame=true;
            		map = "map/mapCool.png";
            	}
            	else if((e.getButton() == 1) && rect3.contains(e.getX(), e.getY()) ) {
            		fireGame=true;
            		map = "map/mapCool.png";
            	}
            	else if((e.getButton() == 1) && rect4.contains(e.getX(), e.getY()) ) {
            		fireGame=true;
            		map = "map/mapCool.png";
            	}
            	else if((e.getButton() == 1) && rect5.contains(e.getX(), e.getY()) ) {
            		fireGame=true;
            		map = "map/mapCool.png";
            	}
            	else if((e.getButton() == 1) && rect6.contains(e.getX(), e.getY()) ) {
            		fireGame=true;
            		map = "map/mapCool.png";
            	}
            	
            	if(fireGame){
            		JavaWars.getEngine().initializationOfTheGame(JavaWars.getHuman(),new IA(2, "IA 1") , new IA(3, "IA 2") ,new IA(4, "IA 3") ,false, new File(map));
    	        	JavaWars.getHuman().getIhm().CreatePanel();
    	        	JavaWars.getEngine().startThread();
            		JavaWars.getEngine().startThreadIA();
            	}
            }
		});
	}
	
	/**
	 * Interface for random choice
	 */
	public void displayRandomChoice(){
		//random choice
		JButton boutonRandom = new JButton("Jouer");
		randomChoice.setLayout(null);
		boutonRandom.setBounds(100,60,150,50);
		boutonRandom.setBorderPainted(false);
		boutonRandom.setBackground(new Color(148,20,20));
		boutonRandom.setForeground(Color.white);
		randomChoice.add(boutonRandom);

		//addingListener to begin game
		boutonRandom.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	JavaWars.getEngine().initializationOfTheGame(JavaWars.getHuman(),new IA(2, "IA 1") , new IA(3, "IA 2") ,new IA(4, "IA 3") ,true, null);
	        	JavaWars.getHuman().getIhm().CreatePanel();
	        	JavaWars.getEngine().startThread();
        		JavaWars.getEngine().startThreadIA();
	        }
	    });
	}
	
	/**
	 * Interface for file choice
	 */
	public void displayFileChoice(){
		//file choice
		fileChoice.setLayout(null);
		
		JButton open = new JButton("Choisir un fichier");
		open.setBorderPainted(false);
		open.setBackground(Color.white);
		open.setBounds(150,40,150,30);
		final JFileChooser fc = new JFileChooser();
		fileChoice.add(open);
		
		JButton boutonFile = new JButton("Jouer");
		boutonFile.setBorderPainted(false);
		boutonFile.setBackground(new Color(148,20,20));
		boutonFile.setForeground(Color.white);
		boutonFile.setBounds(150,80,150,50);
		fileChoice.add(boutonFile);
		
		open.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	fc.showOpenDialog(JavaWars.getHuman().getIhm());
	        	map = fc.getSelectedFile();
	        }
	    });
		
		//when the play bouton is clicked
		boutonFile.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	JavaWars.getEngine().initializationOfTheGame(JavaWars.getHuman(),new IA(2, "IA 1") , new IA(3, "IA 2") ,new IA(4, "IA 3") ,false,map);
	        	JavaWars.getHuman().getIhm().CreatePanel();
	        	JavaWars.getEngine().startThread();
        		JavaWars.getEngine().startThreadIA();
	        }
	    });
	}
	
	
	/**
	 * Adding listener on playBouton, if pressed : player can choose type of game
	 */
	public void addPlayListener(){
		this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if ((e.getButton() == 1) && boutonPlay.contains(e.getX(), e.getY()) ) {
            		chooseTypeOfGame();
            		displayLevelChoice();
            	}
            }
		});
	}
	
	/**
	 * Choosing type of map we want (random or from a file)
	 */
	public void chooseTypeOfGame(){
		boutonPlay = new Rectangle2D.Double(0,0,0,0);
		randomChoice.setVisible(true);
		fileChoice.setVisible(true);
		try {
			this.backgroundMenu =  ImageIO.read(new File("res/img/chooseTypeGame.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		repaint();
	}
	
	/**
	 * setting background for the end of the game
	 * @param gagne : boolean true = win/false = loose
	 */
	public void setBackgroundEnd(boolean gagne){
		//resetting btn with new coordinates
		randomChoice.setVisible(false);
		fileChoice.setVisible(false);
		Human player =(Human)JavaWars.getEngine().getPlayers().get(1);
		
		player.getIhm().getCenterPanel().getGroundLayer().setVisible(false);
		player.getIhm().getCenterPanel().getBasesLayer().setVisible(false);
		player.getIhm().getCenterPanel().getTowersLayer().setVisible(false);
		player.getIhm().getCenterPanel().getAgentsLayer().setVisible(false);
		
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
