package fr.imac.javawars.player;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerDelete;
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
	private JTextArea dialogue = new JTextArea(3,20);
	
	
	//tower panel
	private JPanel towerInfos = new JPanel();
	private JLabel towerStrength = new JLabel();
	private JLabel towerActionField = new JLabel();
	private JButton upStrength = new JButton("+");
	private JButton upActionField = new JButton("+");
	
	//button appearance (sell tower)
	String htmlBefore = "<HTML><BODY style='text-align:center'>";
	String htmlMiddle="<BR><p><span style='font-weight:bold; font-size:14px;'>";
	String htmlAfter = "</span><img src='file:res/img/po.png'/></p></BODY></HTML>";
	
	private JButton sellTower = new JButton (htmlBefore+"Vendre"+htmlMiddle+""+htmlAfter);
	private Tower currentTower;
	
	
	/** constructor */
	public BottomBar(){
		//organizing content
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST,wrapperInfos);
		this.add(BorderLayout.EAST,wrapperButtons);
		this.currentTower = null;
		dialogue.setMargin(new Insets(10,10,10,10));
		dialogue.setLineWrap(true);
		dialogue.setWrapStyleWord(true);
		
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
	        		JavaWars.getEngine().pausingGame();
	        	}
	        	else{
	        		pause.setText("||");
	        		JavaWars.getEngine().resumingGame();
	        	}
	        }
	    });
		
		speed.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	//appearance of the button
	        	if(speed.getText()==">>"){
	        		speed.setText(">");
	        		JavaWars.getEngine().setFpsTarget(500/30);
	        	}
	        	else {
	        		speed.setText(">>");
	        		JavaWars.getEngine().setFpsTarget(1000/30);
	        	}
	        }
	    });	
	}
	
	/**
	 * Filling JPanel towerInfos
	 */
	public void manageTowerInfos(){
		//upgrades buttons/labels
		towerStrength.setBorder(new TitledBorder("Puissance "));
		towerActionField.setBorder(new TitledBorder("Champs d'action "));
		towerStrength.setPreferredSize(new Dimension(120,65));
		towerActionField.setPreferredSize(new Dimension(130,65));
		towerInfos.add(towerStrength);
		towerInfos.add(upStrength);
		towerInfos.add(towerActionField);
		towerInfos.add(upActionField);
		
		//sell button
		sellTower.setPreferredSize(new Dimension(100,50));
		towerInfos.add(sellTower);
		
		//hidding panel at first
		towerInfos.setVisible(false);
		
		//add listeners on buttons (improve strength & actionField + sell tower)
		upStrength.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	System.out.println("+strength");
	        	final Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
	        	Tower t = currentTower; 
	        	
	        	if(t.getUpgradeStrength()<5){
		    		ActionTowerUpgrade myAction = new ActionTowerUpgrade(player, t,  2);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("Vous ne pouvez plus augmenter la puissance de cette tour");
	        	}
	        }
	    });
		
		upActionField.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	System.out.println("+actionField");
	        	final Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
	        	Tower t = currentTower;
	        	
	        	if(t.getUpgradeRange()<5){
		    		ActionTowerUpgrade myAction = new ActionTowerUpgrade(player, t,  1);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("Vous ne pouvez plus augmenter la portée de cette tour");
	        	}
	        }
	    });
		
		sellTower.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
				ActionTowerDelete myAction = new ActionTowerDelete(player,currentTower);
	    		JavaWars.getDispatcher().addAction(myAction);
	        }
		});
		
	}
	
	/**
	 * Update infos on tower
	 */
	public void updateTowersLabel(){
		if(currentTower==null) return;
		CopyOnWriteArrayList<Tower> towers = JavaWars.getDispatcher().getTowers();
		Iterator<Tower> it = towers.iterator();
		
		
		while(it.hasNext()){
			Tower t = it.next();
			if(t.getPosition().getX() == currentTower.getPosition().getX() && t.getPosition().getY() == currentTower.getPosition().getY()){
				currentTower = t;
				
				double price = t.getPrice()+ t.getUpgradeRange() + t.getUpgradeStrength()*2; 
				towerStrength.setText(String.valueOf(t.getStrength()));
				towerActionField.setText(String.valueOf(t.getActionField()));
				sellTower.setText(htmlBefore+"Vendre"+htmlMiddle+price+htmlAfter);
				break;
			}
		}
	}
	
	/**
	 *Update error textarea
	 */
	public void updateError(){
		String error = JavaWars.getDispatcher().getError();
		
		if(error == null)
			return;
		
		dialogue.setText(error);
		
		//Remove the texte after 5s
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				dialogue.setText("");
			}	
		};
		
		Timer timer = new Timer();
		timer.schedule(task, 5000);
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

	public Tower getCurrentTower(){
		return currentTower;
	}
	
	public void setCurrentTower(Tower t){
		this.currentTower = t;
	}
}
