package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class Missile implements IColisionable {
    
    private final int MISSILE_SPEED = 3;
    private final int WIDTH = 5;
    private final int HEIGHT = 15;
    
    private int x, y;
    
    public Missile(int canvas_width, int canvas_height, int xPos, int yPos) {
	this.x = xPos;
	this.y = yPos - HEIGHT;
    }

    @Override
    public void update() {
	this.y -= MISSILE_SPEED;
    }

    @Override
    public void display(Graphics2D g2) {
	g2.setColor(Color.GREEN);
	g2.fillOval(x, y, WIDTH, HEIGHT);
    }

    @Override
    public int getX() {
	return x;
    }

    @Override
    public int getY() {
	return y;
    }

}
