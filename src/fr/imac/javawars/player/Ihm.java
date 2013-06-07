package fr.imac.javawars.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ihm  extends JFrame {
	private Menu menu = new Menu();
	private JPanel conteneur = new JPanel();
	
	//panels
	private CenterPanel centerPanel;
	private Sidebar sidebar;
	private BottomBar bottomBar;
	
	//swing needed
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create an human interface to communicate with the machine
	 * @param name
	 * 				the name of the window
	 */
	public Ihm(String name) {
		this.setTitle(name);
	    this.setSize(900, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);           
	    
	    
	    this.setContentPane(this.menu);
	    this.setVisible(true);
	    
	    this.setIconImage(Toolkit.getDefaultToolkit().getImage("res/img/icone.png"));
	}
	
	/**
	 * Create panels for the interface
	 */
	public void CreatePanel(){
		centerPanel = new CenterPanel(this.getLayeredPane());
		sidebar = new Sidebar();
		bottomBar = new BottomBar();
		
		centerPanel.setPreferredSize(new Dimension(centerPanel.getWidth(),centerPanel.getHeight()));
		
		this.conteneur.setLayout(new BorderLayout());
		this.conteneur.add(BorderLayout.CENTER,centerPanel);
		this.conteneur.add(BorderLayout.EAST,sidebar);
		this.conteneur.add(BorderLayout.SOUTH,bottomBar);
		
		this.setContentPane(this.conteneur);
	}

	
	/**
	 * 
	 *GETTERS	
	 */
	public CenterPanel getCenterPanel(){
		return centerPanel;
	}
	
	public BottomBar getBottomBar(){
		return bottomBar;
	}
	
	public Sidebar getSidebar(){
		return sidebar;
	}
	
	public Menu getMenu(){
		return menu;
	}
	
}
