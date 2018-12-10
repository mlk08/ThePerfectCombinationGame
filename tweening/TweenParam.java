package edu.virginia.engine.tweening;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.util.GameClock;

public class TweenParam {
	DisplayObject displayObject;
	double time;
	TweenableParams param;
	double startVal, endVal;
	GameClock gameClock = new GameClock();
	boolean isComplete = false;
	TweenTransitions transition;
	
	
	public TweenParam(DisplayObject sprite, TweenableParams paramToTween, double startVal, double endVal, double time, TweenTransitions transition) {
		this.param = paramToTween;
		this.startVal = startVal;
		this.endVal = endVal;
		this.time = time;
		this.displayObject = sprite;
		gameClock.resetGameClock();
		this.transition = transition;
	}
	
	public TweenableParams getParam() {
		return this.param;
	}
	
	public double getStartVal() {
		return this.startVal;
	}
	
	public double getEndVal() {
		return this.endVal;
	}
	
	public void update() {
		double proportionOfTime = this.transition(gameClock.getElapsedTime() / time);
		
		if(param == TweenableParams.XPOS) {
			displayObject.setPosition((int) (startVal + (endVal - startVal) * proportionOfTime), (int) (displayObject.getPosition()[1]));
		}
		else if(param == TweenableParams.YPOS) {
			displayObject.setPosition((int) (displayObject.getPosition()[0]), (int) (startVal + (endVal - startVal) * proportionOfTime));	
		}
		else if(param == TweenableParams.XSCALE) {
			displayObject.setScale(startVal + (endVal - startVal) * proportionOfTime, displayObject.getScale()[1]);
		}
		else if(param == TweenableParams.YSCALE) {
			displayObject.setScale(displayObject.getScale()[0], startVal + (endVal - startVal) * proportionOfTime);
		}
		else if(param == TweenableParams.ALPHA) {
			displayObject.setAlpha(startVal + (endVal- startVal) * proportionOfTime);
		}
		
		else if(param == TweenableParams.ROTATION) {
			displayObject.setRotation(startVal + (endVal- startVal) * proportionOfTime);
		}
	}
	
	public double transition(double timePercent) {
		switch(transition) {
		case LINEAR:
			return timePercent;
		case QUADRATIC:
			return timePercent * timePercent;
		case QUINT:
			return timePercent * timePercent * timePercent * timePercent * timePercent;
		case SINE:
			return Math.sin(timePercent);
		
			default:
				return timePercent;
		}
		
	}
	
	public boolean isComplete() {
		return gameClock.getElapsedTime() >= time;
	}
}