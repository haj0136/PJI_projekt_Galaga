package core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import main.Galaga;

public class Ship implements IGameObject {

    private int x, y;
    private int width, height;
    private Image image;
    private int health;
    
    public Ship(int width, int height){
	ImageIcon ii = new ImageIcon("src/images/Galagaship.png");
	image = ii.getImage();
	
	this.width = image.getWidth(null);
	this.height = image.getHeight(null);
	this.x = width /2  - this.width/2 ;
	this.y = height - this.height - height/100 ;
	this.health = 3;
    }
    
    @Override
    public void update() {

    }

    @Override
    public void display(Graphics2D g2) {
	g2.drawImage(image, x, y, null);
	Toolkit.getDefaultToolkit().sync();
    }

}
