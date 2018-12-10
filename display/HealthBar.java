package edu.virginia.engine.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class HealthBar extends AnimatedSprite {
	
	Sprite sprite;
	
	public HealthBar(String id, Sprite sprite) {
		super(id);
		this.sprite = sprite;
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(this.getPosition()[0], this.getPosition()[1] + 4, sprite.getMaxHealth() + 4, 20);
		
		Font font = new Font("Font", Font.BOLD, 15);
		g.setFont(font);
		g.drawString(this.getId() + ": " + sprite.getHealth(), this.getPosition()[0], this.getPosition()[1]);
		
		g2d.setColor(Color.RED);
		g2d.fillRect(this.getPosition()[0] + 2, this.getPosition()[1] + 6, sprite.getMaxHealth() - (sprite.getMaxHealth() - sprite.getHealth()), 16);

		
		
	}
}
