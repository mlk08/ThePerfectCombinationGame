package edu.virginia.engine.display;

public class PhysicsSprite extends AnimatedSprite {
	
	public double currentVelocity[] = {0.0, 0.0};
	public boolean isFalling = true;
	public boolean isJumping = false;
	
	private double gravity = 0.02;
	private double maxAcceleration = 0.02;
	private double maxSpeed = 1.0;
	
	public PhysicsSprite(String id, String fileName, boolean isOnePic) {
		super(id, fileName, isOnePic);
	}
	
	public double[] getCurrentVelocity() {
		return this.currentVelocity;
	}
	
	
	public void setCurrentVelocity(double x, double y) {
		this.currentVelocity[0] = x;
		this.currentVelocity[1] = y;
	}
	
	public void increaseCurrentVelocity(double x, double y) {
		this.currentVelocity[0] = this.currentVelocity[0] + x;
		this.currentVelocity[1] = this.currentVelocity[1] + y;
	}
	
	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}
	
	public double getGravity() {
		return this.gravity;
	}
	
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}
	
	public double getMaxAcceleration() {
		return this.maxAcceleration;
	}
	
	public void setMaxAcceleration(double acc) {
		this.maxAcceleration = acc;
	}
	
	public double getMaxSpeed() {
		return this.maxSpeed;
	}
	
	public void setMaxSpeed(double max) {
		this.maxSpeed = max;
	}
}