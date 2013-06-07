package fr.imac.javawars.player;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionBaseUpgrade;
import fr.imac.javawars.dispatcher.ActionTowerDelete;
import fr.imac.javawars.dispatcher.ActionTowerUpgrade;
import fr.imac.javawars.engine.Base;
import fr.imac.javawars.engine.Base.Power;
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
	private JTextArea dialogue = new JTextArea(3,14);
	
	private JPanel towerBaseInfos = new JPanel();
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
	private Base currentBase;
	
	//base panel
	private JPanel baseInfos = new JPanel();
	private JLabel baseRegenSpeed = new JLabel();
	private JButton upRegenSpeed = new JButton("+");
	private JButton speedBase = new JButton(htmlBefore+"Speed"+htmlMiddle+"15"+htmlAfter);
	private JButton strengthBase = new JButton(htmlBefore+"Strength"+htmlMiddle+"15"+htmlAfter);
	private JButton regenBase = new JButton(htmlBefore+"Medicale"+htmlMiddle+"15"+htmlAfter);
	private JButton multBase = new JButton(htmlBefore+"Mult"+htmlMiddle+"15"+htmlAfter);
	
	/** constructor */
	public BottomBar(){
		
		//organizing content
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST,wrapperInfos);
		this.add(BorderLayout.EAST,wrapperButtons);
		this.currentTower = null;
		this.currentBase = null;
		dialogue.setMargin(new Insets(10,10,10,10));
		dialogue.setLineWrap(true);
		dialogue.setWrapStyleWord(true);
		
		
		wrapperInfos.setLayout(new BorderLayout());
		wrapperInfos.add(BorderLayout.WEST,towerBaseInfos);
		wrapperInfos.add(BorderLayout.EAST,dialogue);
		
		towerBaseInfos.add(baseInfos);
		towerBaseInfos.add(towerInfos);
		
		manageWrapperButtons();
		manageTowerInfos();
		manageBaseInfos();
	}
	
	/**
	 * Filling JPanel wrapperButtons (pause and accelerate)
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
	 * Filling JPanel baseInfos(adding Listeners, buttons etc)
	 */
	public void manageBaseInfos(){
		baseRegenSpeed.setBorder(new TitledBorder("Vitesse régénération "));
		baseRegenSpeed.setPreferredSize(new Dimension(120,65));
		baseInfos.add(baseRegenSpeed);
		baseInfos.add(upRegenSpeed);
		
		speedBase.setPreferredSize(new Dimension(80,50));
		strengthBase.setPreferredSize(new Dimension(80,50));
		regenBase.setPreferredSize(new Dimension(80,50));
		multBase.setPreferredSize(new Dimension(80,50));
		
		baseInfos.add(speedBase);
		baseInfos.add(strengthBase);
		baseInfos.add(regenBase);
		baseInfos.add(multBase);
		
		baseInfos.setVisible(false);
		
		//add listeners on buttons
		upRegenSpeed.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(currentBase.getUpgrades()<5){
		    		ActionBaseUpgrade myAction = new ActionBaseUpgrade(JavaWars.getHuman(), currentBase, Power.NORMAL);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("Vous ne pouvez plus augmenter la vitesse de régénération de la base");
	        	}
	        }
	    });
		
		speedBase.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(currentBase.getPower() == Power.NORMAL){
		        	ActionBaseUpgrade myAction = new ActionBaseUpgrade(JavaWars.getHuman(), currentBase, Power.SPEED_UP);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("La base possède déjà un pouvoir");
	        	}
	        }
	    });
		
		strengthBase.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(currentBase.getPower() == Power.NORMAL){
		        	ActionBaseUpgrade myAction = new ActionBaseUpgrade(JavaWars.getHuman(), currentBase, Power.RESISTANCE);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("La base possède déjà un pouvoir");
	        	}
	        }
	    });
		
		regenBase.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(currentBase.getPower() == Power.NORMAL){
		        	ActionBaseUpgrade myAction = new ActionBaseUpgrade(JavaWars.getHuman(), currentBase, Power.LIFE_UP);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("La base possède déjà un pouvoir");
	        	}
	        }
	    });
		
		multBase.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(currentBase.getPower() == Power.NORMAL){
		        	ActionBaseUpgrade myAction = new ActionBaseUpgrade(JavaWars.getHuman(), currentBase, Power.MULT);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("La base possède déjà un pouvoir");
	        	}
	        }
	    });
	}
	
	/**
	 * Filling JPanel towerInfos (adding Listeners, buttons etc)
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
		
		final Human player = JavaWars.getHuman();
    	
		//add listeners on buttons (improve strength & actionField + sell tower)
		upStrength.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(currentTower.getUpgradeStrength()<5){
		    		ActionTowerUpgrade myAction = new ActionTowerUpgrade(player, currentTower,  2);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("Vous ne pouvez plus augmenter la puissance de cette tour");
	        	}
	        }
	    });
		
		upActionField.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(currentTower.getUpgradeRange()<5){
		    		ActionTowerUpgrade myAction = new ActionTowerUpgrade(player, currentTower,  1);
		    		JavaWars.getDispatcher().addAction(myAction);
	        	}
	        	else{
	        		dialogue.setText("Vous ne pouvez plus augmenter la portée de cette tour");
	        	}
	        }
	    });
		
		sellTower.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
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
		Tower t= currentTower;	
		double price = t.getPrice()+ t.getUpgradeRange() + t.getUpgradeStrength()*2; 
		towerStrength.setText(String.valueOf(t.getStrength()));
		towerActionField.setText(String.valueOf(t.getActionField()));
		sellTower.setText(htmlBefore+"Vendre"+htmlMiddle+price+htmlAfter);
	}
	
	/**
	 * Update infos on base
	 */
	public void updateBasesLabel(){
		if(currentBase == null) return;
		Base b = currentBase;
		baseRegenSpeed.setText(String.valueOf(b.getSpeedRegeneration()));
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
	
	public JPanel getBaseInfos(){
		return baseInfos;
	}

	public JLabel getTowerStrength() {
		return towerStrength;
	}

	public JLabel getTowerActionField() {
		return towerActionField;
	}
	
	public JLabel getBaseRegenSpeed(){
		return baseRegenSpeed;
	}

	public JButton getUpStrength() {
		return upStrength;
	}

	public JButton getUpActionField() {
		return upActionField;
	}

	public void setCurrentTower(Tower t){
		this.currentTower = t;
	}
	
	public Tower getCurrentTower(){
		return currentTower;
	}
	
	public void setCurrentBase(Base b){
		this.currentBase = b;
	}
	
	public Base getCurrentBase(){
		return this.currentBase;
	}
}
