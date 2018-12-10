package edu.virginia.thePerfectCombination;

import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PhysicsSprite;

public class Book extends AnimatedSprite {
	
	public Book(String id) {
		super(id);
		this.setImage("book.jpg");
		this.setScale(0.08, 0.08);
		this.setRotation(30);
		this.setPosition(-5, 15);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
		
		if(this.getParent() != null && !(this.getParent().getScale()[1] == 0.8)) {
			PhysicsSprite temp = (PhysicsSprite) this.getParent();
			temp.setGravity(0.03);
			temp.setScale(0.8, 0.8);
			temp.setMaxAcceleration(0.01);
			temp.setMaxSpeed(0.2);
			temp.setHealth(150);
			System.out.println("inside book's update");
		}
	}

}
