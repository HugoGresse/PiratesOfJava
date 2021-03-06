package fr.imac.javawars.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import fr.imac.javawars.JavaWars;


/**
 * Class Sidebar: create sidebar of the interface
 * 
 *  
 */
public class Sidebar extends JPanel implements ActionListener {
	// swing needed
	private static final long serialVersionUID = 1L;
	private boolean mouseListenerActive = false;
	
	String htmlBefore = "<HTML><BODY style='text-align:center'>";
	String htmlMiddle="<BR><p><span style='font-weight:bold; font-size:14px;'>";
	String htmlAfter = "</span><img src='file:res/img/po.png'/></p></BODY></HTML>";
	
	//buttons
	private JButton freezeTower = new JButton(htmlBefore+"Freeze"+htmlMiddle+"4"+htmlAfter);
	private JButton laserTower = new JButton(htmlBefore+"Laser"+htmlMiddle+"6"+htmlAfter);
	private JButton missileTower = new JButton(htmlBefore+"Missile"+htmlMiddle+"2"+htmlAfter);
	private JButton gunTower = new JButton(htmlBefore+"Gun"+htmlMiddle+"3"+htmlAfter);
	private JButton bombTower = new JButton(htmlBefore+"Bomb"+htmlMiddle+"4"+htmlAfter);
	private JButton ricochetTower = new JButton(htmlBefore+"Bounce"+htmlMiddle+"4"+htmlAfter);
	private JButton sniperTower = new JButton(htmlBefore+"Sniper"+htmlMiddle+"5"+htmlAfter);
	private JButton poisonTower = new JButton(htmlBefore+"Poison"+htmlMiddle+"4"+htmlAfter);
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	
	//slider+money
	private JSlider slider = new JSlider(JSlider.VERTICAL,0,100,50);
	private JLabel labelSlider = new JLabel();
	private JLabel money = new JLabel();
	
	
	private static String type = null;
	
	/**
	 * Constructor 
	 */
	public Sidebar(){
		JPanel wrapperSlider = new JPanel();
		JPanel wrapperButtons = new JPanel();
		
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.NORTH,wrapperSlider);
		this.add(BorderLayout.CENTER,wrapperButtons);
		this.add(BorderLayout.SOUTH,money);
		
	    money.setBorder(new TitledBorder("Argent disponible"));
	    Player p = (Human)JavaWars.getEngine().getPlayers().get(1);
	    int money = p.getMoney();
	    this.money.setText(String.valueOf(money));
		this.addButtons(wrapperButtons);
		this.addSlider(wrapperSlider);
	}
	
	/**
	 * Create buttons to create towers and put a listener on them
	 * @param wrapperButtons
	 * 				panel where the buttons will be
	 * 
	 */
	public void addButtons(JPanel wrapperButtons){
		wrapperButtons.setLayout(new GridLayout(4, 2));
		wrapperButtons.setBorder(new TitledBorder("Cr�er des tours"));
		buttons.add(freezeTower);
		buttons.add(laserTower);
		buttons.add(missileTower);
		buttons.add(gunTower);
		buttons.add(bombTower);
		buttons.add(ricochetTower);
		buttons.add(sniperTower);
		buttons.add(poisonTower);
		
		for(JButton button : buttons){
			wrapperButtons.add(button);
			button.addActionListener(this);
		}
	}
	
	/**
	 * Create slider to determine the number of agents sent everytime
	 * @param wrapperSlider
	 * 				panel where the slider and the label will be
	 * 
	 */
	public void addSlider(JPanel wrapperSlider){
		slider.setPreferredSize(new Dimension(165,100));
		wrapperSlider.setBorder(new TitledBorder("Pourcentage d'agents envoy�s"));
		labelSlider.setText("50");
		//Display slider's value
		slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                int value = ((JSlider)event.getSource()).getValue();
                labelSlider.setText(String.valueOf(value));
            }
        });
		JButton confirmAgentSend = new JButton("OK");
		confirmAgentSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("test clic");
			}
		});
		wrapperSlider.setLayout(new BorderLayout());
		wrapperSlider.add(BorderLayout.NORTH,slider);
		wrapperSlider.add(BorderLayout.SOUTH,labelSlider);
		
	}
	
	/**
	 * Update money
	 */
	public void updateMoney(int money){
	    this.money.setText(String.valueOf(money));
	}
	
	
	
	public final static String getType() {
		return type;
	}

	/**
	 * Action when a "tower" button is clicked
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//getting layers of IHM
		mouseListenerActive = true;
		Human human =(Human)JavaWars.getEngine().getPlayers().get(1);
		Ihm ihm = human.getIhm();
		
		final ListenersLayer listenersLayer = ihm.getCenterPanel().getListenersLayer();
		final GroundLayer groundLayer = ihm.getCenterPanel().getGroundLayer();
		final TowersLayer towersLayer = ihm.getCenterPanel().getTowersLayer();
		
		//activate showInfluence + forcing repaint
		groundLayer.setShowInfluence(true);
		groundLayer.drawBufferedImage();
		
		//Save the type of tower, used in TowersLayer
		if(e.getSource() == freezeTower){
			type = "freeze";
		}
		else if(e.getSource() == laserTower){
			type ="laser";
		}
		else if(e.getSource() == missileTower){
			type="missile";
		}
		else if(e.getSource() == gunTower){
			type="machinegun";
		}
		else if(e.getSource() == bombTower){
			type="bomb";
		}
		else if(e.getSource() == ricochetTower){
			type="bounce";
		}
		else if(e.getSource() == sniperTower){
			type="sniper";
		}
		else if(e.getSource() == poisonTower){
			type = "poison";
		} else 
			type= "undefined";

		//adding a listener on listeners panel
		listenersLayer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(mouseListenerActive){
            		
            		//create tower
            		towersLayer.createTower(e.getX(), e.getY(), Sidebar.getType());
            		
            		//remove Listener + remove drawing of the influence zone
            		mouseListenerActive = false;
            		groundLayer.setShowInfluence(false);
            		groundLayer.drawBufferedImage();
            	}
            }
		});		
		
	}

	public JLabel getLabelSlider() {
		return labelSlider;
	}

	public void setLabelSlider(JLabel labelSlider) {
		this.labelSlider = labelSlider;
	}
}
