package fr.imac.javawars.player;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class Sidebar: create sidebar of the interface
 * 
 *  
 */
public class Sidebar extends JPanel implements ActionListener {
	// swing needed
	private static final long serialVersionUID = 1L;
	
	//buttons
	private JButton freezeTower = new JButton("Freeze");
	private JButton laserTower = new JButton("Laser");
	private JButton missileTower = new JButton("Missile");
	private JButton gunTower = new JButton("Gun");
	private JButton bombTower = new JButton("Bomb");
	private JButton ricochetTower = new JButton("Ricochet");
	private JButton sniperTower = new JButton("Sniper");
	private JButton poisonTower = new JButton("Poison");
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	
	//slider+money
	private JSlider slider = new JSlider(JSlider.VERTICAL,0,100,50);
	private JLabel labelSlider = new JLabel();
	private JLabel money = new JLabel("50");
	
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
		wrapperSlider.setBorder(new TitledBorder("Nombre d'agents envoy�s"));
		labelSlider.setText("50");
		//Display slider's value
		slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                int value = ((JSlider)event.getSource()).getValue();
                labelSlider.setText(String.valueOf(value));
                // TODO : send new number to dispatcher
            }
        });
		wrapperSlider.setLayout(new BorderLayout());
		wrapperSlider.add(BorderLayout.NORTH,slider);
		wrapperSlider.add(BorderLayout.SOUTH,labelSlider);
	}

	@Override
	//When a "tower button" is clicked
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == freezeTower){
			money.setText("Freeze");
		}
	}

}
