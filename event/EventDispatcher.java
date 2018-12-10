package edu.virginia.engine.event;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class EventDispatcher implements IEventDispatcher {
	
	Hashtable<String, ArrayList<IEventListener>> observers = new Hashtable<String, ArrayList<IEventListener>>();

	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		if(observers.containsKey(eventType)) {
			observers.get(eventType).add(listener);
	    } 
		
		else {
			observers.put(eventType, new ArrayList<>());
	        observers.get(eventType).add(listener);
	    }
	}

	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		if(observers.containsKey(eventType)) {
			observers.get(eventType).remove(listener);
		}
	}

	@Override
	public void dispatchEvent(Event event) {
		if(observers.containsKey(event.getEventType())) {
			for(Iterator<IEventListener> i = observers.get(event.getEventType()).iterator(); i.hasNext();) {
				IEventListener o = i.next();    
				o.handleEvent(event);
			}
		}
	}

	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		return observers.containsKey(eventType) && observers.get(eventType).contains(listener);
	}
}