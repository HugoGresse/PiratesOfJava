package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.Ihm;
import fr.imac.javawars.player.Player;

public class Agent extends AbstractUnite {
	private int speed;
	private Base baseStart;
	private Base baseTarget;

	//constructor
	public Agent(int life, Point position, Player player, int speed, Base start, Base target) {
		super(life, position, player);
		this.speed = speed;
		this.baseStart = start;
		this.baseTarget = target;
	}
	
	public Agent(Point position, Player player){
		this(100, position, player, 1, null, null);
	}
	
	//METHODS
	/*public void sendToBase(Base base) {
		int[] distanceMap = base.getDistanceMap();
		JavaWars.getEngine().getGround();
		int width = Ground.getWinWidth();
		System.out.println(width);
		// point of start of the agent
		int currentAgentPosition = (int) (this.getPosition().getX() + this.getPosition().getY() * width);
		// while the agent isn't on a case which value is zero (on the base)
		int i = 0;
		while(distanceMap[currentAgentPosition] > 0 && i < distanceMap.length) {
			//System.out.println("distanceMap[currentAgentPosition] :" + distanceMap[currentAgentPosition] );
			double currentXagentPosition = this.getPosition().getX();
			//System.out.println("currentXagentPosition " + currentXagentPosition );
			double currentYagentPosition = this.getPosition().getY();
			//System.out.println("currentYagentPosition " + currentYagentPosition );
			
			// if the box next to the agent is closer of the base, he moves on it
			// we test every neigbor box
//			System.out.println("distanceMap[currentAgentPosition - 1]" + distanceMap[currentAgentPosition - 1]);
//			System.out.println("distanceMap[currentAgentPosition + 1]" + distanceMap[currentAgentPosition + 1]);
//			System.out.println("distanceMap[currentAgentPosition - width]" + distanceMap[currentAgentPosition - width]);
//			System.out.println("distanceMap[currentAgentPosition + width]" + distanceMap[currentAgentPosition + width]);
//			System.out.println("distanceMap[currentAgentPosition - width - 1]" + distanceMap[currentAgentPosition - width - 1]);
//			System.out.println("distanceMap[currentAgentPosition - width + 1]" + distanceMap[currentAgentPosition - width + 1]);
//			System.out.println("distanceMap[currentAgentPosition + width - 1]" + distanceMap[currentAgentPosition + width - 1]);
//			System.out.println("distanceMap[currentAgentPosition + width + 1]" + distanceMap[currentAgentPosition + width + 1]);
			//used to access the ihm and update display
			Human human =(Human)JavaWars.getDispatcher().getPlayers().get(1);
			
			if(distanceMap[currentAgentPosition - 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition - 1] >= 0) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 1");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition + 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition + 1] >= 0) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 2");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition - width] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition - width] >= 0) {
				this.position.setLocation(currentXagentPosition, currentYagentPosition - 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 3");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition + width] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition + width] >= 0) {
				this.position.setLocation(currentXagentPosition, currentYagentPosition + 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 4");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition - width - 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition - width - 1] >= 0) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition - 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 5");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition - width + 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition - width + 1] >= 0) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition - 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 6");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition + width - 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition + width - 1] >= 0) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition + 1);
				human.getIhm().getCenterPanel().getAgentsLayer().repaint();
//				System.out.println("Yo 7");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition + width + 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition + width + 1] >= 0) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition + 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 8");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			// update currentAgentPosition
			currentAgentPosition = (int) (this.getPosition().getX() + this.getPosition().getY() * width);
			//System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			try {
		        Thread.sleep(5);
		     } catch (InterruptedException e) {
		        e.printStackTrace();
		     }

			human.getIhm().getCenterPanel().getAgentsLayer().repaint();
			i++;
		}
	}*/
	
	/**
	 * Update the position of the agent.
	 * @return 
	 * 			return true is the agent is arrived
	 */
	public boolean updatePosition(){
		int[] distanceMap = this.baseTarget.getDistanceMap();
		int width = Ground.getWinWidth();
		// point of start of the agent
		int currentAgentPosition = (int) (this.getPosition().getX() + this.getPosition().getY() * width);
		// while the agent isn't on a case which value is zero (on the base)
		int i = 0;
		if(distanceMap[currentAgentPosition] > 0 && i < distanceMap.length) {
			
			double currentXagentPosition = this.getPosition().getX();
			double currentYagentPosition = this.getPosition().getY();
			
			// if the box next to the agent is closer of the base, he moves on it
			// we test every neigbor box
	
			if(distanceMap[currentAgentPosition - 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition - 1] >= 0) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition);
			}
			else if(distanceMap[currentAgentPosition + 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition + 1] >= 0) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition);
			}
			else if(distanceMap[currentAgentPosition - width] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition - width] >= 0) {
				this.position.setLocation(currentXagentPosition, currentYagentPosition - 1);
			}
			else if(distanceMap[currentAgentPosition + width] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition + width] >= 0) {
				this.position.setLocation(currentXagentPosition, currentYagentPosition + 1);
			}
			else if(distanceMap[currentAgentPosition - width - 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition - width - 1] >= 0) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition - 1);
			}
			else if(distanceMap[currentAgentPosition - width + 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition - width + 1] >= 0) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition - 1);
			}
			else if(distanceMap[currentAgentPosition + width - 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition + width - 1] >= 0) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition + 1);
			}
			else if(distanceMap[currentAgentPosition + width + 1] < distanceMap[currentAgentPosition] && distanceMap[currentAgentPosition + width + 1] >= 0) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition + 1);
			}
			//System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			return false;
		}
		else {
			//the agent is arrived at destination
			return true;
		}
	}
	
	/*public void moveToAnotherBase(Base baseStart, Base baseTarget, int nbAgentsMoving){
		baseStart.nbAgents -= 1;
		changePosition();
		if(agent.position == baseTarget.position){
			baseTarget.nbAgents +=1
		}
	}*/
	
	//getters/setters
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Base getBaseStart() {
		return baseStart;
	}

	public void setBaseStart(Base baseStart) {
		this.baseStart = baseStart;
	}

	public Base getBaseTarget() {
		return baseTarget;
	}

	public void setBaseTarget(Base baseTarget) {
		this.baseTarget = baseTarget;
	}
	
	
}
