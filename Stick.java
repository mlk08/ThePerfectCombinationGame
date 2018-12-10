package edu.virginia.thePerfectCombination;

import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParams;
import edu.virginia.engine.util.GameClock;

public class Stick extends AnimatedSprite {
	
	int health = 320;
	int damage = 10;
	int coolDown = 500;
	Tween stickTween;
	Tween caliburUpTween;
	
	PhysicsSprite temp;
	
	boolean notYetReversed = false;
	boolean isCoolDown = false;
	
	GameClock cooldown = new GameClock();
	
	SoundManager mySound = new SoundManager();

	
	public Stick(String id) {
		super(id);
		this.setImage("stick.png");
		this.setScale(0.1, 0.16);
		//this.setRotation(-25.0);
		this.setPosition(-2, 36);
		this.setPivotPoint(445, 35);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
		
		
		if(this.getParent() != null && !(this.getParent().getScale()[1] == 1.9)) {
			temp = (PhysicsSprite) this.getParent();
			stickTween = new Tween(temp);
			caliburUpTween = new Tween(temp);
			//temp.setGravity(0.08);
			temp.setScale(1.9, 1.9);
			temp.setMaxAcceleration(0.01);
			//temp.setMaxSpeed(0.1);
			temp.setHealth(health);
			//temp.setPivotPoint(25, 0);
			System.out.println("inside calibur's update");
			
			//this.setRotation(-70);
		}
		
	
		if(stickTween.isComplete() && this.notYetReversed) {
			notYetReversed = false;
			System.out.println("should go up now");
			caliburUpTween.animate(TweenableParams.XSCALE, 1.9, -1.9, coolDown / 2, TweenTransitions.LINEAR);

			TweenJuggler.add(caliburUpTween);
		}
		
		if(caliburUpTween.isComplete()) {
			isCoolDown = false;
		}
	
	}
	
	public void attack() {
		if(!this.isOnCooldown()) {
			mySound.PlaySoundEffect("STICK");
			isCoolDown = true;
			notYetReversed = true;
			stickTween.animate(TweenableParams.XSCALE, -1.9, 1.9, coolDown / 2, TweenTransitions.LINEAR);
			TweenJuggler.add(stickTween);
			//temp.setScale(-temp.getScale()[0], temp.getScale()[1]);
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
