package fr.imac.javawars.player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;
import fr.imac.javawars.engine.Tower;

/**
 * Class ListenersLayer: handle all listeners on the main panel
 *  
 */
public class ListenersLayer extends JPanel{
	//swing needeed
	private static final long serialVersionUID = 1L;
	
	//used to know which base of start and of target the player has choosen
	Base currentHumanBaseSelected;
	Base currentTargetBaseSelected;

	/**
	 * CONSTRUCTOR
	 */
	ListenersLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
		this.currentHumanBaseSelected = null;
		this.currentTargetBaseSelected = null;
		addListeners();
		
	}
	
	/**
	 * Adding listeners on the panel
	 */
	public void addListeners(){
		//getting bases and towers from engine
		final CopyOnWriteArrayList<Base> bases = JavaWars.getDispatcher().getBases();
		final CopyOnWriteArrayList<Tower> towers = JavaWars.getDispatcher().getTowers();
		
		//getting player (the one with IHM)
		final Human human =(Human)JavaWars.getEngine().getPlayers().get(1);
		
		this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	manageBasesListeners(e, bases,human);
            	manageTowersListeners(e,towers,human);
            }
		});
	}
	

	/**
	 * Manage bases listener
	 * @param e
	 * 				mouseEvent, useful for the coord
	 * @param bases
	 * 				list of the engine bases
	 * @param human
	 * 				player who has the IHM
	 */
	public void manageBasesListeners(MouseEvent e, CopyOnWriteArrayList<Base> bases, Human human ){
		//initialize bases list/iterator
		CopyOnWriteArrayList<Base> tmpBases = bases;
		final Iterator<Base> it = tmpBases.iterator();
		//iterate on bases list
    	while(it.hasNext()){
    		Base b = it.next();
    		int radius = b.getRadius();
    		
    		//create an oval and test if the click of the mouse is in it.
    		Ellipse2D oval = new Ellipse2D.Double((int)b.getPosition().getX()- radius, (int)b.getPosition().getY()-radius, radius*2, radius*2);
    		if(e.getButton() == 1){
				if (oval.contains(e.getX(), e.getY())) {
					//if the base selected belongs to the player
					if(b.getPlayer() == human){
						this.currentHumanBaseSelected = b;
						BottomBar bottom= human.getIhm().getBottomBar();
						bottom.setCurrentBase(b);
						
						bottom.getBaseRegenSpeed().setText(String.valueOf(b.getSpeedRegeneration()));
						bottom.getTowerInfos().setVisible(false);
						bottom.getBaseInfos().setVisible(true);
					}
					else{
						//if the base selected belongs to an enemy
						if(b.getPlayer() != null){
							this.currentTargetBaseSelected = b;
						}
						//if the base selected belongs to no one (neutral)
						else{
							this.currentTargetBaseSelected = b;
						}
						// when one of our base is selected and that we have a target selected, we send an agent at every click
						if( this.currentHumanBaseSelected != null && this.currentTargetBaseSelected != null){
							//if there are enough agents :
							if(this.currentHumanBaseSelected.getLife() > 1){
								//we send the number of agents corresponding to the percentage on the sidebar
								int percentageOfAgents = Integer.parseInt(human.getIhm().getSidebar().getLabelSlider().getText());
								for(int i = 0; i < (int)(percentageOfAgents * this.currentHumanBaseSelected.getLife()/100); ++i) {
									human.getIhm().getCenterPanel().getAgentsLayer().createAndSendAgent(human, this.currentHumanBaseSelected, this.currentTargetBaseSelected);
								}
							}
						}
					}
				}// end if
    		}// end while
    	}
	}
	
	/**
	 * Manage towers listener
	 * @param e
	 * 				mouseEvent, useful for the coord
	 * @param towers
	 * 				list of the engine towers
	 * @param human
	 * 				player who has the IHM
	 */
	public void manageTowersListeners(MouseEvent e, CopyOnWriteArrayList<Tower> towers, Human human ){
		//initialize towers list/iterator
		CopyOnWriteArrayList<Tower> tmpTowers = towers;
		final Iterator<Tower> it2 = tmpTowers.iterator();
		
		//iterate on towers list
    	while(it2.hasNext()){
    		final Tower t = it2.next();
    		
    		//create a rect and test if the click of the mouse is in it.
    		Rectangle2D rect = new Rectangle2D.Double((int)(t.getPosition().getX()-7.5), (int)(t.getPosition().getY()-7.5),15,15);
    		if ((e.getButton() == 1) && rect.contains(e.getX(), e.getY()) && t.getPlayer() == human ) {
    			//getting the bottom bar
				BottomBar bottom= human.getIhm().getBottomBar();
				bottom.setCurrentTower(t);
				
				//filling with tower values and make the JPanel visible
				bottom.getTowerStrength().setText(String.valueOf(t.getStrength()));
				bottom.getTowerActionField().setText(String.valueOf(t.getActionField()));
				bottom.getBaseInfos().setVisible(false);
				bottom.getTowerInfos().setVisible(true);
				
			}
    	}
	}

	public Base getCurrentHumanBaseSelected() {
		return currentHumanBaseSelected;
	}

	public void setCurrentHumanBaseSelected(Base currentHumanBaseSelected) {
		this.currentHumanBaseSelected = currentHumanBaseSelected;
	}

	public Base getCurrentTargetBaseSelected() {
		return currentTargetBaseSelected;
	}

	public void setCurrentTargetBaseSelected(Base currentTargetBaseSelected) {
		this.currentTargetBaseSelected = currentTargetBaseSelected;
	}
	
	
}
