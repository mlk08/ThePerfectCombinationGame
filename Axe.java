package edu.virginia.thePerfectCombination;

import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParams;
import edu.virginia.engine.util.GameClock;

public class Axe extends AnimatedSprite {
	
	int health = 300;
	int damage = 15;
	int coolDown = 1000;
	
	Tween caliburDownTween = new Tween(this);
	Tween caliburUpTween = new Tween(this);
	
	boolean notYetReversed = false;
	boolean isCoolDown = false;
	
	GameClock cooldown = new GameClock();
	
	SoundManager mySound = new SoundManager();
	
	public Axe(String id) {
		super(id);
		this.setImage("axe.png");
		this.setScale(0.05, 0.05);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
		
		if(this.getParent() != null && !(this.getParent().getScale()[1] == 1.5)) {
			PhysicsSprite temp = (PhysicsSprite) this.getParent();
			temp.setScale(1.5, 1.5);
			temp.setMaxAcceleration(0.01);
			temp.setHealth(health);
			System.out.println("inside axe's update");
			this.setPosition(-5, 35);
			this.setPivotPoint(146, 1500);
			this.setRotation(-70);	
		}
		
		if(caliburDownTween.isComplete() && this.notYetReversed) {
			notYetReversed = false;
			System.out.println("should go up now");
			caliburUpTween.animate(TweenableParams.ROTATION, 28, -70, this.getCoolDown() / 2, TweenTransitions.LINEAR);
			TweenJuggler.add(caliburUpTween);
		}

	}
	
	public void attack() {
		if(!this.isOnCooldown()) {
			mySound.PlaySoundEffect("AXE");
			isCoolDown = true;
			notYetReversed = true;
			caliburDownTween.animate(TweenableParams.ROTATION, -70, 28, this.getCoolDown() / 2, TweenTransitions.QUINT);
			TweenJuggler.add(caliburDownTween);
		}
		else {
			System.out.println("still on cooldown");
		}
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
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return this.damage;
	}
}
