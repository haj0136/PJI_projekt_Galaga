package core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Alien implements IColisionable {

    private int x, y;
    private int width, height;
    private Image image;
    private int health;

    Random rand = new Random();

    public Alien(int canvas_width, int canvas_height, int xPos, int yPos) {
	image = loadImage();
	
	this.x = xPos;
	this.y = yPos;
    }

    @Override
    public void update() {

    }

    @Override
    public void display(Graphics2D g2) {
	g2.drawImage(image, x, y, null);
    }

    @Override
    public int getX() {
	return x;
    }

    @Override
    public int getY() {
	return y;
    }

    private Image loadImage() {
	ImageIcon ii = null;
	switch (rand.nextInt(5)) {
	case 0:
	    ii = new ImageIcon("src/images/Galagacommander.png");
	    break;
	case 1:
	    ii = new ImageIcon("src/images/Purplegalaxian.png");
	    break;
	case 2:
	    ii = new ImageIcon("src/images/Redgalaga.png");
	    break;
	case 3:
	    ii = new ImageIcon("src/images/Yellowgalaga.png");
	    break;
	case 4:
	    ii = new ImageIcon("src/images/Big_galaga2.png");
	    break;
	}
	return ii.getImage();
    }

}
