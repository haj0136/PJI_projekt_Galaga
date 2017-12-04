package core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Ship implements IMovable {

    private int x, y;
    private int dx;
    private int width, height;
    private int canvas_width, canvas_height;
    private Image image;
    private int lives;
    private List<IColisionable> missiles;
    private boolean reloading;
    private boolean invicible;

    private Timer timer = new Timer();
    Random rand = new Random();

    public Ship(int width, int height) {
	ImageIcon ii = new ImageIcon("src/images/spco-small.png");
	image = ii.getImage();

	this.width = image.getWidth(null);
	this.height = image.getHeight(null);
	this.x = width / 2 - this.width / 2;
	this.y = height - this.height - height / 100;
	this.lives = 3;
	this.dx = 0;

	missiles = new ArrayList<>();
	canvas_height = height;
	canvas_width = width;
	reloading = false;
	invicible = false;
    }

    @Override
    public void update() {
	if(x >= 0 && x <= canvas_width - width){
	    this.x += this.dx;
	}
	else if(x < 0){
	  this.x = 0;  
	}
	else{
	  this.x = canvas_width - width;
	}
	
	// System.out.println(x);

	for (int i = 0; i < missiles.size(); i++) {
	    missiles.get(i).update();
	    if (missiles.get(i).getY() < 0 || missiles.get(i).getLives() <= 0) {
		missiles.remove(i);
	    }
	}
    }

    @Override
    public void display(Graphics2D g2) {
	if (invicible == true) {
	    if (rand.nextInt(2) == 0) {
		g2.drawImage(image, x, y, null);
	    } else {
		g2.drawImage(null, x, y, null);
	    }

	} else {
	    g2.drawImage(image, x, y, null);
	}

	Toolkit.getDefaultToolkit().sync();
	for (int i = 0; i < missiles.size(); i++) {
	    missiles.get(i).display(g2);
	}
    }

    @Override
    public void move(int keyCode) {
	if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
	    if(this.x != 0)
	    this.dx = -3;

	}
	if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
	    if(this.x != canvas_width - width)
	    this.dx = 3;
	}

	if (keyCode == KeyEvent.VK_SPACE) {
	    if (reloading == false) {
		missiles.add(new Missile(canvas_width, canvas_height, x - 3 + width / 2, y, false));
	    }
	    reloading = true;
	}

	if (keyCode == KeyEvent.VK_LEFT * -1 || keyCode == KeyEvent.VK_A * -1) {
	    if (this.dx < 0) {
		this.dx = 0;
	    }
	}
	if (keyCode == KeyEvent.VK_RIGHT * -1 || keyCode == KeyEvent.VK_D * -1) {
	    if (this.dx > 0) {
		this.dx = 0;
	    }
	}
	if (keyCode == KeyEvent.VK_SPACE * -1) {
	    reloading = false;
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

    @Override
    public Rectangle getBounds() {
	return new Rectangle(x + 15, y, width - 30, height - 10);
    }

    @Override
    public int getLives() {
	return lives;
    }

    @Override
    public void hit() {
	if (!invicible) {
	    this.lives--;
	    this.invicible = true;

	    timer.schedule(new TimerTask() {
		@Override
		public void run() {
		    invicible = false;
		}
	    }, 3 * 1000);
	}
    }

    @Override
    public List<IColisionable> getMissiles() {
	return missiles;
    }

}
