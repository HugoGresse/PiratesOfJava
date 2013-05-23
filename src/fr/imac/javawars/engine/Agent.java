package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.JavaWars;
import fr.imac.javawars.player.Human;
import fr.imac.javawars.player.Ihm;
import fr.imac.javawars.player.Player;

public class Agent extends AbstractUnite {
	private int speed;

	//constructor
	public Agent(int life, Point position, Player player, int speed) {
		super(life, position, player);
		this.speed = speed;
	}
	
	public Agent(Point position, Player player){
		this(100, position, player, 1);
	}
	
	//METHODS
	
	public void sendToBase(Base base) {
		int[] distanceMap = base.getDistanceMap();
		int width = 700;
		System.out.println("YOOOOOOOOOOOOOOoo");
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
			/*System.out.println("distanceMap[currentAgentPosition - 1]" + distanceMap[currentAgentPosition - 1]);
			System.out.println("distanceMap[currentAgentPosition + 1]" + distanceMap[currentAgentPosition + 1]);
			System.out.println("distanceMap[currentAgentPosition - width]" + distanceMap[currentAgentPosition - width]);
			System.out.println("distanceMap[currentAgentPosition + width]" + distanceMap[currentAgentPosition + width]);
			System.out.println("distanceMap[currentAgentPosition - width - 1]" + distanceMap[currentAgentPosition - width - 1]);
			System.out.println("distanceMap[currentAgentPosition - width + 1]" + distanceMap[currentAgentPosition - width + 1]);
			System.out.println("distanceMap[currentAgentPosition + width - 1]" + distanceMap[currentAgentPosition + width - 1]);
			System.out.println("distanceMap[currentAgentPosition + width + 1]" + distanceMap[currentAgentPosition + width + 1]);
			*/
			/*used to access the ihm and update display*/
			Human human =(Human)JavaWars.getDispatcher().getPlayers().get(1);
			
			if(distanceMap[currentAgentPosition - 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 1");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition + 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 2");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition - width] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition, currentYagentPosition - 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 3");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition + width] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition, currentYagentPosition + 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 4");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition - width - 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition - 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 5");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition - width + 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition - 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 6");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition + width - 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition + 1);
				human.getIhm().getCenterPanel().getAgentsLayer().repaint();
//				System.out.println("Yo 7");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			else if(distanceMap[currentAgentPosition + width + 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition + 1);
				human.getIhm().getCenterPanel().repaint();
//				System.out.println("Yo 8");
//				System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			}
			// update currentAgentPosition
			currentAgentPosition = (int) (this.getPosition().getX() + this.getPosition().getY() * width);
			System.out.println("current agent position : " + this.getPosition().getX() + ", " + this.getPosition().getY() );
			try {
		        Thread.sleep(10);
		     } catch (InterruptedException e) {
		        e.printStackTrace();
		     }

			human.getIhm().getCenterPanel().getAgentsLayer().repaint();
			i++;
		}
	}
	
	public void destroyAgent(){
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
}
