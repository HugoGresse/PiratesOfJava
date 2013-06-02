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
	private TowersLayer towersLayer;
	private BasesLayer basesLayer;
	private AgentsLayer agentsLayer;
	private ListenersLayer listenersLayer;
	private GroundLayer groundLayer;
	
	/**
	 * CONSTRUCTOR
	 * @param layers
	 * 				container of the different panels
	 * 
	 */
	public CenterPanel(JLayeredPane layers) {
        this.layers  = layers;
        this.layers.setOpaque(false);
        this.towersLayer = new TowersLayer();
        this.basesLayer = new BasesLayer();
        this.agentsLayer = new AgentsLayer();
        this.listenersLayer = new ListenersLayer();
        this.groundLayer = new GroundLayer("res/img/ground.jpg","res/img/sand.jpg");
        
        //add panels to the layeredPane
        this.layers.add(groundLayer,new Integer(-10));
		this.layers.add(basesLayer,new Integer(10));
		this.layers.add(towersLayer,new Integer(9));
		this.layers.add(agentsLayer,new Integer(8));
		this.layers.add(listenersLayer,new Integer(11));
    }

	public JLayeredPane getLayers() {
		return layers;
	}

	public void setLayers(JLayeredPane layers) {
		this.layers = layers;
	}

	public TowersLayer getTowersLayer() {
		return towersLayer;
	}

	public void setTowersLayer(TowersLayer towersLayer) {
		this.towersLayer = towersLayer;
	}

	public BasesLayer getBasesLayer() {
		return basesLayer;
	}

	public void setBasesLayer(BasesLayer basesLayer) {
		this.basesLayer = basesLayer;
	}

	public AgentsLayer getAgentsLayer() {
		return agentsLayer;
	}

	public void setAgentsLayer(AgentsLayer agentsLayer) {
		this.agentsLayer = agentsLayer;
	}

	public ListenersLayer getListenersLayer() {
		return listenersLayer;
	}

	public void setListenersLayer(ListenersLayer listenersLayer) {
		this.listenersLayer = listenersLayer;
	}

	public GroundLayer getGroundLayer() {
		return groundLayer;
	}

	public void setGroundLayer(GroundLayer groundLayer) {
		this.groundLayer = groundLayer;
	}
}