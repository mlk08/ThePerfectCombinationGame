package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class AnimatedSprite extends Sprite {
	
	ArrayList<BufferedImage> animation_images = new ArrayList<BufferedImage>();
	private int[] start_end = {0, 0};
	private int current_frame = start_end[0];
	private int animationSpeed = 15; //change picture per x frames
	private int frameCounter = 0;

	public AnimatedSprite(String id) {
		super(id);
	}
	
	public AnimatedSprite(String id, String imageFileName, boolean isOnePic) {
		super(id, imageFileName);
		if(isOnePic) {
			this.setImage(imageFileName);
		}
		else {
			getAnimatedSprites();
			//getMirroredAnimatedSprites();
			this.setImage(animation_images.get(1));
			getSecondSprite();
		}
	}
	
	public void getAnimatedSprites() {
		animation_images.add(readImage("pick.png"));
		animation_images.add(readImage("static.png"));
		animation_images.add(readImage("walk1.png"));
		animation_images.add(readImage("walk2.png"));
		animation_images.add(readImage("jump1.png"));
		animation_images.add(readImage("jump2.png"));
		animation_images.add(readImage("hit1.png"));
		animation_images.add(readImage("hit2.png"));
		animation_images.add(readImage("kick1.png"));
		animation_images.add(readImage("kick2.png"));
	}
	
	public void stand() {
		this.start_end[0] = 0;
		this.start_end[1] = 0;
	}
	
	public void setAnimationSpeed(int perXframes) {
		this.animationSpeed = perXframes;
	}
	
	public void animate(String action) {
		switch (action) {
			case "walkLeft":
				this.start_end[0] = 12;
				this.start_end[1] = 13;
				break;
			case "walkRight":
				this.start_end[0] = 2;
				this.start_end[1] = 3;
				break;
			case "jumpRight":
				this.start_end[0] = 5;
				this.start_end[1] = 5;
				break;
			case "pickRight":
				this.start_end[0] = 0;
				this.start_end[1] = 1;
				break;
				
			case "walkRight2":
				this.start_end[0] = 10;
				this.start_end[1] = 11;
				break;
				
			default:
				this.start_end[0] = 0;
				this.start_end[1] = 0;
		}	
		
		frameCounter++;

		
		if((frameCounter >= animationSpeed)) {
			current_frame++;
			frameCounter = 0;
		}
		
		if(current_frame > start_end[1] || current_frame < start_end[0]) {
			current_frame = start_end[0];
		}
		
		this.setImage(animation_images.get(current_frame));
	}
	
	public void getMirroredAnimatedSprites() {
		int counter = animation_images.size();
		for(int i = 0; i < counter; i++) {
			BufferedImage mimg = new BufferedImage(animation_images.get(i).getWidth(), animation_images.get(i).getHeight(), BufferedImage.TYPE_INT_ARGB);
			 for(int y = 0; y < animation_images.get(i).getHeight(); y++){
			      for(int lx = 0, rx = animation_images.get(i).getWidth() - 1; lx < animation_images.get(i).getWidth(); lx++, rx--){
			        int p = animation_images.get(i).getRGB(lx, y);
			        mimg.setRGB(rx, y, p);
			      }
			    }
			
			animation_images.add(mimg);
		}		
	}
	
	public void getSecondSprite() {
		for(int i = 2; i < 4; i++) {
			BufferedImage temp = deepCopy(animation_images.get(i));
			
			for(int j = 0; j < temp.getWidth(); j++) {
				for(int k = 0; k < temp.getHeight(); k++) {
					temp.setRGB(j, k, temp.getRGB(j, k) + 100);
					
				}
			}
			animation_images.add(temp);
			
		}		
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
	}
}
