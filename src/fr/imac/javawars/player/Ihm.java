package fr.imac.javawars.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ihm  extends JFrame {
	private Player p;
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
	 * @param pp
	 * 				the player attach to the ihm
	 */
	public Ihm(String name, Player pp) {
		this.p = pp;
		
		this.setTitle(name);
	    this.setSize(900, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // TODO : change default listener and close thread before quit
	    // http://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);           
	    
	    CreatePanel();
	    
	    this.setContentPane(this.conteneur);
	    this.setVisible(true);
	}
	
	/**
	 * Create panels, juliette's code should go here
	 */
	private void CreatePanel(){
		centerPanel = new CenterPanel(this.getLayeredPane());
		sidebar = new Sidebar();
		bottomBar = new BottomBar();
		
		centerPanel.setPreferredSize(new Dimension(centerPanel.getWidth(),centerPanel.getHeight()));
		
		this.conteneur.setLayout(new BorderLayout());
		this.conteneur.add(BorderLayout.CENTER,centerPanel);
		this.conteneur.add(BorderLayout.EAST,sidebar);
		this.conteneur.add(BorderLayout.SOUTH,bottomBar);
		
	}
	
	
	public void updateTestLabel(double money){
		
	}

	
}