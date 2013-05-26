package fr.imac.javawars.dispatcher;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.engine.Base;
import fr.imac.javawars.engine.Tower;
import fr.imac.javawars.player.BottomBar;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.Player;
/**
 * 
 * @author Hugo
 *
 */
public class Dispatcher {
	

	private ConcurrentLinkedQueue<Action> actions = new ConcurrentLinkedQueue<Action>();
	
	public Dispatcher() {
		
	}
		
	
	/**
	 * Add action 
	 * @param action
	 * 				The action to add
	 */
	public void addAction(Action action){
		
		actions.add(action);
	}
	
	/**
	 * Get the actions queue (to perform, should be called on Engine)
	 * @return An action linked queue with action 
	 */
	public ConcurrentLinkedQueue<Action> getAction(){
		return actions;
	}
	
	/**
	 * update player's infos
	 */
	public void updatePlayers(){
		Iterator<Map.Entry<Integer, Player>> it = JavaWars.getEngine().getPlayers().entrySet().iterator();
		
		while (it.hasNext()) {
			  Map.Entry<Integer, Player> entry = it.next();
			  entry.getValue().update();
		}
		
	}
	
	/**
	 * Repaint the AgentsLayers
	 * Call it in Engine
	 */
	public void repaintAgents(){
		((Human) JavaWars.getEngine().getPlayers().get(1)).getIhm().getCenterPanel().getAgentsLayer().drawBufferedImage();
	}
	
	/**
	 * Repaint the TowersLayers
	 * Call it in Engine
	 */
	public void repaintTowers(){
		((Human) JavaWars.getEngine().getPlayers().get(1)).getIhm().getCenterPanel().getTowersLayer().drawBufferedImage();
	}
	
	/**
	 * Repaint the GroundLayers
	 * Call it in Engine
	 */
	public void repaintGround(){
		((Human) JavaWars.getEngine().getPlayers().get(1)).getIhm().getCenterPanel().getGroundLayer().drawBufferedImage();
	}
	
	/**
	 * Repaint the BaseLayers
	 * Call it in Engine
	 */
	public void repaintBase(){
		((Human) JavaWars.getEngine().getPlayers().get(1)).getIhm().getCenterPanel().getBasesLayer().drawBufferedImage();
	}
	
	/**
	 * Getting the map for the initialization of the game
	 */
	public int[][] getGround(){
		return JavaWars.getEngine().getGround().getBitMap();
	}
	
	/**
	 *  Getting the map of influence of bases
	 */
	public int[][] getBasesManager(){
		return JavaWars.getEngine().getBasesManager().getInfluenceAreaBitMap();
	}
	
	/**
	 * Getting bases of the game
	 */
	public CopyOnWriteArrayList<Base> getBases(){
		return JavaWars.getEngine().getBases();
	}
	
	/**
	 * Getting towers of the game
	 */
	public CopyOnWriteArrayList<Tower> getTowers(){
		return JavaWars.getEngine().getTowers();
	}
	
	/**
	 * Getting errors
	 */
	public String getError(){
		String error = JavaWars.getEngine().getError();
		JavaWars.getEngine().setError(null);
		return error;
	}
	
	/**
	 *setting autoAddLife on bases
	 */
	public void setAutoAddLife(){
		CopyOnWriteArrayList<Base> bases = JavaWars.getEngine().getBases();
		Iterator<Base> it = bases.iterator();
		
		while(it.hasNext()){
			Base b = it.next();
			b.autoAddLife();
		}
	}
	
	/**
	 * Getting players of the game
	 */
	public Map<Integer, Player> getPlayers(){
		return JavaWars.getEngine().getPlayers();
	}
}
