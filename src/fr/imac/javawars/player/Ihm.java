package fr.imac.javawars.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Ihm  extends JFrame {
	
	private JPanel conteneur = new JPanel();
	//private ArrayList<PanelInterface> listPanel = new ArrayList<PanelInterface>();
	
	private TestPanel bandeau;
	
	private Dimension size;
	
	private JMenuBar menu = null;
	private JMenu towerTest = null;
	private JMenuItem createTower = null;
	
	private static final long serialVersionUID = 1L;
	
	public Ihm(String name) {
		this.setTitle(name);
	    this.setSize(900, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // TODO : change default listener and close thread before quit
	    // http://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);           
	    
	    menu = new JMenuBar();
	    towerTest = new JMenu("TowerTest");
	    towerTest.setMnemonic('l');
	    createTower = new JMenuItem("Buid tower (virtual)");
	    createTower.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
	    createTower.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("- build tower");
			}	    	
	    });
	    
	    towerTest.add(createTower);
	    menu.add(towerTest);
	    this.setJMenuBar(menu);
	    
	    this.size = new Dimension(this.getWidth(), this.getHeight());
	    
	    this.conteneur.setPreferredSize(this.size);
	    this.conteneur.setBackground(Color.white);
	    
	    CreatePanel();
	    
	    this.setContentPane(this.conteneur);
	    this.setVisible(true);
		
	}
	
	private void CreatePanel(){
		// Creation bandeau/
	    bandeau = new TestPanel(this.size);
	    //this.conteneur.add(lifePanel.getPanel());
	    
		this.conteneur.add(BorderLayout.EAST, bandeau.getPanel());
	}
	
	public void updateLife(int nLife){
		bandeau.update(String.valueOf(nLife));
	}

	
}
