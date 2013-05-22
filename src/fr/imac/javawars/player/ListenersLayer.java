package fr.imac.javawars.player;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.dispatcher.ActionTowerUpgrade;
import fr.imac.javawars.engine.Agent;
import fr.imac.javawars.engine.Base;
import fr.imac.javawars.engine.Tower;

/**
 * Class ListenersLayer: handle all listeners on the main panel
 *  
 */
public class ListenersLayer extends JPanel{
	//swing needeed
	private static final long serialVersionUID = 1L;
	
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
	 * @param towers
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
					if(b.getPlayer() == human){
						System.out.println("one of my bases positioned in : " + b.getPosition());
						this.currentHumanBaseSelected = b;
						Point agentPosition = new Point(100, 50);
						human.addAgent(new Agent(agentPosition, human));
						human.getIhm().getCenterPanel().repaint();
		            	//System.out.println("currentHumanBase : " + this.currentHumanBaseSelected.getPlayer().getName());
					}
					else if(b.getPlayer() != null){
						System.out.println("base " + b.getPlayer().getName() + " positioned in : " + b.getPosition());
						this.currentTargetBaseSelected = b;
		            	//System.out.println("currentTargetBase : " + this.currentTargetBaseSelected.getPlayer().getName());
					}
					else{
						System.out.println("neutral base positioned in : " + b.getPosition());
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
				
				//filling with tower values and make the JPanel visible
				bottom.getTowerStrength().setText(String.valueOf(t.getStrength()));
				bottom.getTowerActionField().setText(String.valueOf(t.getActionField()));
				bottom.getTowerInfos().setVisible(true);
				
				//adding listeners on buttons (strength & actionField)
				bottom.getUpStrength().addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e){
			        	System.out.println("+strength");
			        	Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
			    		ActionTowerUpgrade myAction = new ActionTowerUpgrade(player, t,  2);
			    		JavaWars.getDispatcher().addAction(myAction);
			        }
			    });
				
				bottom.getUpActionField().addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e){
			        	// TODO : improve tower actionField, send to dispatcher
			        	System.out.println("+actionField");

			        	Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
			    		ActionTowerUpgrade myAction = new ActionTowerUpgrade(player, t,  1);
			    		JavaWars.getDispatcher().addAction(myAction);
			        }
			    });
			}
    	}
	}
}
