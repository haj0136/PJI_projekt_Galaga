package core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import main.Galaga;

public class Ship implements IGameObject, IMovable {

    private int x, y;
    private int dx;
    private int width, height;
    private int canvas_width, canvas_height;
    private Image image;
    private int health;
    private List<IColisionable> missiles;
    private boolean reload;

    public Ship(int width, int height) {
	ImageIcon ii = new ImageIcon("src/images/spco-small.png");
	image = ii.getImage();

	this.width = image.getWidth(null);
	this.height = image.getHeight(null);
	this.x = width / 2 - this.width / 2;
	this.y = height - this.height - height / 100;
	this.health = 3;
	this.dx = 0;
	
	missiles = new ArrayList<>();
	canvas_height = height;
	canvas_width = width;
	reload = false;
    }

    @Override
    public void update() {
	this.x += this.dx;
//	System.out.println(x);
	
	
	for (int i = 0; i < missiles.size(); i++) {
	    missiles.get(i).update();
	    if(missiles.get(i).getY() < 0){
		missiles.remove(i);
	    }
	}
    }

    @Override
    public void display(Graphics2D g2) {
	g2.drawImage(image, x, y, null);
	Toolkit.getDefaultToolkit().sync();
	
	for (int i = 0; i < missiles.size(); i++) {
	    missiles.get(i).display(g2);
	}
    }

    @Override
    public void move(int keyCode) {
	if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
	    this.dx = -2;

	}
	if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
	    this.dx = 2;
	}
	if (keyCode == 0) {
	    this.dx = 0;
	    reload = false;
	}
	if (keyCode == KeyEvent.VK_SPACE) {
	    if(reload == false){
		missiles.add(new Missile(canvas_width, canvas_height, x - 3 + width/2, y));
	    }
	    reload = true;
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

}
