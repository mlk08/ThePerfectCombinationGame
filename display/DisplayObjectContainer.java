package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject {
	
	ArrayList<DisplayObject> allChildren = new ArrayList<DisplayObject>();
	

	public DisplayObjectContainer(String id) {
		super(id);
	}
	
	public void addChild(DisplayObject newChild) {
		newChild.setParent(this);
		this.allChildren.add(newChild);
	}

	public void addChildAtIndex(DisplayObject newChild, int index) {
		newChild.setParent(this);
		this.allChildren.add(index, newChild);
	}
	
	public void removeChild(String id) {
		for(int i = 0; i < this.allChildren.size(); i++) {
			if(this.allChildren.get(i).getId() == id) {
				this.allChildren.get(i).setParent(null);
				this.allChildren.remove(i);
				break;
			}
		}	
	}
	
	public void removeChildAtIndex(int index) {
		this.allChildren.get(index).setParent(null);
		this.allChildren.remove(index);
	}
	
	public void removeAllChildren() {
		for(int i = 0; i < allChildren.size(); i++) {
			this.allChildren.get(i).setParent(null);
		}
		this.allChildren.clear();
	}
	
	public boolean contains(String id) {
		int size = this.allChildren.size();
		for(int i = 0; i < size; i++) {
			if(this.allChildren.get(i).getId() == id) return true; 
		}
		return false;
	}
	
	public DisplayObject getChildByID(String id) {
		for(int i = 0; i < this.allChildren.size(); i++) {
			if(id == allChildren.get(i).getId()) {
				return allChildren.get(i);
			}
		}
		return null;
	}
	
	public DisplayObject getChildByIndex(int index) {
		return this.allChildren.get(index);
	}
	
	public void printAllChildren() {
		for(int i = 0; i < this.allChildren.size(); i++) {
			System.out.println(this.getId() + " has child i: " + i + " id: " + allChildren.get(i).getId());
		}
	}
	
	public int howManyChildren() {
		return this.allChildren.size();
	}
	
	
	public ArrayList<DisplayObject> getAllChildren() {
		return this.allChildren;	
	}
	
	@Override
	protected void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
		for(DisplayObject o: this.allChildren) o.update(pressedKeys);

	}
	
	@Override
	public void draw(Graphics g) {
		if(this.isVisible()) {
			Graphics2D g2d = (Graphics2D) g;
			super.draw(g2d);
			
			applyTransformations(g2d);
			for(int i = 0; i < this.allChildren.size(); i++) {
				this.allChildren.get(i).draw(g2d);
			}
			reverseTransformations(g2d);
		}
	}
}