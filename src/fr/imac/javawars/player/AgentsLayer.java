package fr.imac.javawars.player;


import java.awt.Graphics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.dispatcher.ActionAgentSend;
import fr.imac.javawars.dispatcher.ActionTowerCreate;
import fr.imac.javawars.engine.Agent;
import fr.imac.javawars.engine.Base;


/**
 * Class AgentsLayer: manage & display agents 
 */
public class AgentsLayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	AgentsLayer(){
		super();
		this.setBounds(0,0,700,500);
		this.setOpaque(false);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Antialiasing ON
		((Graphics2D)  g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// need to run trough all agents created
		//getting players from engine
		Map<Integer, Player> players = JavaWars.getDispatcher().getPlayers();
		Iterator<Map.Entry<Integer, Player>> it = players.entrySet().iterator();
	
		while(it.hasNext()){
			drawAgentsOfPlayer(it.next().getValue(), g);
		}
	}
		
	public void drawAgentsOfPlayer(Player player, Graphics g){
		LinkedList<Agent> agents = player.getAgents();
		if (agents != null){
			Iterator<Agent> it = agents.iterator();
			while(it.hasNext()){
				Agent agent = it.next();
				AgentDisplay agentDisplay = new AgentDisplay(agent.getPosition(), agent.getPlayer().getPlayerNumber());
				agentDisplay.paintComponent(g);
			}
		}
	}
	
	public void createAndSendAgent(Player player, Base start, Base target){	
		//Human player = (Human)JavaWars.getEngine().getPlayers().get(1);
		
		ActionAgentSend myAction = new ActionAgentSend(player, start, target);

		JavaWars.getDispatcher().addAction(myAction);
		
		repaint();
	}
}