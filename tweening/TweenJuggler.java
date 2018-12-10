package edu.virginia.engine.tweening;

import java.util.ArrayList;
import java.util.Iterator;

import edu.virginia.engine.event.Event;



public class TweenJuggler {
	static ArrayList<Tween> jugglerList = new ArrayList<Tween> ();
	
	public static void add(Tween t) {
		jugglerList.add(t);
	}
	
	public void update() {
		ArrayList<Tween> done = new ArrayList<Tween>();
		for(Iterator<Tween> i = jugglerList.iterator(); i.hasNext();) {
			Tween t = i.next();
			t.update();
			if(t.isComplete()) {
				done.add(t);
				i.remove();
			}
		}
		
		for(Tween t : done) t.dispatchEvent(new Event("TWEEN_COMPLETE_EVENT", t));
	}	
}