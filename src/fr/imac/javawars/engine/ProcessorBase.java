package fr.imac.javawars.engine;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProcessorBase {
	
	/**
	 * Used to detecte if we some life Base change
	 */
	boolean change = false;
	
	public ProcessorBase(){
		
	}
	
	/**
	 * process the base to add life to it
	 * @param bases
	 * 					the bases
	 */
	public boolean process(CopyOnWriteArrayList<Base> bases, double newTime){
		
		Iterator<Base> itr = bases.iterator();
		while(itr.hasNext()){
			Base base = itr.next();
			
			change = base.checkAndAddLife(newTime);
			
		}
		
		return true;
		
		
	}
}
