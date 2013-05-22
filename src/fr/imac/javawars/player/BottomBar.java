package fr.imac.javawars.player;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerUpgrade;
import fr.imac.javawars.engine.Tower;

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
	
	private Tower currentTower;
	
	
	/** constructor */
	public BottomBar(){
		//organizing content
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST,wrapperInfos);
		this.add(BorderLayout.EAST,wrapperButtons);
		this.currentTower = null;
		
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
		
		//add listeners on buttons (improve strength & actionField)
		upStrength.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	System.out.println("+strength");
	        	final Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
	        	Tower t = currentTower; 
	    		ActionTowerUpgrade myAction = new ActionTowerUpgrade(player, t,  2);
	    		JavaWars.getDispatcher().addAction(myAction);
	        }
	    });
		
		upActionField.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	System.out.println("+actionField");
	        	final Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
	        	Tower t = currentTower;
	    		ActionTowerUpgrade myAction = new ActionTowerUpgrade(player, t,  1);
	    		JavaWars.getDispatcher().addAction(myAction);
	        }
	    });
		
	}
	
	public void updateTowersLabel(){
		if(currentTower==null) return;
		CopyOnWriteArrayList<Tower> towers = JavaWars.getDispatcher().getTowers();
		Iterator<Tower> it = towers.iterator();
		
		
		while(it.hasNext()){
			Tower t = it.next();
			if(t.getPosition().getX() == currentTower.getPosition().getX() && t.getPosition().getY() == currentTower.getPosition().getY()){
				currentTower = t;
				towerStrength.setText(String.valueOf(t.getStrength()));
				towerActionField.setText(String.valueOf(t.getActionField()));
				break;
			}
		}
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
	
	/** getters **/
	public Tower getCurrentTower(){
		return currentTower;
	}
	
	public void setCurrentTower(Tower t){
		this.currentTower = t;
	}
}
