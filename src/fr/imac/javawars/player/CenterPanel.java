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
	private TowersLayer towers;
	private BasesLayer bases;
	private AgentsLayer agents;
	private GroundLayer map;
	
	/**
	 * CONSTRUCTOR
	 * @param layers
	 * 				container of the different panels
	 * 
	 */
	public CenterPanel(JLayeredPane layers) {
        this.layers  = layers;
        this.layers.setOpaque(false);
        this.towers = new TowersLayer();
        this.bases = new BasesLayer();
        this.agents = new AgentsLayer();
        this.map = new GroundLayer("res/img/ground.jpg","res/img/wall.png");
        
        //add panels to the layeredPane
        this.layers.add(map,new Integer(-10));
		this.layers.add(bases,new Integer(1));
		this.layers.add(towers,new Integer(1));
		this.layers.add(agents,new Integer(10)); 
    }
	
	public TowersLayer getTowersLayer(){
		return towers;
	}

}