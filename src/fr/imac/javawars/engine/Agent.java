package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.player.Player;

public class Agent extends AbstractUnite {
	private int speed;

	//constructor
	public Agent(int life, Point position, Player player, int speed) {
		super(life, position, player);
		this.speed = speed;
	}
	
	//methods
	public void changePosition(Base base) {
		int[] distanceMap = base.getDistanceMap();
		int width = distanceMap.length;
		// point of start of the agent
		int currentAgentPosition = (int) (this.getPosition().getX() + this.getPosition().getY() * width);
		// while the agent isn't on a case which value is zero (on the base)
		while(distanceMap[currentAgentPosition] > 0) {
			double currentXagentPosition = this.getPosition().getX();
			double currentYagentPosition = this.getPosition().getY();
			
			// if the box next to the agent is closer of the base, he moves on it
			// we test every neigbor box
			if(distanceMap[currentAgentPosition - 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition);
			}
			if(distanceMap[currentAgentPosition + 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition);
			}
			if(distanceMap[currentAgentPosition - width] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition, currentYagentPosition - 1);
			}
			if(distanceMap[currentAgentPosition + width] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition, currentYagentPosition + 1);
			}
			if(distanceMap[currentAgentPosition - width - 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition - 1);
			}
			if(distanceMap[currentAgentPosition - width + 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition - 1);
			}
			if(distanceMap[currentAgentPosition + width - 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition - 1, currentYagentPosition + 1);
			}
			if(distanceMap[currentAgentPosition + width + 1] < distanceMap[currentAgentPosition]) {
				this.position.setLocation(currentXagentPosition + 1, currentYagentPosition + 1);
			}
			// update currentAgentPosition
			currentAgentPosition = (int) (this.getPosition().getX() + this.getPosition().getY() * width);
		}
	}
	
	public void destroyAgent(){
	}
	
	//getters/setters
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
