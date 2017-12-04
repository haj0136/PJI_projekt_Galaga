package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Star implements IGameObject {

    private int x, y;
    private float speed;
    private int width, height;
    private final int W_HEIGHT, W_WIDTH;

    Random rand = new Random();

    public Star(int width, int height) {
	x = rand.nextInt(width + 1);
	y = rand.nextInt(height + 1);

	this.width = rand.nextInt((5 - 2) + 1) + 2;
	this.height = this.width;
	this.speed = this.height;
	this.W_HEIGHT = height;
	this.W_WIDTH = width;
    }

    @Override
    public void update() {
	y += speed;

	if (y > W_HEIGHT) {
	    y = 0;
	    x = rand.nextInt(W_WIDTH + 1);
	}
    }

    @Override
    public void display(Graphics2D g2) {
	// g.setColor(Color.WHITE);
	// g.fillOval(x, y, width, height);
	int type = rand.nextInt(3);

	if (type != 0)
	    g2.setColor(Color.GRAY);
	else
	    g2.setColor(Color.WHITE);

	g2.fillOval(x, y, width, height);
    }
}
