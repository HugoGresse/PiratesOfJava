package fr.imac.javawars.player;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Class BottomBar: create bottom bar of the interface
 *  
 */
public class BottomBar extends JPanel{
	//swing needed
	private static final long serialVersionUID = 1L;
	
	private JButton pause = new JButton("||");
	private JButton speed = new JButton(">>");
	private JPanel wrapperButtons = new JPanel();
	private JPanel wrapperInfos = new JPanel();
	private JTextArea dialogue = new JTextArea(3,23);
	
	//tower panel
	private JPanel towerInfos = new JPanel();
	private JLabel towerStrength = new JLabel();
	private JLabel towerActionField = new JLabel();
	private JButton upStrength = new JButton("+");
	private JButton upActionField = new JButton("+");
	
	/** constructor */
	public BottomBar(){
		//organizing content
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST,wrapperInfos);
		this.add(BorderLayout.EAST,wrapperButtons);
		
		wrapperInfos.setLayout(new BorderLayout());
		wrapperInfos.add(BorderLayout.WEST,towerInfos);
		wrapperInfos.add(BorderLayout.EAST,dialogue);
		
		manageWrapperButtons();
		manageTowerInfos();
	}
	
	/**
	 * Filling JPanel wrapperButtons
	 */
	public void manageWrapperButtons(){
		//put pause and speed buttons
		pause.setPreferredSize(new Dimension(82,65));
		speed.setPreferredSize(new Dimension(83,65));
		wrapperButtons.add(pause);
		wrapperButtons.add(speed);
		
		//adding listeners
		pause.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	//appearance of the button
	        	if(pause.getText()=="||"){
	        		pause.setText(">");
	        	}
	        	else pause.setText("||");
	        }
	    });
		
		speed.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	//appearance of the button
	        	if(speed.getText()==">>"){
	        		speed.setText(">");
	        	}
	        	else speed.setText(">>");
	        }
	    });	
	}
	
	/**
	 * Filling JPanel towerInfos
	 */
	public void manageTowerInfos(){
		towerStrength.setBorder(new TitledBorder("Puissance de la tour"));
		towerActionField.setBorder(new TitledBorder("Champs d'action de la tour"));
		towerStrength.setPreferredSize(new Dimension(150,65));
		towerActionField.setPreferredSize(new Dimension(180,65));
		towerInfos.add(towerStrength);
		towerInfos.add(upStrength);
		towerInfos.add(towerActionField);
		towerInfos.add(upActionField);
		//hidding the panel at first
		towerInfos.setVisible(false);
	}
	
	/**
	 * Getters
	 */
	public JPanel getTowerInfos(){
		return towerInfos;
	}

	public JLabel getTowerStrength() {
		return towerStrength;
	}

	public JLabel getTowerActionField() {
		return towerActionField;
	}

	public JButton getUpStrength() {
		return upStrength;
	}

	public JButton getUpActionField() {
		return upActionField;
	}
	
	
	
	

}
