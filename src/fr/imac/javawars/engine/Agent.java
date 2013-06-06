package fr.imac.javawars.engine;

import java.awt.Point;

import fr.imac.javawars.engine.Base.Power;
import fr.imac.javawars.player.Player;

public class Agent extends AbstractUnite {
	private int speed;
	private Base baseStart;
	private Base baseTarget;
	private Power power;
	private boolean isFreeze = false;
	private boolean isFreezeMove = false;

	//constructor
	public Agent(float life, Point position, Player player, int speed, Base start, Base target, Power power) {
		super(life, position, player);
		this.speed = speed;
		this.baseStart = start;
		this.baseTarget = target;
		this.power = power;
		switch(power){
			case SPEED_UP : this.speed = 2;
				break;
			case LIFE_UP : this.life = 150;
				break;
			default:
				break;
		}
	}
	
	public Agent(Point position, Player player){
		this(100, position, player, 1, null, null, Power.NORMAL);
	}
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((baseStart == null) ? 0 : baseStart.hashCode());
		result = prime * result
				+ ((baseTarget == null) ? 0 : baseTarget.hashCode());
		result = prime * result + speed;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Agent))
			return false;
		Agent other = (Agent) obj;
		if (baseStart == null) {
			if (other.baseStart != null)
				return false;
		} else if (!baseStart.equals(other.baseStart))
			return false;
		if (baseTarget == null) {
			if (other.baseTarget != null)
				return false;
		} else if (!baseTarget.equals(other.baseTarget))
			return false;
		if (speed != other.speed)
			return false;
		return true;
	}

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
	
	/**
	 * Remove the strenght from agent after applied Power
	 * @param strenght the strenght you want the agent to lose
	 */
	@Override
	public void loseLife(float strenght) {
		if(this.getBaseStart().getPower() == Power.RESISTANCE){
			super.loseLife(strenght/2);
		}else{
			super.loseLife(strenght);
		}
	}
	
	public Power getPower(){
		return this.power;
	}

	public boolean isFreeze() {
		return isFreeze;
	}

	public void setFreeze(boolean isFreeze) {
		this.isFreeze = isFreeze;
	}

	public boolean isFreezeMove() {
		return isFreezeMove;
	}

	public void setFreezeMove(boolean isFreezeMove) {
		this.isFreezeMove = isFreezeMove;
	}
	
	
}
