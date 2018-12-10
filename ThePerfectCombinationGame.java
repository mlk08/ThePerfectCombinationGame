package edu.virginia.thePerfectCombination;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.FightScene;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.PlayerStats;
import edu.virginia.engine.display.Training;
import edu.virginia.engine.display.Training2;
import edu.virginia.engine.util.GameClock;



/**
 * The Perfect Combination is a game where two players 
 * have to fight each other, but train first. Be smart about 
 * how you prepare for the battle!
 * 
 * @author Nace Plesko, Kelly Mckee
 *
 */
public class ThePerfectCombinationGame extends Game implements MouseListener{
	
	AnimatedSprite wall = new AnimatedSprite("wall", "bg-new.jpg", true);
	AnimatedSprite startButton = new AnimatedSprite("startButton", "start.png", true);
	AnimatedSprite ground = new AnimatedSprite("ground", "platform-new.jpg", true);
	AnimatedSprite spikes1 = new AnimatedSprite("spikes1", "spikes_left.png", true);
	AnimatedSprite spikes2 = new AnimatedSprite("spikes2", "spikes_right.png", true);
	AnimatedSprite oneWon = new AnimatedSprite("oneWon", "onewon.png", true);
	AnimatedSprite twoWon = new AnimatedSprite("twoWon", "twowon.png", true);
	AnimatedSprite exitInstead1 = new AnimatedSprite("exitInstead1", "exitinstead.png", true);
	AnimatedSprite exitInstead2 = new AnimatedSprite("exitInstead2", "exitinstead.png", true);

	Training train = new Training("train");
	Training2 train2 = new Training2("train2");
	FightScene fight;
	PlayerStats statsP1 = new PlayerStats();
	PlayerStats statsP2 = new PlayerStats();
	SoundManager sound = new SoundManager();
	boolean isFighting = false;
	boolean isTraining = false;
	boolean isTraining2 = false;
	boolean isStart = true;
	boolean isTransition = false;
	boolean isTransition2 = false;
	boolean isReady = false;
	boolean isReady2 = false;
	boolean isTransition3 = false;
	boolean isTime = false;
	int time = 0;
	int temp = 0;
	
	GameClock gameClock = new GameClock();
	
	
	public ThePerfectCombinationGame() {
		super("The Perfect Combination", 1400, 800);
		
		getMainFrame().addMouseListener(this);
		this.addChild(wall);
		this.addChild(ground);
		
		ground.addChild(startButton);
		this.addChild(spikes1);
		this.addChild(spikes2);

		oneWon.setScale(2.3, 2.3);
		oneWon.setPosition(150, -108);
		oneWon.addChild(exitInstead1);
		exitInstead1.setPosition(190, 288);
		exitInstead1.setScale(0.5, 0.5);
		
		twoWon.setScale(2.3, 2.3);
		twoWon.setPosition(150, -108);
		twoWon.addChild(exitInstead2);
		exitInstead2.setPosition(190, 288);
		exitInstead2.setScale(0.5, 0.5);
		
		startButton.setPosition(500, -250);
		ground.setPosition(0, 750);
		spikes1.setPosition(-4, 0);
		spikes2.setPosition(1317, 0);
		sound.loopSoundEffect("UPBEAT_FUNK");
		
	
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<Integer> pressedKeys){
		super.update(pressedKeys);
		
		//Add trainging stats
		train.setTrainingStats(statsP1);
		train2.setTrainingStats(statsP2);
		
		if(fight != null && fight.isOver()) {
			if(fight.isPlayer1Won())
					this.addChild(oneWon);
			else this.addChild(twoWon);
		}
		
		if(time == 5 && !isTime) {
			isTime= true;
			sound.PlaySoundEffect("COUNT");
		}

		//Game Start
		if ((int) gameClock.getElapsedTime() / 1000 > temp && !isStart) {
			time--;
			//transition 5 seconds
			if (time <= 0 && isTransition && !this.contains("train")) {
				time = 30;
				isTransition = false;
				isTraining = true;
				this.addChild(train);
			}
			
			//training 10sec
			if (time <= 0 && isTraining) {
				//time = 20;
				this.removeChild("train");
				isReady = true;
				isTraining = false;
			}
			

			if (time <=0 && isTransition2  && !this.contains("train2")) {
				System.out.println("transition2");
				time= 30;
				isTraining2 = true;
				isTransition2 = false;
				this.addChild(train2);
			}
			if(time <= 0 && isTraining2) {
				System.out.println("train2");
				isTraining2 = false;
				this.removeChild("train2");
				isReady2 = true;
			}
			
	
			if (time <=0 && isTransition3) {
				System.out.println("transition3");
				isFighting = true;
				isTransition3 = false;
			}
			
			if(isFighting  && !this.contains("fight")) {
				System.out.println("fight");
				if(fight == null) {
					fight = new FightScene("fight", this);
					fight.setTrainingStatsP1(statsP1);
					fight.setTrainingStatsP2(statsP2);
				}
				this.addChild(fight);
				this.removeChild("train2");
				this.removeChild("spikes1");
				this.removeChild("spikes2");
			}
		
		}
		temp = (int) gameClock.getElapsedTime() / 1000;
	

	}
		
	/**
	 * Engine automatically invokes draw() every frame as well
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		Font font = new Font("Font2", Font.BOLD, 40);
		Font font2 = new Font("Font2", Font.BOLD, 60);
		Font font3 = new Font("Font2", Font.PLAIN, 30);
		
		if(isStart) {
			g.setFont(font);
			g.drawString("The Perfect Combination Game", 360, 100);
			g.setFont(font3);
			g.drawString("Training Instructions: ", 250, 200);
			g.drawString("Use arrow keys to move bear to training area, then press A. ", 250, 230);
			g.drawString("Get minimum 70 points to unlock weapon. ", 250, 260);
			
			g.drawString("Fighting Instructions: ", 250, 330);
			g.drawString("Player 1 uses arrow keys to move and L for attacks.", 250, 360);
			g.drawString("Player 2 uses ASDW to move and 1 for attacks.", 250, 400);
			g.setFont(font);
		}
		if(isTraining || isTraining2) {
			g.setFont(font);
			g.drawString("Time: "+time, 600, 660);
		}
		if(isTransition) {
			g.setFont(font);
			g.drawString("Player 1: Traning Begins in", 380, 100);
			g.drawString("Get 70 points to get weapon!", 380, 600);

			g.setFont(font2);
			g.drawString(""+time, 650, 300);
			
					}
		if(isReady) {
			g.setFont(font);
			g.drawString("Player 2:"
					+ "Click to begin training", 380, 100);
		}
		if(isTransition2) {
			g.setFont(font);
			g.drawString("Player 2:"
					+ " Training Begins in", 380, 100);
			g.drawString("Get 70 points to get weapon!", 380, 600);
			g.setFont(font2);
			g.drawString(""+time, 650, 300);
		
		}
		if(isReady2) {
			System.out.println("why");
			g.setFont(font);
			g.drawString("Click when ready to battle", 380, 100);
			g.setFont(font3);
			g.drawString("Fighting Instructions: ", 250, 330);
			g.drawString("Player 1 uses arrow keys to move and L for attacks.", 250, 360);
			g.drawString("Player 2 uses ASDW to move and 1 for attacks.", 250, 400);
		}
		if(isTransition3) {
			g.setFont(font);
			g.drawString("Battle Begins in", 380, 100);
			g.setFont(font2);
			g.drawString(""+time, 650, 300);
		}

		
		
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		ThePerfectCombinationGame game = new ThePerfectCombinationGame();
		game.start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getY());
		//If start is clicked start transition
		Rectangle startHit = new Rectangle(560, 525, 315, 95);
		if( isStart && startHit.contains(e.getX() - 7, e.getY() - 35)) {
			ground.removeChild("startButton");
			isStart = false;
			isTransition = true;
			time =5;
			isTime = false;
		}
		if(isReady) {
			System.out.println("clickready");
			isTransition2 = true;
			isReady = false;
			time=5;
			isTime= false;
		}
		
		if(isReady2) {
			System.out.println("clickready");
			isTransition3 = true;
			isReady2 = false;
			time=5;
			isTime=false;
		}
		
		if( fight != null && fight.isOver() && exitInstead1.getGlobalHitbox().contains(e.getX() - 7, e.getY() - 35)) {
			System.out.println("exiting");
			this.closeGame();
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}