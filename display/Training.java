package edu.virginia.engine.display;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.event.Event;
import edu.virginia.engine.event.IEventListener;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParams;
import edu.virginia.engine.util.GameClock;
import edu.virginia.thePerfectCombination.SoundManager;


public class Training extends DisplayObjectContainer implements IEventListener{
	
	//Set Background
	AnimatedSprite wall = new AnimatedSprite("wall", "wall.png", true);
	AnimatedSprite background2 = new AnimatedSprite("background", "Black.png", true);
	AnimatedSprite background = new AnimatedSprite("background", "floor.png", true);
	AnimatedSprite border = new AnimatedSprite("border", "Border.png", true);
	AnimatedSprite spikes1 = new AnimatedSprite("spikes1", "spikes_left.png", true);
	AnimatedSprite spikes2 = new AnimatedSprite("spikes2", "spikes_right.png", true);
	
	
	//Pop-up window sprite
	AnimatedSprite popUpBench = new AnimatedSprite("popUpBench", "grey.png", true);
	AnimatedSprite popUpBook = new AnimatedSprite("popUpBook", "classroom.png", true);
	AnimatedSprite popUpRun = new AnimatedSprite("popUpRun", "grey.png", true);
	
	//Character Sprite
	PhysicsSprite player = new PhysicsSprite("bear1", "paper_bear1_flash_sprite_sheet_by_yoshipapaercrafter-d8fxvpv.png", false);
	PhysicsSprite player2 = new PhysicsSprite("bear2", "jump2.png", false);
	PhysicsSprite player3 = new PhysicsSprite("bear1", "paper_bear1_flash_sprite_sheet_by_yoshipapaercrafter-d8fxvpv.png", false);
	Tween player2Tween = new Tween(player2);
	
	AnimatedSprite aButton = new AnimatedSprite("aButton", "a.png", true);
	Tween aButtonTween = new Tween(aButton);
	AnimatedSprite axe = new AnimatedSprite("axe", "axe.png", true);
	Tween axeTween = new Tween(axe);
	AnimatedSprite dagger = new AnimatedSprite("dagger", "dagger.png", true);
	Tween daggerTween = new Tween(dagger);
	AnimatedSprite book1 = new AnimatedSprite("book1", "book.jpg", true);
	Tween bookTween = new Tween(book1);
	AnimatedSprite splat = new AnimatedSprite("splat", "splat.png", true);
	Tween splatTween = new Tween(splat);
	
	//Training Sprite
	AnimatedSprite bench = new AnimatedSprite("bench", "bench101.png", true);
	AnimatedSprite bench1 = new AnimatedSprite("bench1", "bar1.png", true);
	AnimatedSprite bench2 = new AnimatedSprite("bench2", "bar1.png", true);
	AnimatedSprite bench101 = new AnimatedSprite("bench101", "bench101.png", true);
	Tween benchTween = new Tween(bench2);
	AnimatedSprite book = new AnimatedSprite("book", "school.png", true);
	AnimatedSprite run = new AnimatedSprite("run", "tm2.png", true);
	AnimatedSprite run2 = new AnimatedSprite("run2", "tm2.png", true);

	AnimatedSprite plus = new AnimatedSprite("plus", "plus.png", true);
	

	
	SoundManager sound = new SoundManager();
	
	//Generating random numbers
	Random rand = new Random();
	int  rand1 = rand.nextInt(50) + 1;
	int  rand2 = rand.nextInt(50) + 1;
	int  rand3 = rand.nextInt(25) + 65;
	GameClock cooldownAxe = new GameClock();
	
	//Getting the previous pressed key
	ArrayList<Integer> prevPressedKeys = new ArrayList<Integer>();
	
	//Bear velocity
	int velocity = 4;
	
	//Training amounts
	private PlayerStats trainingStats;
	/*int strength = 0;
	int stamina = 0;
	int knowledge = 0;*/
	
	//Boolean is training
	boolean isBench = false;
	public boolean isBook = false;
	boolean isRun = false;
	
	//String input for sum
	String num = "";
	int add = 0;
	String p;
	
	
	public Training(String id) {
		super(id);

	
		//Creating Display Tree
		this.addChild(wall);

		background.setPosition(200, 0);
		border.setPosition(700- (border.getScaledWidth()/2), 560);
		this.addChild(border);
	
		this.addChild(background);
		background.addChild(background2);
		this.addChild(spikes1);
		this.addChild(spikes2);
		spikes1.setPosition(-4, 0);
		spikes2.setPosition(1317, 0);

		background.addChild(player);
		background.addChild(bench);
		background.addChild(book);
		background.addChild(run);
		background.addChild(bench1);
	
		axe.setScale(0.08, 0.08);
		splat.setScale(.5, .5);
		axe.setPosition(350, 150);
		splat.setPosition(350, 150);
		player.setPivotPoint(25, 0);
		splat.setAlpha(0);
		//dagger.setScale(.25, .25);
		
		//Set training positions
		bench.setPosition(80, 180);
		bench1.setPosition(40, 100);
		book.setPosition(400, 330);
		book.setScale(.9, .9);
		run.setPosition(680, 170);
		run.setScale(.85, .85);
		bench.setScale(1.1, 1.1);
		bench101.setScale(1.1, 1.1);
		//Benching Pop-Up Window
		popUpBench.setPosition(100, 100);
		
		//Running Pop-Up Window
		popUpRun.setPosition(100, 100);
		popUpRun.addChild(run2);
		run2.setPosition(70, 70);
		player3.setPosition(140, 130);
		
		//Set Bear Position
		player.setPosition(510, 180);
		
		//Knowledge Training Pop-up Window
		popUpBook.setPosition(225, 0);

		popUpBook.addChild(plus);
		plus.setPosition(200, 140);
		
		aButton.setScale(.75, .75);
		aButton.setPosition(500, 150);
		bench2.setPosition(60, 45);
		player2.rotate(-75);
		player2.setPosition(150, 230);
		bench101.setPosition(100, 110);
		player3.setScale(2, 2);
		player2.setScale(2, 2);
		
		player.setScale(1.5, 1.5);
		
		
		
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
	
		// Adding random generated numbers for Knowledge training
		add= rand1 +rand2;

		if(player != null) player.update(pressedKeys);
		
		if(pressedKeys.size() > 0) {
			if (!isBench && !isRun && !isBook) {
				//use arrow keys to move Bear
				if(pressedKeys.contains(KeyEvent.VK_RIGHT)&& !isBook && player.getPosition()[0] < 1000-player.getUnscaledWidth()) { 
					player.setPosition(player.getPosition()[0] + velocity, player.getPosition()[1]);
					if(player.getScale()[0] < 0) {
						player.setScale(-player.getScale()[0], player.getScale()[1]);
					}
					player.animate("walkRight");
				}
				if(pressedKeys.contains(KeyEvent.VK_LEFT)&& !isBook && player.getPosition()[0] > 40) { 
					player.setPosition(player.getPosition()[0] - velocity, player.getPosition()[1]);
					if(player.getScale()[0] > 0) {
						player.setScale(-player.getScale()[0], player.getScale()[1]);
					}
					player.animate("walkRight");
					
				}
				if(pressedKeys.contains(KeyEvent.VK_DOWN)&& !isBook && player.getPosition()[1] < 500-player.getUnscaledHeight()) {
					player.setPosition(player.getPosition()[0], player.getPosition()[1] + velocity);
					player.animate("walkRight");
				}
				if(pressedKeys.contains(KeyEvent.VK_UP)&& !isBook && player.getPosition()[1] > 20) {
					player.setPosition(player.getPosition()[0], player.getPosition()[1] - velocity);
					player.animate("walkRight");
				}
			}
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

			//Bench Training press A
			if(pressedKeys.contains(KeyEvent.VK_A) && isBench && !prevPressedKeys.contains(KeyEvent.VK_A)) {
				trainingStats.setStrength(trainingStats.getStrength()+1);
				benchTween.animate(TweenableParams.YPOS, 85, 45, 250, TweenTransitions.LINEAR);
				benchTween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(benchTween);
				player2Tween.animate(TweenableParams.XSCALE, 1.25, 2, 250, TweenTransitions.LINEAR);
				player2Tween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(player2Tween);
				aButtonTween.animate(TweenableParams.ALPHA, .5, 1, 250, TweenTransitions.LINEAR);
				aButtonTween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(aButtonTween);
			}
		
			
			//Stamina Training press random generated number displayed
			if(pressedKeys.contains(rand3) && isRun && !prevPressedKeys.contains(rand3)) {
				trainingStats.setStamina(trainingStats.getStamina()+5);
				rand3 = rand.nextInt(25) + 65;
			}
			
			//Escape training
			if (pressedKeys.contains(KeyEvent.VK_ESCAPE) && (background.contains("popUpBench") || background.contains("popUpRun") || background.contains("popUpBook"))){
				background.removeChild("popUpBench");
				isBench = false;
				background.removeChild("popUpBook");
				isBook = false;
				background.removeChild("popUpRun");
				isRun = false;
			}
			
			//if bear is touching training item and presses A training begins
			if (player.collidesWith(bench) && !background.contains("popUpBench") && pressedKeys.contains(KeyEvent.VK_A)) {
				isBench = true;
				background.addChild(popUpBench);
				popUpBench.addChild(aButton);
				popUpBench.addChild(bench101);
				popUpBench.addChild(player2);
				popUpBench.addChild(bench2);
				
			}
			if (player.collidesWith(book) && !this.contains("popUpBench") && pressedKeys.contains(KeyEvent.VK_A)) {
				isBook = true;
				background.addChild(popUpBook);
			}
			if(isRun) {
				player3.animate("walkRight");
				sound.PlaySoundEffect("TREADMILL");
			}
			
			if(isBench) {
				sound.PlaySoundEffect("LIFT");
				player2.animate("jumpRight");
			}
			
			if (player.collidesWith(run) && !this.contains("popUpRun") && pressedKeys.contains(KeyEvent.VK_A)) {
				isRun = true;
				background.addChild(popUpRun);
				popUpRun.addChild(player3);
				//sound.PlaySoundEffect("TREADMILL");
			}
			
			//Knowledge training
			if (num.equals(Integer.toString(add)) && pressedKeys.contains(KeyEvent.VK_ENTER) && !prevPressedKeys.contains(KeyEvent.VK_ENTER) && isBook){
				trainingStats.setKnowledge(trainingStats.getKnowledge()+20);
				rand1 = rand.nextInt(50) + 1;
				rand2 = rand.nextInt(50) + 1;
				num = "";
			}
			else if (!num.equals(Integer.toString(add)) && pressedKeys.contains(KeyEvent.VK_ENTER) && !prevPressedKeys.contains(KeyEvent.VK_ENTER) && isBook){
				num = "";
				rand1 = rand.nextInt(50) + 1;
				rand2 = rand.nextInt(50) + 1;
			}
			
			if(isBook) {
				if (pressedKeys.contains(KeyEvent.VK_0) && !prevPressedKeys.contains(KeyEvent.VK_0)) {
					num += 0;
				}
				if (pressedKeys.contains(KeyEvent.VK_1) && !prevPressedKeys.contains(KeyEvent.VK_1)) {
					num += 1;
				}
				if (pressedKeys.contains(KeyEvent.VK_2) && !prevPressedKeys.contains(KeyEvent.VK_2)) {
					num += 2;
				}
				if (pressedKeys.contains(KeyEvent.VK_3) && !prevPressedKeys.contains(KeyEvent.VK_3)) {
					num += 3;
				}
				if (pressedKeys.contains(KeyEvent.VK_4) && !prevPressedKeys.contains(KeyEvent.VK_4)) {
					num += 4;
				}
				if (pressedKeys.contains(KeyEvent.VK_5) && !prevPressedKeys.contains(KeyEvent.VK_5)) {
					num += 5;
				}
				if (pressedKeys.contains(KeyEvent.VK_6) && !prevPressedKeys.contains(KeyEvent.VK_6)) {
					num += 6;
				}
				if (pressedKeys.contains(KeyEvent.VK_7) && !prevPressedKeys.contains(KeyEvent.VK_7)) {
					num += 7;
				}
				if (pressedKeys.contains(KeyEvent.VK_8) && !prevPressedKeys.contains(KeyEvent.VK_8)) {
					num += 8;
				}
				if (pressedKeys.contains(KeyEvent.VK_9) && !prevPressedKeys.contains(KeyEvent.VK_9)) {
					num += 9;
				}
			}

			if (trainingStats.getKnowledge() >= 75 && !background.contains("book1")) {
				trainingStats.setHasMagic(true);
				sound.PlaySoundEffect("ITEM");
				background.addChild(splat);
				background.addChild(book1);
				bookTween.animate(TweenableParams.XPOS, 350, 850, 1500, TweenTransitions.LINEAR);
				bookTween.animate(TweenableParams.YPOS, 150, 650, 1500, TweenTransitions.LINEAR);
				bookTween.animate(TweenableParams.XSCALE, .5, .08, 1500, TweenTransitions.LINEAR);
				bookTween.animate(TweenableParams.YSCALE, .5, .08, 1500, TweenTransitions.LINEAR);
				splatTween.animate(TweenableParams.ALPHA, 1, 0, 1500, TweenTransitions.LINEAR);
				splatTween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(splatTween);
				bookTween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(bookTween);
			}
			if (trainingStats.getStrength() >= 70 && !background.contains("axe")) {
				trainingStats.setHasAxe(true);
				sound.PlaySoundEffect("ITEM");
				background.addChild(splat);
				background.addChild(axe);
				axe.setVisible(true);
				axeTween.animate(TweenableParams.XPOS, 350, 750, 1500, TweenTransitions.LINEAR);
				axeTween.animate(TweenableParams.YPOS, 150, 650, 1500, TweenTransitions.QUADRATIC);
				axeTween.animate(TweenableParams.XSCALE, axe.getScale()[0], .02, 1500, TweenTransitions.LINEAR);
				axeTween.animate(TweenableParams.YSCALE, axe.getScale()[1], .02, 1500, TweenTransitions.LINEAR);
				splatTween.animate(TweenableParams.ALPHA, 1, 0, 1500, TweenTransitions.LINEAR);
				splatTween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(splatTween);
				axeTween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(axeTween);
			}

			if (trainingStats.getStamina() >= 70 && !background.contains("dagger")) {
				trainingStats.setHasDagger(true);
				sound.PlaySoundEffect("ITEM");
				background.addChild(splat);
				background.addChild(dagger);
				dagger.setVisible(true);
				daggerTween.animate(TweenableParams.XPOS, 350, 800, 1500, TweenTransitions.LINEAR);
				daggerTween.animate(TweenableParams.YPOS, 150, 650, 1500, TweenTransitions.LINEAR);
				daggerTween.animate(TweenableParams.XSCALE, 1, .35, 1500, TweenTransitions.LINEAR);
				daggerTween.animate(TweenableParams.YSCALE, 1, .35, 1500, TweenTransitions.LINEAR);
				splatTween.animate(TweenableParams.ALPHA, 1, 0, 1500, TweenTransitions.LINEAR);
				splatTween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(splatTween);
				daggerTween.addEventListener(this, "TWEEN_COMPLETE_EVENT");
				TweenJuggler.add(daggerTween);
			}
			
			if (trainingStats.getStamina() >= 70 && trainingStats.getKnowledge() >= 75) {
				trainingStats.setHasLazer(true);
				trainingStats.setHasDagger(false);
				trainingStats.setHasMagic(false);
				
			}
			
			if (trainingStats.getStrength() >= 70 && trainingStats.getKnowledge() >= 75) {
				trainingStats.setHasCalibur(true);
				trainingStats.setHasMagic(false);
				trainingStats.setHasAxe(false);
			}
			
			if (trainingStats.getStrength() >= 70 && trainingStats.getStamina() >= 70) {
				trainingStats.setHasStick(true);
				trainingStats.setHasDagger(false);
				trainingStats.setHasAxe(false);
			}
			
		}
		
		//Getting previous pressed key
		prevPressedKeys.clear();
		for (int i: pressedKeys) {
			prevPressedKeys.add(i);
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		//generating fonts and strings
		Font font2 = new Font("Font2", Font.BOLD, 15);
		Font font = new Font("Font", Font.BOLD, 50);
		g.setFont(font2);
		g.drawString("Strength:" + trainingStats.getStrength(), 250, 620);
		g.drawString("Stamina:" + trainingStats.getStamina(), 250, 660);
		g.drawString("Knowledge:" + trainingStats.getKnowledge(), 250, 700);
		g.setFont(font2);
		g.drawString("Go to training area, click A to train.", 550, 40);
		g.drawString("Press ESC to exit a training zone.", 550, 60);
		if (isBench) {
			g.drawString("Spam click A.", 800, 200);
		}
		if(isBook) {
			g.setFont(font);
			g.drawString(""+rand1, 680, 135);
			g.drawString(""+rand2, 680, 180);
			g.drawLine(665, 185, 760, 185);
			g.drawString(""+num, 680, 225);
			g.setFont(font2);
			g.drawString("Type sum and press enter.", 550, 300);
			
		}
		if(isRun) {
			g.drawString("Press the letter as fast as you can!", 800, 200);
			g.setFont(font);
			g.drawString(Character.toString((char) rand3), 850, 300);
		}
		g.setFont(font2);

		
	}
	
	public PlayerStats getTrainingStats() {
		return trainingStats;
	}

	public void setTrainingStats(PlayerStats trainingStats) {
		this.trainingStats = trainingStats;
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	

}
