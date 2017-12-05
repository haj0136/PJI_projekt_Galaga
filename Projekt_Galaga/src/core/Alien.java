package core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public class Alien implements IShootable {

    private int x, y;
    private int width, height;
    private int canvas_width, canvas_height;
    private Image image;
    private int lives;
    private int step = 1;
    private int xBound = 50;
    private List<IColisionable> missiles;
    private int fireChance; // higher fireChance = less chance to shoot

    private int baseYPos, baseXPos;
    private boolean intro = true;

    Random rand = new Random();

    /************************
     * 
     * type 0-4 : choose look of alien
     */
    public Alien(int canvas_width, int canvas_height, int xPos, int yPos, int type, int level) {
	image = loadImage(type);

	this.x = xPos;
	this.y = yPos - 500;
	this.canvas_height = canvas_height;
	this.canvas_width = canvas_width;
	this.width = image.getWidth(null);
	this.height = image.getHeight(null);
	this.missiles = new ArrayList<>();

	this.fireChance /= level;

	baseYPos = yPos;
	baseXPos = xPos;
    }

    @Override
    public void update() {

	if (intro) {
	    if (this.y <= baseYPos) {
		this.y += 3;
	    } else {
		intro = false;
	    }
	} else {
	    if (this.x >= baseXPos - xBound && this.x <= baseXPos + xBound) {
		this.x += step;
		if (this.x == baseXPos - xBound || this.x == baseXPos + xBound)
		    step = step * -1;
	    }
	}

	if (rand.nextInt(fireChance) == 0) {
	    shoot();
	}

	for (int i = 0; i < missiles.size(); i++) {
	    missiles.get(i).update();
	    if (missiles.get(i).getY() > canvas_height || missiles.get(i).getLives() <= 0) {
		missiles.remove(i);
	    }
	}

    }

    @Override
    public void display(Graphics2D g2) {
	g2.drawImage(image, x, y, null);

	for (int i = 0; i < missiles.size(); i++) {
	    missiles.get(i).display(g2);
	}
    }

    @Override
    public int getX() {
	return x;
    }

    @Override
    public int getY() {
	return y;
    }

    private Image loadImage(int type) {
	ImageIcon ii = null;
	URL url = null;
	switch (type) {
	case 0:
	    url = Alien.class.getResource("/resources/Redgalaga.png");
	    ii = new ImageIcon(url);
	    this.fireChance = 4000;
	    this.lives = 3;
	    break;
	case 1:
	    url = Alien.class.getResource("/resources/Yellowgalaga.png");
	    ii = new ImageIcon(url);
	    this.fireChance = 4000;
	    this.lives = 3;
	    break;
	case 2:
	    url = Alien.class.getResource("/resources/Galagacommander.png");
	    ii = new ImageIcon(url);
	    this.fireChance = 5000;
	    this.lives = 2;
	    break;
	case 3:
	    url = Alien.class.getResource("/resources/Big_galaga2.png");
	    ii = new ImageIcon(url);
	    this.fireChance = 3000;
	    this.lives = 5;
	    break;
	default:
	    url = Alien.class.getResource("/resources/Bluegalaxian.png");
	    ii = new ImageIcon(url);
	    this.fireChance = 2000;
	    this.lives = 8;
	    break;
	}
	return ii.getImage();
    }

    @Override
    public Rectangle getBounds() {
	return new Rectangle(x, y, width, height);
    }

    @Override
    public int getLives() {
	return lives;
    }

    @Override
    public void hit() {
	this.lives--;
    }

    private void shoot() {
	if (!intro) {
	    missiles.add(new Missile(canvas_width, canvas_height, x - 3 + width / 2, y + height, true));
	}
    }

    @Override
    public List<IColisionable> getMissiles() {
	return this.missiles;
    }

}
