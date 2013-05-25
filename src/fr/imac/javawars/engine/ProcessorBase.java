package fr.imac.javawars.engine;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.imac.javawars.dispatcher.Action;

public class ProcessorBase {
	
	private double beginTime = 0; 
	
	public ProcessorBase(){
		
	}
	
	/**
	 * process the base to add life to it
	 * @param bases
	 * 					the bases
	 */
	public boolean process(CopyOnWriteArrayList<Base> bases, double newTime){
		if(beginTime == 0)
			beginTime = newTime;
		
		Iterator<Base> itr = bases.iterator();
		boolean change = false;
		while(itr.hasNext()){
			Base base = itr.next();
			
			//if(base.getPlayer() == null) return false;
			
			//If time to short before previous addLife
			if(newTime - beginTime < base.getSpeedRegeneration())
				return false;
			
			base.addLife(1);
			
		}
		beginTime = newTime;
		
		return true;
		
		
	}
}
