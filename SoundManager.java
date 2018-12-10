package edu.virginia.thePerfectCombination;

import java.io.File;

import edu.virginia.engine.event.Event;
import edu.virginia.engine.event.IEventListener;


public class SoundManager implements IEventListener {
	

	SoundEffect UPBEAT_FUNK;
	SoundEffect JUMP;
	SoundEffect ITEM;
	SoundEffect TREADMILL;
	SoundEffect OINK;
	SoundEffect FIRE;
	SoundEffect AXE;
	SoundEffect STAB;
	SoundEffect CALIBUR;
	SoundEffect STICK;
	SoundEffect LASER;
	SoundEffect COUNT;
	SoundEffect LIFT;
	
	public SoundManager() {

		UPBEAT_FUNK = new SoundEffect("resources" + File.separator + "UpBeatFunk.wav");
		JUMP = new SoundEffect("resources" + File.separator + "Sweep5.wav");
		ITEM = new SoundEffect("resources" + File.separator + "item.wav");
		TREADMILL = new SoundEffect("resources" + File.separator + "treadmill3.wav");
		OINK = new SoundEffect("resources" + File.separator + "pig.wav");
		FIRE = new SoundEffect("resources" + File.separator + "Fire2.wav");
		AXE = new SoundEffect("resources" + File.separator + "axe.wav");
		STAB = new SoundEffect("resources" + File.separator + "stab.wav");
		CALIBUR = new SoundEffect("resources" + File.separator + "calibur.wav");
		STICK = new SoundEffect("resources" + File.separator + "stick.wav");
		LASER = new SoundEffect("resources" + File.separator + "laser.wav");
		COUNT = new SoundEffect("resources" + File.separator + "countdown.wav");
		LIFT = new SoundEffect("resources" + File.separator + "lift2.wav");
	}
	
	
	public  void	PlaySoundEffect(String id) {
		switch(id) {
	
			case "JUMP":
				JUMP.play();
				break;
				
			case "ITEM":
				ITEM.play();
				break;
				
			case "TREADMILL":
				TREADMILL.play();
				break;
				
			case "OINK":
				OINK.play();
				break;
				
			case "FIRE":
				FIRE.play();
				break;
				
			case "AXE":
				AXE.play();
				break;
				
			case "STAB":
				STAB.play();
				break;
				
			case "CALIBUR":
				CALIBUR.play();
				break;
				
			case "STICK":
				STICK.play();
				break;
				
			case "LASER":
				LASER.play();
				break;
				
			case "COUNT":
				COUNT.play();
				break;
			
			case "LIFT":
				LIFT.play();
				break;
				
			default:
				break;
		}
	}
	
	public void loopSoundEffect(String id) {
		switch(id) {

			case "UPBEAT_FUNK":
				UPBEAT_FUNK.loop();
				break;
				
			default:
				break;
		}
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	} 
}