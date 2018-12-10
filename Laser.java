package edu.virginia.thePerfectCombination;

import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParams;
import edu.virginia.engine.util.GameClock;

public class Laser extends AnimatedSprite {
	
	int health = 260;
	int damage = 8;
	int coolDown = 1000;
	Tween caliburDownTween = new Tween(this);
	Tween caliburUpTween = new Tween(this);
	
	boolean notYetReversed = false;
	boolean isCoolDown = false;
	
	GameClock cooldown = new GameClock();
	
	SoundManager mySound = new SoundManager();

	
	
	public Laser(String id) {
		super(id);
		this.setImage("vader-laser.png");
		this.setScale(0.001, 0.08);
		//this.setRotation(0);
		this.setPosition(20, 6);
		this.setPivotPoint(310, 0);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
		
		
		if(this.getParent() != null && !(this.getParent().getScale()[1] == 1.7)) {
			PhysicsSprite temp = (PhysicsSprite) this.getParent();
			//temp.setGravity(0.08);
			temp.setScale(1.7, 1.7);
			temp.setMaxAcceleration(0.01);
			//temp.setMaxSpeed(0.1);
			temp.setHealth(health);
			//temp.setPivotPoint(25, 0);
			System.out.println("inside calibur's update");
			
			//this.setRotation(-70);
		}
		
		if(caliburDownTween.isComplete() && this.notYetReversed) {
			notYetReversed = false;
			System.out.println("should go up now");
			caliburUpTween.animate(TweenableParams.XSCALE, 0.05, 0.001, coolDown / 2, TweenTransitions.LINEAR);
			TweenJuggler.add(caliburUpTween);
			isCoolDown = false;
		}
		
		/**
		if(caliburUpTween.isComplete()) {
			isCoolDown = false;
		}
		*/
	}
	
	public void attack() {
		if(!this.isOnCooldown()) {
			mySound.PlaySoundEffect("LASER");
			isCoolDown = true;
			notYetReversed = true;
			caliburDownTween.animate(TweenableParams.XSCALE, 0.001, 0.05, coolDown / 2, TweenTransitions.LINEAR);
			TweenJuggler.add(caliburDownTween);
		}
		else {
			System.out.println("still on cooldown");
		}
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return this.damage;
	}
	
	private boolean isOnCooldown() {
		if(this.cooldown.getElapsedTime() > this.coolDown) {
			this.cooldown.resetGameClock();
			return false;
		}
		return true;
	}
	
	public int getCoolDown() {
		return this.coolDown;
	}
	
	public boolean isCooldown() {
		return this.isCoolDown;
	}
}
