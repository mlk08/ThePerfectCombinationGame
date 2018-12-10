package edu.virginia.thePerfectCombination;

import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParams;
import edu.virginia.engine.util.GameClock;

public class Calibur extends AnimatedSprite {
	
	int health = 275;
	int damage = 20;
	int coolDown = 500;
	Tween caliburDownTween = new Tween(this);
	Tween caliburUpTween = new Tween(this);
	
	boolean notYetReversed = false;
	boolean isCoolDown = false;
	
	GameClock cooldown = new GameClock();
	
	SoundManager mySound = new SoundManager();
	
	public Calibur(String id) {
		super(id);
		this.setImage("soul-calibur.png");
		this.setScale(0.26, 0.21);
		this.setRotation(-25.0);
		this.setPosition(-21, 49);
		this.setPivotPoint(100, 105);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
		
		
		if(this.getParent() != null && !(this.getParent().getScale()[1] == 2.3)) {
			PhysicsSprite temp = (PhysicsSprite) this.getParent();
			temp.setScale(2.3, 2.3);
			temp.setMaxAcceleration(0.01);
			temp.setHealth(health);
			System.out.println("inside calibur's update");
		}
		
		if(caliburDownTween.isComplete() && this.notYetReversed) {
			notYetReversed = false;
			System.out.println("should go up now");
			caliburUpTween.animate(TweenableParams.ROTATION, this.getRotation(), -25, coolDown / 2, TweenTransitions.LINEAR);
			TweenJuggler.add(caliburUpTween);
		}
	}
	
	public void attack() {
		if(!this.isOnCooldown()) {
			mySound.PlaySoundEffect("CALIBUR");
			isCoolDown = true;
			notYetReversed = true;
			caliburDownTween.animate(TweenableParams.ROTATION, -25, 0, coolDown / 2, TweenTransitions.SINE);
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
}
