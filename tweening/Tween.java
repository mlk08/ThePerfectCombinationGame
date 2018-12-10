package edu.virginia.engine.tweening;

import java.util.ArrayList;
import java.util.Iterator;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.event.EventDispatcher;



public class Tween extends EventDispatcher {
	ArrayList<TweenParam> list = new ArrayList<TweenParam>();
	DisplayObject sprite;
	
	public Tween(DisplayObject object) {
		this.sprite = object;
	}
	
	public Tween(DisplayObject object, TweenTransitions transition) {
		this.sprite = object;
	}
	
	public void animate(TweenableParams fieldToAnimate, double startVal, double endVal, double time, TweenTransitions transition) {
		TweenParam temp = new TweenParam(sprite, fieldToAnimate, startVal, endVal, time, transition);
		this.list.add(temp);
	}
	
	public void update() {
		
		for(Iterator<TweenParam> i = list.iterator(); i.hasNext();) {
			TweenParam temp = i.next();
			temp.update();
			
			if(temp.isComplete()) {
				i.remove();
			}
		}
	}
	
	public boolean isComplete() {
		return list.isEmpty();
	}	
}