package edu.virginia.thePerfectCombination;

import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PhysicsSprite;

public class Dagger extends AnimatedSprite {

		int damage = 5;

		public Dagger(String id) {
		super(id);
		this.setImage("dagger.png");
		this.setScale(0.25, 0.25);
		this.setPosition(-5, 25);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
		
		if(this.getParent() != null && !(this.getParent().getScale()[1] == 1.1)) {
			PhysicsSprite temp = (PhysicsSprite) this.getParent();
			temp.setGravity(0.02);
			temp.setScale(1.1, 1.1);
			temp.setMaxAcceleration(0.02);
			temp.setMaxSpeed(1.0);
			temp.setHealth(250);
			System.out.println("inside dagger's update");
		}

	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return this.damage;
	}

}
