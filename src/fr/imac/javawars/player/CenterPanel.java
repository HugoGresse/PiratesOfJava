package fr.imac.javawars.player;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Class CenterPanel: create panel where map goes
 *  
 */
public class CenterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLayeredPane layers;
	private Layer towers;
	private Layer bases;
	private Layer agents;
	private Layer map;
	
	/**
	 * CONSTRUCTOR
	 * @param layers
	 * 				container of the different panels
	 * @param mapData
	 * 				tab that describe the map with numbers
	 */
	public CenterPanel(JLayeredPane layers) {
        this.layers  = layers;
        this.layers.setOpaque(false);
        this.towers = new Layer("towers");
        this.bases = new Layer("bases");
        this.agents = new Layer("agents");
        this.map = new Layer("map");
        
        //add panels to the layeredPane
        this.layers.add(map,new Integer(-10));
		this.layers.add(bases,new Integer(1));
		this.layers.add(towers,new Integer(1));
		this.layers.add(agents,new Integer(10)); 
    }

}