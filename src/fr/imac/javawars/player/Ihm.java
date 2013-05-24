package fr.imac.javawars.player;

import java.awt.BorderLayout;
import java.awt.Dimension;


import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Ihm  extends JFrame {
	private Player p;
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
	    
//	    menu = new JMenuBar();
//	    towerTest = new JMenu("TowerTest");
//	    towerTest.setMnemonic('l');
//	    createTower = new JMenuItem("Buid tower (virtual)");
//	    createTower.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
//	    createTower.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0){
//			
//			}    	
//	    });
//	    
//	    towerTest.add(createTower);
//	    menu.add(towerTest);
//	    this.setJMenuBar(menu);
	    
	    
	    this.setContentPane(this.menu);
	    this.setVisible(true);
	    
	    this.setIconImage(Toolkit.getDefaultToolkit().getImage("res/img/icone.png"));
	}
	
	/**
	 * Create panels, juliette's code should go here
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
	

	
}
