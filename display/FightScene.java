package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.event.Event;
import edu.virginia.engine.event.IEventListener;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParams;
import edu.virginia.engine.util.GameClock;
import edu.virginia.thePerfectCombination.Axe;
import edu.virginia.thePerfectCombination.Book;
import edu.virginia.thePerfectCombination.Calibur;
import edu.virginia.thePerfectCombination.Dagger;
import edu.virginia.thePerfectCombination.Laser;
import edu.virginia.thePerfectCombination.Magic;
import edu.virginia.thePerfectCombination.SoundManager;
import edu.virginia.thePerfectCombination.Stick;

public class FightScene extends DisplayObjectContainer implements IEventListener{
	
	boolean isOver = false;
	
	boolean first1 = true;
	
	
	//to set up positions, pivot points of platforms and weapons
	AnimatedSprite currentSettingUp;
	
	
	private PlayerStats trainingStatsP1;
	private PlayerStats trainingStatsP2;
	
	GameClock gameClock = new GameClock();
	int previousTime, currentTime;
	int deltaT;
	boolean facingLeft1 = false;
	boolean facingLeft2 = false;
	
	
	private String weapon1 = "book";
	private String weapon2 = "laser";

	
	ArrayList<Integer> prevPressedKeys = new ArrayList<Integer>();
	
	//fight arena
	AnimatedSprite background = new AnimatedSprite("background", "bg-new.jpg", true);

	AnimatedSprite ground = new AnimatedSprite("ground", "platform-new.jpg", true);
	AnimatedSprite spikes1 = new AnimatedSprite("spikes1", "spikes_left.png", true);
	AnimatedSprite spikes2 = new AnimatedSprite("spikes2", "spikes_right.png", true);
	
	AnimatedSprite platform11 = new AnimatedSprite("platform11", "platform-new.jpg", true);
	AnimatedSprite platform12 = new AnimatedSprite("platform12", "platform-new.jpg", true);
	
	AnimatedSprite platform21 = new AnimatedSprite("platform21", "platform-new.jpg", true);
	AnimatedSprite platform22 = new AnimatedSprite("platform22", "platform-new.jpg", true);
	
	AnimatedSprite platform31 = new AnimatedSprite("platform31", "platform-new.jpg", true);



	
	//player1
	PhysicsSprite bear1 = new PhysicsSprite("bear1", "paper_bear1_flash_sprite_sheet_by_yoshipapaercrafter-d8fxvpv.png", false);
	HealthBar bear1Health = new HealthBar("Player 1 hp", bear1);
	AnimatedSprite down1 = new AnimatedSprite("down1", "box_transparent.png", true); //square on bear1

	AnimatedSprite blood1 = new AnimatedSprite("blood1", "blood.png", true);
	AnimatedSprite ouch1 = new AnimatedSprite("ouch1", "ouch.png", true);

	AnimatedSprite axe1box = new AnimatedSprite("axe1box", "box_transparent.png", true); //square on bear2
	AnimatedSprite calibur1box = new AnimatedSprite("calibur1box", "box_transparent.png", true); //square on bear2

	AnimatedSprite vader1 = new AnimatedSprite("vader1", "vader_handle.png", true);
	
	//player2
	PhysicsSprite bear2 = new PhysicsSprite("bear2", "paper_bear1_flash_sprite_sheet_by_yoshipapaercrafter-d8fxvpv.png", false);
	HealthBar bear2Health = new HealthBar("Player 2 hp", bear2);
	AnimatedSprite down2 = new AnimatedSprite("down2", "box_transparent.png", true); //square on bear2
	
	AnimatedSprite blood2 = new AnimatedSprite("blood2", "blood.png", true);
	AnimatedSprite ouch2 = new AnimatedSprite("ouch2", "ouch.png", true);

	AnimatedSprite axe2box = new AnimatedSprite("axe2box", "box_transparent.png", true); //square on bear2	
	AnimatedSprite calibur2box = new AnimatedSprite("calibur2box", "box_transparent.png", true); //square on bear2
	
	AnimatedSprite vader2 = new AnimatedSprite("vader2", "vader_handle.png", true);
	
	//weapons player 1
	Axe axe1 = new Axe("axe1");
	Book book1 = new Book("book1");
	Dagger dagger1 = new Dagger("dagger1");
	Calibur calibur1 = new Calibur("calibur1");
	Stick stick1 = new Stick("stick1");
	Laser laser1 = new Laser("laser1");

	Magic magic1 = new Magic("magic1", bear1);
	
	//weapons player 2
	Axe axe2 = new Axe("axe2");
	Book book2 = new Book("book2");
	Dagger dagger2 = new Dagger("dagger2");
	Calibur calibur2 = new Calibur("calibur2");
	Stick stick2 = new Stick("stick2");
	Laser laser2 = new Laser("laser2");
	
	Magic magic2 = new Magic("magic2", bear2);

	
	//tweens player 1
	Tween axe1Tween = new Tween(axe1);
	Tween blood1Tween = new Tween(blood1);
	Tween ouch1Tween = new Tween(ouch1);
	Tween bear1Tween = new Tween(bear1);
	
	//tweens player 2
	Tween axe2Tween = new Tween(axe2);
	Tween blood2Tween = new Tween(blood2);
	Tween ouch2Tween = new Tween(ouch2);
	Tween bear2Tween = new Tween(bear2);

	
	//tween for the middle platform
	Tween platform31Tween = new Tween(platform31);

	//cooldown1Axe for axe1 -- not sure if I still need this
	GameClock cooldown1Axe = new GameClock();
	GameClock cooldown1AxeHit = new GameClock();
	GameClock cooldown1Fire = new GameClock();
	
	GameClock cooldown2Axe = new GameClock();
	GameClock cooldown2AxeHit = new GameClock();
	GameClock cooldown2Fire = new GameClock();

	//cooldown for player 1 to receive damage
	GameClock cooldown1beingHit = new GameClock();
	
	//cooldown for player 2 to receive damage
	GameClock cooldown2beingHit = new GameClock();
	
	//cooldown for the middle platform tween
	GameClock cooldownPlatform31Tween = new GameClock();

	Game game;
	
	SoundManager soundManager = new SoundManager();
	
	//extra damage 
	int extraDmg1 = 0;
	int extraDmg2 = 0;

	private boolean player1Won = false;
	
	public FightScene(String id, Game game) {
		super(id);
		this.game = game;
		this.addChild(background);
		this.addChild(platform11);
		this.addChild(platform12);
		this.addChild(platform21);
		this.addChild(platform22);
		this.addChild(platform31);
		this.addChild(ground);
		this.addChild(spikes1);
		this.addChild(spikes2);
		this.addChild(bear1);
		this.addChild(bear2);
		this.addChild(blood1);
		this.addChild(blood2);
		this.addChild(bear1Health);
		this.addChild(bear2Health);
		this.addChild(magic1);
		this.addChild(magic2);

		
		
		//currentSettingUp = bear1;
		
		//fighting area
		ground.setPosition(0, 750);
		spikes1.setPosition(-4, 0);
		spikes2.setPosition(1317, 0);
		
		platform11.setPosition(923, 650);
		platform11.setScale(0.22, 0.12);
		platform12.setPosition(923, 541);
		platform12.setScale(0.22, 0.12);
		
		platform21.setPosition(143, 650);
		platform21.setScale(0.22, 0.12);
		platform22.setPosition(143, 541);
		platform22.setScale(0.22, 0.12);
		
		platform31.setScale(0.1, 0.12);
		platform31.setPosition(629, 535);
		
		//player1
		bear1.setPosition(1154, 300);
		bear1.setPivotPoint(25, 0);
		bear1.addChild(down1);
		bear1.addChild(ouch1);
		
		ouch1.setAlpha(0);
		ouch1.setScale(0.1, 0.1);
		ouch1.setPosition(0, -50);
		down1.setScale(1.0, 0.22);
		down1.setPosition(-14, 54);
		
		blood1.setScale(0.1, 0.1);
		blood1.setPivotPoint(blood1.getUnscaledWidth() / 2, blood1.getUnscaledHeight() / 2);
		bear1Health.setPosition(964, 52);
		bear1.setHealth(50);
		
		vader1.setScale(0.07, 0.16);
		vader1.setRotation(15.0);
		vader1.setPosition(-20, 0);
		vader1.setPivotPoint(-160, 10);
		
		calibur1.addChild(calibur1box);
		calibur1box.setPosition(180, -28);
		calibur1box.setScale(15.9, 2.6);
		
		axe1.addChild(axe1box);
		axe1box.setScale(50, 50);
		axe1box.setPosition(1200, -1500);
	
		/*switch(weapon1) {
		case "axe":
			bear1.addChild(axe1);
			break;
		case "book":
			bear1.addChild(book1);
			break;
		case "dagger":
			bear1.addChild(dagger1);
			break;
		case "calibur":
			bear1.addChild(calibur1);
			break;
		case "stick":
			bear1.addChild(stick1);
			break;
		case "laser":
			bear1.addChild(laser1);
			bear1.addChild(vader1);
			break;
		}*/
		
		bear1Tween.animate(TweenableParams.YPOS, 800, 300, 1000, TweenTransitions.LINEAR);
		bear1Tween.animate(TweenableParams.ALPHA, 0.0, 1.0, 1500, TweenTransitions.QUINT);
		TweenJuggler.add(bear1Tween);

		//player2
		bear2.setPosition(323, 300);
		bear2.setPivotPoint(25, 0);
		bear2.addChild(down2);
		bear2.addChild(ouch2);
		
		ouch2.setAlpha(0);
		ouch2.setScale(0.1, 0.1);
		ouch2.setPosition(0, -50);
		down2.setScale(1.0, 0.22);
		down2.setPosition(-14, 54);
		
		blood2.setScale(0.1, 0.1);
		blood2.setPivotPoint(blood2.getUnscaledWidth() / 2, blood2.getUnscaledHeight() / 2);
		bear2Health.setPosition(128, 52);
		bear2.setHealth(50);
		
		vader2.setScale(0.07, 0.16);
		vader2.setRotation(15.0);
		vader2.setPosition(-20, 0);
		vader2.setPivotPoint(-160, 10);
		
		calibur2.addChild(calibur2box);
		calibur2box.setPosition(180, -28);
		calibur2box.setScale(15.9, 2.6);
		
		axe2.addChild(axe2box);
		axe2box.setScale(50, 50);
		axe2box.setPosition(1200, -1500);
		
		/*switch(weapon2) {
		case "axe":
			bear2.addChild(axe2);
			break;
		case "book":
			bear2.addChild(book2);
			break;
		case "dagger":
			bear2.addChild(dagger2);
			break;
		case "calibur":
			bear2.addChild(calibur2);
			break;
		case "stick":
			bear2.addChild(stick2);
			break;
		case "laser":
			bear2.addChild(laser2);
			bear2.addChild(vader2);
			break;
		}*/
		

		bear2Tween.animate(TweenableParams.YPOS, 800, 300, 1000, TweenTransitions.LINEAR);
		bear2Tween.animate(TweenableParams.ALPHA, 0.0, 1.0, 1500, TweenTransitions.QUINT);
		TweenJuggler.add(bear2Tween);
	}
	
	@Override
	protected void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
		
		if(trainingStatsP1.isHasAxe() && !bear1.contains("axe1")) {
			System.out.print(trainingStatsP1.getStrength());
			bear1.addChild(axe1);
			extraDmg1 = (trainingStatsP1.getStrength() - 70)/3;
			axe1.setDamage(axe1.getDamage() + extraDmg1);
		}
		if(trainingStatsP1.isHasDagger() && !bear1.contains("dagger1")) {
			bear1.addChild(dagger1);
			extraDmg1 = (trainingStatsP1.getStamina() - 70)/3;
			dagger1.setDamage(dagger1.getDamage() + extraDmg1);
		}
		if(trainingStatsP1.isHasMagic() && !bear1.contains("book1")) {
			bear1.addChild(book1);
			extraDmg1 = (trainingStatsP1.getKnowledge() - 75)/3;
			magic1.setDamage(magic1.getDamage() + extraDmg1);
		}
		if(trainingStatsP1.isHasCalibur() && !bear1.contains("calibur1")) {
			bear1.addChild(calibur1);
			extraDmg1 = (trainingStatsP1.getKnowledge() - 75) + (trainingStatsP1.getStrength() - 70);
			calibur1.setDamage(calibur1.getDamage() + extraDmg1);
		}
		if(trainingStatsP1.isHasLazer() && !bear1.contains("laser1")) {
			bear1.addChild(laser1);
			bear1.addChild(vader1);
			extraDmg1 = (trainingStatsP1.getKnowledge() - 75) + (trainingStatsP1.getStamina() - 70);
			laser1.setDamage(laser1.getDamage() + extraDmg1);
		}
		if(trainingStatsP1.isHasStick() && !bear1.contains("stick1")) {
			bear1.addChild(stick1);
			extraDmg1 = (trainingStatsP1.getStrength() - 70) + (trainingStatsP1.getStamina() - 70);
			stick1.setDamage(stick1.getDamage() + extraDmg1);
		}
		if(trainingStatsP2.isHasAxe() && !bear2.contains("axe2")) {
			bear2.addChild(axe2);
			extraDmg2 = (trainingStatsP2.getStrength() - 70)/3;
			axe2.setDamage(axe2.getDamage() + extraDmg2);
		}
		if(trainingStatsP2.isHasDagger() && !bear2.contains("dagger2")) {
			bear2.addChild(dagger2);
			extraDmg2 = (trainingStatsP2.getStamina() - 70)/3;
			dagger2.setDamage(dagger2.getDamage() + extraDmg2);
		}
		if(trainingStatsP2.isHasMagic() && !bear2.contains("book2")) {
			bear2.addChild(book2);
			extraDmg2 = (trainingStatsP2.getKnowledge() - 75)/3;
			magic2.setDamage(magic2.getDamage() + extraDmg2);
		}
		if(trainingStatsP2.isHasCalibur() && !bear2.contains("calibur2")) {
			bear2.addChild(calibur2);
			extraDmg2 = (trainingStatsP2.getKnowledge() - 75) + (trainingStatsP2.getStrength() - 70);
			calibur2.setDamage(calibur2.getDamage() + extraDmg2);
		}
		if(trainingStatsP2.isHasLazer() && !bear2.contains("laser2")) {
			bear2.addChild(laser2);
			bear2.addChild(vader2);
			extraDmg2 = (trainingStatsP2.getKnowledge() - 75) + (trainingStatsP2.getStamina() - 70);
			laser2.setDamage(laser2.getDamage() + extraDmg2);
		}
		if(trainingStatsP2.isHasStick() && !bear2.contains("stick2")) {
			bear2.addChild(stick2);
			extraDmg2 = (trainingStatsP2.getStrength() - 70) + (trainingStatsP2.getStamina() - 70);
			stick2.setDamage(stick2.getDamage() + extraDmg2);
		}

		
		//make the middle platform move
		if(first1 || cooldownPlatform31Tween.getElapsedTime() > 10000) {
			cooldownPlatform31Tween.resetGameClock();
			platform31Tween.animate(TweenableParams.YPOS, 535, 50, 5000, TweenTransitions.LINEAR);
			TweenJuggler.add(platform31Tween);
			platform31Tween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
		}
		
///////////////just to set up things/////////////////////// 
/*
		if(pressedKeys.contains(KeyEvent.VK_Y)) {
			currentSettingUp.translateY(-2);
			System.out.println("x, y:" + currentSettingUp.getPosition()[0] + ", " + currentSettingUp.getPosition()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_H)) {
			currentSettingUp.translateY(2);
			System.out.println("x, y:" + currentSettingUp.getPosition()[0] + ", " + currentSettingUp.getPosition()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_J)) {
			currentSettingUp.translateX(2);
			System.out.println("x, y:" + currentSettingUp.getPosition()[0] + ", " + currentSettingUp.getPosition()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_G)) {
			currentSettingUp.translateX(-2);
			System.out.println("x, y:" + currentSettingUp.getPosition()[0] + ", " + currentSettingUp.getPosition()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_5)) {
			currentSettingUp.setScale(currentSettingUp.getScale()[0] - 0.005, currentSettingUp.getScale()[1]);
			System.out.println("scale x, y:" + currentSettingUp.getScale()[0] + ", " + currentSettingUp.getScale()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_6)) {
			currentSettingUp.setScale(currentSettingUp.getScale()[0] + 0.005, currentSettingUp.getScale()[1]);
			System.out.println("scale x, y:" + currentSettingUp.getScale()[0] + ", " + currentSettingUp.getScale()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_R)) {
			currentSettingUp.setScale(currentSettingUp.getScale()[0], currentSettingUp.getScale()[1] + 0.005);
			System.out.println("scale x, y:" + currentSettingUp.getScale()[0] + ", " + currentSettingUp.getScale()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_F)) {
			currentSettingUp.setScale(currentSettingUp.getScale()[0], currentSettingUp.getScale()[1] - 0.005);
			System.out.println("scale x, y:" + currentSettingUp.getScale()[0] + ", " + currentSettingUp.getScale()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_7)) {
			currentSettingUp.rotate(-5);
			System.out.println("rotation:" + currentSettingUp.getRotation());
		}
		
		if(pressedKeys.contains(KeyEvent.VK_8)) {
			currentSettingUp.rotate(5);
			System.out.println("rotation:" + currentSettingUp.getRotation());
		}
		
		if(pressedKeys.contains(KeyEvent.VK_9)) {
			currentSettingUp.setPivotPoint(currentSettingUp.getPivotPoint()[0] , currentSettingUp.getPivotPoint()[1] - 5);
			System.out.println("pivot point:" + currentSettingUp.getPivotPoint()[0] + ", " + currentSettingUp.getPivotPoint()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_O)) {
			currentSettingUp.setPivotPoint(currentSettingUp.getPivotPoint()[0] , currentSettingUp.getPivotPoint()[1] + 5);
			System.out.println("pivot point:" + currentSettingUp.getPivotPoint()[0] + ", " + currentSettingUp.getPivotPoint()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_I)) {
			currentSettingUp.setPivotPoint(currentSettingUp.getPivotPoint()[0] + 5 , currentSettingUp.getPivotPoint()[1]);
			System.out.println("pivot point:" + currentSettingUp.getPivotPoint()[0] + ", " + currentSettingUp.getPivotPoint()[1]);
		}
		
		if(pressedKeys.contains(KeyEvent.VK_P)) {
			currentSettingUp.setPivotPoint(currentSettingUp.getPivotPoint()[0] - 5 , currentSettingUp.getPivotPoint()[1]);
			System.out.println("pivot point:" + currentSettingUp.getPivotPoint()[0] + ", " + currentSettingUp.getPivotPoint()[1]);
		}
	*/
///////////////////////////////
			
		
/*********************************player1********************************/
		
		//handle player input
		if(pressedKeys.size() > 0) {
			
			if(bear1.getScale()[0] < 0) {
				facingLeft1 = true;
			}
			else {
				facingLeft1 = false;
			}
		
///////////////////////////////////////attack
			if(pressedKeys.contains(KeyEvent.VK_L) && !prevPressedKeys.contains(KeyEvent.VK_L))	{

				if(laser1.getParent() != null) {
					laser1.attack();
				}

				else if(stick1.getParent() != null) {
					stick1.attack();
				}

				else if(calibur1.getParent() != null) {
					calibur1.attack();

				}
				else if(axe1.getParent() != null) {
					axe1.attack();
				}
				
				if(book1.getParent() != null) {
					
					if(pressedKeys.contains(KeyEvent.VK_UP) && pressedKeys.contains(KeyEvent.VK_RIGHT)) {
						magic1.attackDiagonal(false);
					}
					
					else if(pressedKeys.contains(KeyEvent.VK_UP) && pressedKeys.contains(KeyEvent.VK_LEFT)) {
						magic1.attackDiagonal(true);
					}
					
					else if(pressedKeys.contains(KeyEvent.VK_UP)) {
						magic1.attackUp();
					}
					
					else {
						magic1.attack(facingLeft1);
					}
					
				}
				
				if(dagger1.getParent() != null) {
					dagger1.setPosition(30, 20);	
					soundManager.PlaySoundEffect("STAB");
				}	
			}
			
			else {
				dagger1.setPosition(-5, 25);
			}
		}		
		
		//movement of bear1
		currentTime = (int) gameClock.getElapsedTime();
		deltaT = currentTime - previousTime;
		previousTime = currentTime;
				
		if(bear1.isFalling() || bear1.isJumping()) {
			if(bear1.getCurrentVelocity()[1] < bear1.getMaxSpeed()) {
				bear1.increaseCurrentVelocity(0, bear1.getGravity());
			}
		}
		
		if(pressedKeys.contains(KeyEvent.VK_UP) && !prevPressedKeys.contains(KeyEvent.VK_UP) && !bear1.isJumping()) {
			bear1.setJumping(true);
			bear1.setCurrentVelocity(bear1.getCurrentVelocity()[0], -0.4);
			soundManager.PlaySoundEffect("JUMP");
		}
		
		if(pressedKeys.contains(KeyEvent.VK_RIGHT) && !(cooldown1Axe.getElapsedTime() < 500 && facingLeft1)) {
			bear1.increaseCurrentVelocity(bear1.getMaxAcceleration(), 0);
			 if (bear1.getCurrentVelocity()[0] > bear1.getMaxSpeed()) {
				 bear1.setCurrentVelocity(bear1.getMaxSpeed(), bear1.getCurrentVelocity()[1]);
		     }
			 bear1.animate("walkRight");
			 if(facingLeft1) {
				 bear1.setScale(-bear1.getScale()[0], bear1.getScale()[1]);
			 }
		}
		
		else if (pressedKeys.contains(KeyEvent.VK_LEFT) && !(cooldown1Axe.getElapsedTime() < 500 && !facingLeft1) || first1) {
			first1 = false;
			bear1.increaseCurrentVelocity(-bear1.getMaxAcceleration(), 0);
			//System.out.println("bear1's speed: " + bear1.getCurrentVelocity()[0] + ", " + bear1.getCurrentVelocity()[1]);
	        if (bear1.getCurrentVelocity()[0] < -bear1.getMaxSpeed()) {
	        	bear1.setCurrentVelocity(-bear1.getMaxSpeed(), bear1.getCurrentVelocity()[1]);
	        }
	        bear1.animate("walkRight");
	        if(!facingLeft1) {
	        	bear1.setScale(-bear1.getScale()[0], bear1.getScale()[1]);
	        }
		}
			
		else {	
			 // Neither forward nor backward pressed
	        if (bear1.getCurrentVelocity()[0] < 0) {
	        	bear1.increaseCurrentVelocity(bear1.getMaxAcceleration(), 0);
	            if (bear1.getCurrentVelocity()[0] >= 0) {
	            	bear1.setCurrentVelocity(0, bear1.getCurrentVelocity()[1]);
	            }
	        } else {
	        	bear1.increaseCurrentVelocity(-bear1.getMaxAcceleration(), 0);
	            if (bear1.currentVelocity[0] <= 0) {
	            	bear1.setCurrentVelocity(0, bear1.getCurrentVelocity()[1]);
	            }
	        }
		}			
			
		//update bear1's position
		bear1.setPosition((int) (bear1.getPosition()[0] + bear1.getCurrentVelocity()[0] * deltaT), (int) (bear1.getPosition()[1] + bear1.getCurrentVelocity()[1] * deltaT));

//check for collisions with background
		if(bear1.collidesWith(ground)) {
			bear1.setCurrentVelocity(bear1.getCurrentVelocity()[0], 0);
			bear1.setFalling(false);
			bear1.setJumping(false);
			
			bear1.setPosition(bear1.getPosition()[0], ground.getPosition()[1] - bear1.getScaledHeight());
		}
			
		else if(down1.collidesWith(platform11)) {
			bear1.setCurrentVelocity(bear1.getCurrentVelocity()[0], 0);
			bear1.setFalling(false);
			bear1.setJumping(false);
			
			bear1.setPosition(bear1.getPosition()[0], platform11.getPosition()[1] - bear1.getScaledHeight());
		}
			
		else if(down1.collidesWith(platform12)) {
			bear1.setCurrentVelocity(bear1.getCurrentVelocity()[0], 0);
			bear1.setFalling(false);
			bear1.setJumping(false);
			
			bear1.setPosition(bear1.getPosition()[0], platform12.getPosition()[1] - bear1.getScaledHeight());
		}

		else if(down1.collidesWith(platform21)) {
			bear1.setCurrentVelocity(bear1.getCurrentVelocity()[0], 0);
			bear1.setFalling(false);
			bear1.setJumping(false);
			
			bear1.setPosition(bear1.getPosition()[0], platform21.getPosition()[1] - bear1.getScaledHeight());
		}
			
		else if(down1.collidesWith(platform22)) {
			bear1.setCurrentVelocity(bear1.getCurrentVelocity()[0], 0);
			bear1.setFalling(false);
			bear1.setJumping(false);
			
			bear1.setPosition(bear1.getPosition()[0], platform22.getPosition()[1] - bear1.getScaledHeight());
		}
			
		else if(down1.collidesWith(platform31)) {
			bear1.setCurrentVelocity(bear1.getCurrentVelocity()[0], 0);
			bear1.setFalling(false);
			bear1.setJumping(false);
			
			bear1.setPosition(bear1.getPosition()[0], platform31.getPosition()[1] - bear1.getScaledHeight());
		}
		else {
			bear1.setFalling(true);
		}

		if(bear1.collidesWith(spikes1)) {
			bear1.takeDamage(1);
			soundManager.PlaySoundEffect("OINK");
			System.out.println("colliding with left spikes");
			blood1.setPosition(bear1.getPosition()[0], bear1.getPosition()[1]);
			bear1.setCurrentVelocity(0, bear1.getCurrentVelocity()[1]);
			int width = Math.abs(bear1.getScaledWidth());
			bear1.setPosition(spikes1.getPosition()[0] + spikes1.getUnscaledWidth() + width / 2 + 10, bear1.getPosition()[1]);
			blood1Tween.animate(TweenableParams.XSCALE, 0, 0.5, 1000, TweenTransitions.LINEAR);
			blood1Tween.animate(TweenableParams.YSCALE, 0, 0.5, 1000, TweenTransitions.LINEAR);
			blood1Tween.animate(TweenableParams.ALPHA, 1.0, 0.0, 1000, TweenTransitions.QUINT);
			ouch1Tween.animate(TweenableParams.ALPHA, 1, 0, 500, TweenTransitions.QUINT);

			TweenJuggler.add(blood1Tween);
			TweenJuggler.add(ouch1Tween);
		}
			
		if(bear1.collidesWith(spikes2)) {
			bear1.takeDamage(1);
			soundManager.PlaySoundEffect("OINK");
			System.out.println("colliding with right spikes");
			blood1.setPosition(bear1.getPosition()[0], bear1.getPosition()[1]);
			bear1.setCurrentVelocity(0, bear1.getCurrentVelocity()[1]);
			int width = Math.abs(bear1.getScaledWidth());
			bear1.setPosition(spikes2.getPosition()[0] - width / 2 - 10, bear1.getPosition()[1]);
			blood1Tween.animate(TweenableParams.XSCALE, 0, 0.5, 1000, TweenTransitions.LINEAR);
			blood1Tween.animate(TweenableParams.YSCALE, 0, 0.5, 1000, TweenTransitions.LINEAR);
			blood1Tween.animate(TweenableParams.ALPHA, 1.0, 0.0, 1000, TweenTransitions.QUINT);
			ouch1Tween.animate(TweenableParams.ALPHA, 1, 0, 500, TweenTransitions.QUINT);
			
			TweenJuggler.add(blood1Tween);
			TweenJuggler.add(ouch1Tween);

		}
			
/*********************************player2********************************/
			
		//handle player input
		if(pressedKeys.size() > 0) {
			
			if(bear2.getScale()[0] < 0) {
				facingLeft2 = true;
			}
			else {
				facingLeft2 = false;
			}
			
/////////////attack
			if(pressedKeys.contains(KeyEvent.VK_1) && !prevPressedKeys.contains(KeyEvent.VK_1))	{
	
				if(laser2.getParent() != null) {
					laser2.attack();
				}
	
				else if(stick2.getParent() != null) {
					stick2.attack();
				}
	
				else if(calibur2.getParent() != null) {
					calibur2.attack();
	
				}
				else if(axe2.getParent() != null) {
					axe2.attack();
				}
				
				else if(book2.getParent() != null) {
					
					if(pressedKeys.contains(KeyEvent.VK_W) && pressedKeys.contains(KeyEvent.VK_D)) {
						magic2.attackDiagonal(false);
					}
					
					else if(pressedKeys.contains(KeyEvent.VK_W) && pressedKeys.contains(KeyEvent.VK_A)) {
						magic2.attackDiagonal(true);
					}
					
					else if(pressedKeys.contains(KeyEvent.VK_W)) {
						magic2.attackUp();
					}
					
					else {
						magic2.attack(facingLeft2);
					}
					
				}
				
				if(dagger2.getParent() != null) {
					dagger2.setPosition(30, 20);	
					soundManager.PlaySoundEffect("STAB");
				}	
			}
			else {
			dagger2.setPosition(-5, 25);
			}
		}		
			
			//movement of bear2
			if(bear2.isFalling() || bear2.isJumping()) {
				if(bear2.getCurrentVelocity()[1] < bear2.getMaxSpeed()) {
					bear2.increaseCurrentVelocity(0, bear2.getGravity());
				}
			}
			
			if(pressedKeys.contains(KeyEvent.VK_W) && !prevPressedKeys.contains(KeyEvent.VK_W) && !bear2.isJumping()) { //if up arrow is pressed, move up
				bear2.setJumping(true);
				bear2.setCurrentVelocity(bear2.getCurrentVelocity()[0], -0.4);
				soundManager.PlaySoundEffect("JUMP");
			}
			
			if(pressedKeys.contains(KeyEvent.VK_D) && !(cooldown2Axe.getElapsedTime() < 2000 && facingLeft2)) {
				bear2.increaseCurrentVelocity(bear2.getMaxAcceleration(), 0);
				 if (bear2.getCurrentVelocity()[0] > bear2.getMaxSpeed()) {
					 bear2.setCurrentVelocity(bear2.getMaxSpeed(), bear2.getCurrentVelocity()[1]);
			     }
				 bear2.animate("walkRight2");
				 if(facingLeft2) {
					 bear2.setScale(-bear2.getScale()[0], bear2.getScale()[1]);
				 }
			}
			
			else if (pressedKeys.contains(KeyEvent.VK_A) && !(cooldown2Axe.getElapsedTime() < 2000 && !facingLeft2)) {
				bear2.increaseCurrentVelocity(-bear2.getMaxAcceleration(), 0);
		        if (bear2.getCurrentVelocity()[0] < -bear2.getMaxSpeed()) {
		        	bear2.setCurrentVelocity(-bear2.getMaxSpeed(), bear2.getCurrentVelocity()[1]);
		        }
		        bear2.animate("walkRight2");
		        if(!facingLeft2) {
		        	bear2.setScale(-bear2.getScale()[0], bear2.getScale()[1]);
		        }
			}
				
			else {
				 // Neither forward nor backward pressed
		        if (bear2.getCurrentVelocity()[0] < 0) {
		        	bear2.increaseCurrentVelocity(bear2.getMaxAcceleration(), 0);
		            if (bear2.getCurrentVelocity()[0] >= 0) {
		            	bear2.setCurrentVelocity(0, bear2.getCurrentVelocity()[1]);
		            }
		        } else {
		        	bear2.increaseCurrentVelocity(-bear2.getMaxAcceleration(), 0);
		            if (bear2.currentVelocity[0] <= 0) {
		            	bear2.setCurrentVelocity(0, bear2.getCurrentVelocity()[1]);
		            }
		        }
			}			
				
			//update bear2's position
			bear2.setPosition((int) (bear2.getPosition()[0] + bear2.getCurrentVelocity()[0] * deltaT), (int) (bear2.getPosition()[1] + bear2.getCurrentVelocity()[1] * deltaT));

//check for collisions with background
			if(bear2.collidesWith(ground)) {
				bear2.setCurrentVelocity(bear2.getCurrentVelocity()[0], 0);
				bear2.setFalling(false);
				bear2.setJumping(false);
				
				bear2.setPosition(bear2.getPosition()[0], ground.getPosition()[1] - bear2.getScaledHeight());
			}
				
			else if(down2.collidesWith(platform11)) {
				bear2.setCurrentVelocity(bear2.getCurrentVelocity()[0], 0);
				bear2.setFalling(false);
				bear2.setJumping(false);
				
				bear2.setPosition(bear2.getPosition()[0], platform11.getPosition()[1] - bear2.getScaledHeight());
			}
				
			else if(down2.collidesWith(platform12)) {
				bear2.setCurrentVelocity(bear2.getCurrentVelocity()[0], 0);
				bear2.setFalling(false);
				bear2.setJumping(false);
				
				bear2.setPosition(bear2.getPosition()[0], platform12.getPosition()[1] - bear2.getScaledHeight());
			}
				
			else if(down2.collidesWith(platform21)) {
				bear2.setCurrentVelocity(bear2.getCurrentVelocity()[0], 0);
				bear2.setFalling(false);
				bear2.setJumping(false);
				
				bear2.setPosition(bear2.getPosition()[0], platform21.getPosition()[1] - bear2.getScaledHeight());
			}
				
			else if(down2.collidesWith(platform22)) {
				bear2.setCurrentVelocity(bear2.getCurrentVelocity()[0], 0);
				bear2.setFalling(false);
				bear2.setJumping(false);
				
				bear2.setPosition(bear2.getPosition()[0], platform22.getPosition()[1] - bear2.getScaledHeight());
			}
				
			else if(down2.collidesWith(platform31)) {
				bear2.setCurrentVelocity(bear2.getCurrentVelocity()[0], 0);
				bear2.setFalling(false);
				bear2.setJumping(false);
				
				bear2.setPosition(bear2.getPosition()[0], platform31.getPosition()[1] - bear2.getScaledHeight());
			}
			else {
				bear2.setFalling(true);
			}
				
			if(bear2.collidesWith(spikes1)) {
				soundManager.PlaySoundEffect("OINK");
				bear2.takeDamage(1);
				System.out.println("colliding with left spikes");
				blood2.setPosition(bear2.getPosition()[0], bear2.getPosition()[1]);
				bear2.setCurrentVelocity(0, bear2.getCurrentVelocity()[1]);
				int width = Math.abs(bear2.getScaledWidth());
				bear2.setPosition(spikes1.getPosition()[0] + spikes1.getUnscaledWidth() + width / 2 + 10, bear2.getPosition()[1]);
				
				blood2Tween.animate(TweenableParams.XSCALE, 0, 0.5, 1000, TweenTransitions.LINEAR);
				blood2Tween.animate(TweenableParams.YSCALE, 0, 0.5, 1000, TweenTransitions.LINEAR);
				blood2Tween.animate(TweenableParams.ALPHA, 1.0, 0.0, 1000, TweenTransitions.QUINT);
				ouch2Tween.animate(TweenableParams.ALPHA, 1, 0, 500, TweenTransitions.QUINT);
	
				TweenJuggler.add(blood2Tween);
				TweenJuggler.add(ouch2Tween);
			}
				
			if(bear2.collidesWith(spikes2)) {
				soundManager.PlaySoundEffect("OINK");
				bear2.takeDamage(1);
				System.out.println("colliding with right spikes");
				blood2.setPosition(bear2.getPosition()[0], bear2.getPosition()[1]);
				bear2.setCurrentVelocity(0, bear2.getCurrentVelocity()[1]);
				int width = Math.abs(bear2.getScaledWidth());
				bear2.setPosition(spikes2.getPosition()[0] - width / 2 - 10, bear2.getPosition()[1]);
				blood2Tween.animate(TweenableParams.XSCALE, 0, 0.5, 1000, TweenTransitions.LINEAR);
				blood2Tween.animate(TweenableParams.YSCALE, 0, 0.5, 1000, TweenTransitions.LINEAR);
				blood2Tween.animate(TweenableParams.ALPHA, 1.0, 0.0, 1000, TweenTransitions.QUINT);
				ouch2Tween.animate(TweenableParams.ALPHA, 1, 0, 500, TweenTransitions.QUINT);

				TweenJuggler.add(blood2Tween);
				TweenJuggler.add(ouch2Tween);
			}
				
/*********************************collisions and such********************************/
				
				prevPressedKeys.clear();			
				for(int i: pressedKeys) {
					prevPressedKeys.add(i);
				}
				
				//player1 hits player 2		
				if(magic1.collidesWith(bear2) && cooldown2beingHit.getElapsedTime() > magic1.getCoolDown() && magic1.isCooldown()) {
					bear2.takeDamage(magic1.getDamage());
					cooldown2beingHit.resetGameClock();
				}
				
				if(laser1.collidesWith(bear2) && cooldown2beingHit.getElapsedTime() > laser1.getCoolDown() && laser1.isCooldown()) {
					bear2.takeDamage(laser1.getDamage());
					cooldown2beingHit.resetGameClock();
				}
				
				if(stick1.collidesWith(bear2) && cooldown2beingHit.getElapsedTime() > stick1.getCoolDown() && stick1.isCooldown()) {
					bear2.takeDamage(stick1.getDamage());
					cooldown2beingHit.resetGameClock();
				}

				if(calibur1box.collidesWith(bear2) && cooldown2beingHit.getElapsedTime() > calibur1.getCoolDown() && calibur1.getRotation() > -20) {
					bear2.takeDamage(calibur1.getDamage());
					cooldown2beingHit.resetGameClock();
				}
				
				if(axe1box.collidesWith(bear2) && cooldown1AxeHit.getElapsedTime() > axe1.getCoolDown() && axe1.getRotation() > -50) {
					bear2.takeDamage(axe1.getDamage());
					cooldown1AxeHit.resetGameClock();
				}
				
				if(dagger1.collidesWith(bear2) && dagger1.getPosition()[0] == 30) {
					bear2.takeDamage(dagger1.getDamage());
				}
				
				//player2 hits player1
				if(magic2.collidesWith(bear1) && cooldown1beingHit.getElapsedTime() > magic2.getCoolDown() && magic2.isCooldown()) {
					bear1.takeDamage(magic2.getDamage());
					cooldown1beingHit.resetGameClock();
				}
				
				if(laser2.collidesWith(bear1) && cooldown1beingHit.getElapsedTime() > laser2.getCoolDown() && laser2.isCooldown()) {
					bear1.takeDamage(laser2.getDamage());
					cooldown1beingHit.resetGameClock();
				}
				
				if(stick2.collidesWith(bear1) && cooldown1beingHit.getElapsedTime() > stick2.getCoolDown() && stick2.isCooldown()) {
					bear1.takeDamage(stick2.getDamage());
					cooldown1beingHit.resetGameClock();
				}
				
				if(calibur2box.collidesWith(bear1) && cooldown1beingHit.getElapsedTime() > calibur2.getCoolDown() && calibur2.getRotation() > -20) {
					bear1.takeDamage(calibur2.getDamage());
					cooldown1beingHit.resetGameClock();
				}
				
				if(axe2box.collidesWith(bear1) && cooldown2AxeHit.getElapsedTime() > axe2.getCoolDown() && axe2.getRotation() > -50) {
					bear1.takeDamage(axe2.getDamage());
					cooldown2AxeHit.resetGameClock();
				}
				
				
				if(dagger2.collidesWith(bear1) && dagger2.getPosition()[0] == 30) {
					bear1.takeDamage(dagger2.getDamage());
				}
				
////////// decide if game is over
				if(bear2.getHealth() <= 0) {
					this.isOver = true;
					this.player1Won = true;
					game.stop();
				}
				
				if(bear1.getHealth() <= 0) {
					this.isOver = true;
					this.player1Won = false;
					game.stop();
				}
	}
	
	public boolean isPlayer1Won() {
		return player1Won;
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		Graphics2D g2d = (Graphics2D) g;
		//if(bear1 != null) g2d.draw(bear1.getGlobalHitbox());
		//if(bear2 != null) g2d.draw(bear2.getGlobalHitbox());
		//if(axe1box.getParent() != null) g2d.draw(axe1box.getGlobalHitbox());
		//if(axe2box.getParent() != null) g2d.draw(axe2box.getGlobalHitbox());
		//if(spikes1 != null) g2d.draw(spikes1.getGlobalHitbox());
		//if(spikes2 != null) g2d.draw(spikes2.getGlobalHitbox());
		//if(calibur1box.getParent() != null) g2d.draw(calibur1box.getGlobalHitbox());
		//if(stick1 != null) g2d.draw(stick1.getGlobalHitbox());

	
		g2d.drawString("extra dmg " + extraDmg1, 964, 92);		 
		g2d.drawString("extra dmg " + extraDmg2, 128, 92);		 


	}

	@Override
	public void handleEvent(Event event) {
		System.out.println("inside handle in fightscene");
		
		if(event.getSource() == platform31Tween) {
			Tween secondTween = new Tween(platform31);
			secondTween.animate(TweenableParams.YPOS, 50, 535, 5000, TweenTransitions.LINEAR);
			TweenJuggler.add(secondTween);
		}
	}

	public PlayerStats getTrainingStatsP1() {
		return trainingStatsP1;
	}

	public void setTrainingStatsP1(PlayerStats trainingStatsP1) {
		this.trainingStatsP1 = trainingStatsP1;
	}

	public PlayerStats getTrainingStatsP2() {
		return trainingStatsP2;
	}

	public void setTrainingStatsP2(PlayerStats trainingStatsP2) {
		this.trainingStatsP2 = trainingStatsP2;
	}
	
	public boolean isOver() {
		return this.isOver;
	}
}
