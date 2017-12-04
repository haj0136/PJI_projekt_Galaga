package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Missile implements IColisionable {

    private final int MISSILE_SPEED = 3;
    private final int WIDTH = 5;
    private final int HEIGHT = 15;

    private int lives;
    private int x, y;
    private boolean enemy;

    public Missile(int canvas_width, int canvas_height, int xPos, int yPos, boolean enemy) {
	this.x = xPos;
	this.y = yPos - HEIGHT;

	lives = 1;
	this.enemy = enemy;
    }

    @Override
    public void update() {
	if (enemy) {
	    this.y += MISSILE_SPEED;
	} else {
	    this.y -= MISSILE_SPEED;
	}
    }

    @Override
    public void display(Graphics2D g2) {
	if (enemy) {
	    g2.setColor(Color.RED);
	} else {
	    g2.setColor(Color.GREEN);
	}
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

    @Override
    public Rectangle getBounds() {
	return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    public int getLives() {
	return lives;
    }

    @Override
    public void hit() {
	this.lives--;
    }

}
