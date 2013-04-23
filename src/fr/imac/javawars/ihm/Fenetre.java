package fr.imac.javawars.ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
  
public class Fenetre extends JFrame {
	
	private JPanel conteneur = new JPanel();
	
	private Dimension size;
	
	private JMenuBar menu = null;
	private JMenu life = null;
	private JMenuItem lifePlus = null;
	private JMenuItem LifeMoins = null;
	  
	  
	private static final long serialVersionUID = 1L;

	public Fenetre(String name){            
		this.setTitle(name);
	    this.setSize(900, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);           

	    menu = new JMenuBar();
	    life = new JMenu("Life");
	    life.setMnemonic('l');
	    lifePlus = new JMenuItem("Ajouter une vie");
	    lifePlus.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
	    lifePlus.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Life ++");
				/*conteneur.removeAll();
				GamePanel gp = new GamePanel(size, model);
				model.addObserver(gp);
				conteneur.add(gp.getPanel(), BorderLayout.CENTER);
				conteneur.revalidate();
				initModel();*/
			}	    	
	    });
	    LifeMoins = new JMenuItem("Enlever une vie");
	    LifeMoins.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
	    LifeMoins.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Life --");
				/*conteneur.removeAll();
				GamePanel gp = new GamePanel(size, model);
				model.addObserver(gp);
				conteneur.add(gp.getPanel(), BorderLayout.CENTER);
				conteneur.revalidate();
				initModel();*/
			}	    	
	    });
	    
	    life.add(lifePlus);
	    life.add(LifeMoins);
	    menu.add(life);

	    this.setJMenuBar(menu);
	    
	    
	    this.size = new Dimension(this.getWidth(), this.getHeight());
	    
	    this.conteneur.setPreferredSize(this.size);
	    this.conteneur.setBackground(Color.white);
	    this.conteneur.add(new TestPanel(this.size).getPanel());
	    this.setContentPane(this.conteneur);
	    
	    
	    this.setVisible(true);
	    
	    
	}
}