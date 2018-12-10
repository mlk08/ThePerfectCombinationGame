package edu.virginia.thePerfectCombination;

import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParams;
import edu.virginia.engine.util.GameClock;

public class Magic extends AnimatedSprite {
	
	int damage = 8;
	int distance = 1000;
	int coolDown = 1000;
	Tween caliburDownTween = new Tween(this);
	
	boolean notYetReversed = false;
	boolean isCoolDown = false;
	PhysicsSprite source;
	
	GameClock cooldown = new GameClock();
	
	SoundManager mySound = new SoundManager();
	
	public Magic(String id, PhysicsSprite source) {
		super(id);
		this.source = source;
		this.setImage("fireball-new.png");
		this.setPivotPoint(50, 50);
		this.setVisible(false);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);

		if(caliburDownTween.isComplete() && this.notYetReversed) {
			this.setVisible(false);
			notYetReversed = false;
			isCoolDown = false;
		}
	}
	
	public void attack(boolean facingLeft) {
		if(!this.isOnCooldown()) {
			this.setVisible(true);
			this.setPosition(source.getPosition()[0], source.getPosition()[1]);
			mySound.PlaySoundEffect("FIRE");
			isCoolDown = true;
			notYetReversed = true;
			
			if(facingLeft) {
				caliburDownTween.animate(TweenableParams.XPOS, source.getPosition()[0], source.getPosition()[0] - distance, coolDown, TweenTransitions.LINEAR);
			}
			else {
				caliburDownTween.animate(TweenableParams.XPOS, source.getPosition()[0], source.getPosition()[0] + distance, coolDown, TweenTransitions.LINEAR);
			}
			TweenJuggler.add(caliburDownTween);
		}
		else {
			System.out.println("still on cooldown");
		}
	}
	
	public void attackUp() {
		if(!this.isOnCooldown()) {
			this.setVisible(true);
			this.setPosition(source.getPosition()[0], source.getPosition()[1]);
			mySound.PlaySoundEffect("FIRE");
			isCoolDown = true;
			notYetReversed = true;
			caliburDownTween.animate(TweenableParams.YPOS, source.getPosition()[1], source.getPosition()[1] - 300, coolDown, TweenTransitions.LINEAR);
			TweenJuggler.add(caliburDownTween);
		}
		else {
			System.out.println("still on cooldown");
		}
	}
	
	public void attackDiagonal(boolean facingLeft) {
		if(!this.isOnCooldown()) {
			this.setVisible(true);
			this.setPosition(source.getPosition()[0], source.getPosition()[1]);
			mySound.PlaySoundEffect("FIRE");
			isCoolDown = true;
			notYetReversed = true;
			if(facingLeft) {
				caliburDownTween.animate(TweenableParams.XPOS, source.getPosition()[0], source.getPosition()[0] - distance - 100, coolDown * 2, TweenTransitions.LINEAR);
				caliburDownTween.animate(TweenableParams.YPOS, source.getPosition()[1], source.getPosition()[1] - distance - 100, coolDown * 2, TweenTransitions.LINEAR);
			}
			else {
				caliburDownTween.animate(TweenableParams.XPOS, source.getPosition()[0], source.getPosition()[0] + distance + 100, coolDown * 2, TweenTransitions.LINEAR);
				caliburDownTween.animate(TweenableParams.YPOS, source.getPosition()[1], source.getPosition()[1] - distance - 100, coolDown * 2, TweenTransitions.LINEAR);
			}
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
