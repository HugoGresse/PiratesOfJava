package fr.imac.javawars.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import fr.imac.javawars.JavaWars;
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
		// need to run trough all agents created
		//getting bases from engine
		//VERIFIER QUE C'EST BON
		Map<Integer, Player> agentPositions = JavaWars.getDispatcher().getPlayers();
		Iterator<Map.Entry<Integer, Player>> it = agentPositions.entrySet().iterator();
	
		while(it.hasNext()){
			drawAgentsOfPlayer(it.next().getValue(), g);
		}
	}
		
	public void drawAgentsOfPlayer(Player player, Graphics g){
		//pas bien d'importer Agent ?
		LinkedList<Agent> agents = player.getAgents();
		if (agents != null){
			Iterator<Agent> it = agents.iterator();
			while(it.hasNext()){
				Agent agent = it.next();
				AgentDisplay agentDisplay = new AgentDisplay(agent.getPosition());
				agentDisplay.paintComponent(g);
			}
		}
	}
}