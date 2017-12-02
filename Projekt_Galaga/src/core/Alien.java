package core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
    
    private int baseYPos, baseXPos;
    private boolean intro = true;

    Random rand = new Random();

    /************************
     * 
     * type 0-4
     * : choose look of alien
     */
    public Alien(int canvas_width, int canvas_height, int xPos, int yPos, int type) {
	image = loadImage(type);
	
	this.x = xPos;
	this.y = yPos - 500;
	this.canvas_height = canvas_height;
	this.canvas_width = canvas_width;
	this.width = image.getWidth(null);
	this.height = image.getHeight(null);
	this.missiles = new ArrayList<>();

	
	baseYPos = yPos;
	baseXPos = xPos;
    }

    @Override
    public void update() {
	
	if (intro) {
	    if (this.y != baseYPos) {
		this.y++;
	    }else{
		intro = false;
	    }
	}else{
	    if(this.x >= baseXPos - xBound && this.x <= baseXPos + xBound){
	    	this.x += step;
	    	if(this.x == baseXPos - xBound || this.x == baseXPos + xBound)
	    	    step = step*-1;
	    }
	}
	
	if(rand.nextInt(5000) == 0){
	    shoot();
	}
	
	for (int i = 0; i < missiles.size(); i++) {
	    missiles.get(i).update();
	    if(missiles.get(i).getY() > canvas_height || missiles.get(i).getLives() <= 0){
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
	switch (type) {
	case 0:
	    ii = new ImageIcon("src/images/Redgalaga.png");
	    this.lives = 2;
	    break;
	case 1:
	    ii = new ImageIcon("src/images/Yellowgalaga.png");
	    this.lives = 2;
	    break;
	case 2:
	    ii = new ImageIcon("src/images/Galagacommander.png");
	    this.lives = 1;
	    break;
	case 3:
	    ii = new ImageIcon("src/images/Big_galaga2.png");
	    this.lives = 3;
	    break;
	default: ii = new ImageIcon("src/images/Galagacommander.png");
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
    
    private void shoot(){
	missiles.add(new Missile(canvas_width, canvas_height, x - 3 + width/2, y + height, true));
    }

    @Override
    public List<IColisionable> getMissiles() {
	return this.missiles;
    }

}
