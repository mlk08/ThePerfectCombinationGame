package edu.virginia.engine.display;


import java.util.ArrayList;

import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParams;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */
public class Sprite extends DisplayObjectContainer {
	
	public int health;
	public int maxHealth;
	
	public Sprite(String id) {
		super(id);
	}

	public Sprite(String id, String imageFileName) {
		super(id);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		System.out.println("setting health");
		this.maxHealth = health;
		this.health = health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public void setMaxHealth(int health) {
		this.maxHealth = health;
	}
	
	
	
	public void takeDamage(int damage) {
		Tween spriteTween = new Tween(this);
		spriteTween.animate(TweenableParams.ALPHA, 0.0, 1.0, 200, TweenTransitions.LINEAR);
		TweenJuggler.add(spriteTween);
		this.health -= damage;
	}
}