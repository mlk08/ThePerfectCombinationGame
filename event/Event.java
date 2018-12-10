package edu.virginia.engine.event;


public class Event {
	
	 public static final String COIN_PICKED_UP = "COIN_PICKED_UP";
	
	String eventType;
	IEventDispatcher source;
	
	public Event(String eventType, IEventDispatcher source) {
		this.eventType = eventType;
		this.source = source;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String type) {
		this.eventType = type;
	}
	
	public IEventDispatcher getSource() {
		return source;
	}
	
	public void setSource(IEventDispatcher source) {
		this.source = source;
	}
	
// If your event needs more information (like questId, or timestamp, then you would extend this class 
// and add the additional information to that new event type.
}