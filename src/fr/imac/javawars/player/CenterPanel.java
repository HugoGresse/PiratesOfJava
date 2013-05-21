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
	private ListenersLayer listeners;
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
        this.listeners = new ListenersLayer();
        this.map = new GroundLayer("res/img/ground.jpg","res/img/wall.png");
        
        //add panels to the layeredPane
        this.layers.add(map,new Integer(-10));
		this.layers.add(bases,new Integer(8));
		this.layers.add(towers,new Integer(9));
		this.layers.add(agents,new Integer(10));
		this.layers.add(listeners,new Integer(11));
		
    }
	
	public TowersLayer getTowersLayer(){
		return towers;
	}
	
	public ListenersLayer getListenersLayer(){
		return listeners;
	}
	

}