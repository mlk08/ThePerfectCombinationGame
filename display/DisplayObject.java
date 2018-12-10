package edu.virginia.engine.display;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.virginia.engine.event.EventDispatcher;


/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject extends EventDispatcher{
		
	private DisplayObject parent;
	
	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;
	
	//should be true iff this display object is meant to be drawn
	private boolean visible = true;
	
	//should describe the x,y position where this object will be drawn	
	int[] position = new int[2]; // 0 is x and 1 is y
	
	//The point, relative to the upper left corner of the image that is the origin of this object
	int[] pivotPoint = {0, 0}; // 0 is x and 1 is y
	
	double[] scale = {1.0, 1.0};
	double rotation = 0;
	double alpha = 1.0;
	
	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this(id, "dummy.png");
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<Integer> pressedKeys) {

	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		if (displayImage != null && this.isVisible()) {
			
			Graphics2D g2d = (Graphics2D) g;
			
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(displayImage, -pivotPoint[0], -pivotPoint[1],
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
		//	g2d.fillArc(-5, -5, 10, 10, 0, 360);			
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(this.position[0], this.position[1]);
		g2d.rotate(Math.toRadians(rotation));
	
		g2d.scale(scale[0], scale[1]);
		
		if(!visible) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
		else g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		
		g2d.scale(1/scale[0], 1/scale[1]);
		g2d.rotate(-Math.toRadians(rotation));
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2d.translate(-this.position[0], -this.position[1]);
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean b) {
		this.visible = b;
	}
	
	public int[] getPosition() {
		return this.position;
	}
	
	public void setPosition(int x, int y) {
		this.position[0] = x;
		this.position[1] = y;
	}
	
	public void translateX(int x) {
		this.position[0] += x;
	}
	
	public void translateY(int y) {
		this.position[1] += y;
	}
	
	public int[] getPivotPoint() {
		return this.pivotPoint;
	}
	
	public void setPivotPoint(int x, int y) {
		this.pivotPoint[0] = x;
		this.pivotPoint[1] = y;
	}
	
	public double getRotation() {
		return this.rotation;
	}
	
	public void rotate(double x) {
		this.rotation += x;
	}
	
	public void setRotation(double x) {
		this.rotation = x;
	}
	
	public double getAlpha() {
		return this.alpha;
	}
	
	public void setAlpha(double x) {
		if(x > 1) this.alpha = 1;
		else if (x < 0) this.alpha = 0;
		else this.alpha = x;
	}
	
	public void setScale(double x, double y) {
		this.scale[0] = x;
		this.scale[1] = y;
	}
	
	public double[] getScale() {
		return this.scale;
	}
	
	public void setParent(DisplayObject parent) {
		this.parent = parent;
	}
	
	public DisplayObject getParent() {
		return this.parent;
	}
	
	public int getScaledHeight() {
		return (int) (this.getUnscaledHeight() * this.getGlobalScaleY());
	}
	
	public int getScaledWidth() {
		return (int) (this.getUnscaledWidth() * this.getGlobalScaleX());
	}
	
	public double getGlobalScaleX() {
		if(this.parent == null) {
			return this.getScale()[0];
		}
		
		else {
			return this.getScale()[0] * this.parent.getGlobalScaleX();
		}
	}
	
	public double getGlobalScaleY() {
		if(this.parent == null) {
			return this.getScale()[1];
		}
		
		else {
			return this.getScale()[1] * this.parent.getGlobalScaleY();
		}
	}	 
	
	 public void printAllParents() {

		 if(this.parent == null) {
			 return;
		 }

		 else {
			 System.out.println("parent: " + this.getParent().getId());
			 this.getParent().printAllParents();
		 }
	 }
	 
	 public Point getGlobalPosition() {
		 return localToGlobal(new Point(this.getPosition()[0], this.getPosition()[1]));
	 }
	 
	 public Point localToGlobal(Point p) {
		 if(this.getParent() == null) {
			 return p;
		 }
		 
		 else {
			 Point parentGlobal = this.getParent().localToGlobal(new Point(this.getParent().getPosition()[0], this.getParent().getPosition()[1]));
			 return  new Point((int) (parentGlobal.getX() + p.getX()), (int) (parentGlobal.getY() + p.getY()));
		 }
	 }
	 
	 protected AffineTransform getGlobalTransform(){
			AffineTransform at;
			if(this.parent == null) at = new AffineTransform();
			else{
				at = this.getParent().getGlobalTransform();
				at.translate(this.getParent().getPivotPoint()[0], this.getParent().getPivotPoint()[1]);
				
			}
			
			at.concatenate(getLocalTransform());
			return at;
		}
			
		private AffineTransform getLocalTransform(){
			AffineTransform at = new AffineTransform();
			at.translate(this.getPosition()[0], this.getPosition()[1]);
			at.rotate(Math.toRadians(this.rotation));
			at.scale(this.getScale()[0], this.getScale()[1]);
			at.translate(-this.getPivotPoint()[0], -this.getPivotPoint()[1]);
			return at;
		}
		
		public Shape getGlobalHitbox(){
			return getGlobalTransform().createTransformedShape(new Rectangle(0, 0, getUnscaledWidth(), getUnscaledHeight()));
		}
			
		public Shape getLocalHitbox(){
			return getLocalTransform().createTransformedShape(new Rectangle(0, 0, getUnscaledWidth(), getUnscaledHeight()));
		}
		
		public boolean collidesWith(DisplayObject other){
			Area a = new Area(getGlobalHitbox());
			a.intersect(new Area(other.getGlobalHitbox()));
			return !a.isEmpty();
		}
}