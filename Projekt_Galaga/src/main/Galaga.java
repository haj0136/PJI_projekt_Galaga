package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import core.Alien;
import core.IColisionable;
import core.IGameObject;
import core.IMovable;
import core.IShootable;
import core.Ship;
import core.Star;

public class Galaga extends JPanel implements Runnable {

    private final int W_HEIGHT = 700;
    private final int W_WIDTH = 500;
    private final int NUMBER_OF_ALIENS = 8;

    private Thread animator;
    private boolean ingame;

    private List<IGameObject> beckground;
    private List<IShootable> aliens;
    private IMovable ship;
    private int score;

    public Galaga() {
	beckground = new ArrayList<>();
	aliens = new ArrayList<>();
	
	addKeyListener(new KeyAdapter() {
	         @Override
	         public void keyPressed(KeyEvent e) {
	             System.out.println(e.getKeyCode());
	     		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
	     		    ship.move(e.getKeyCode());
	     		}
	     		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
	     		    ship.move(e.getKeyCode());
	     		}
	     		if(e.getKeyCode() == KeyEvent.VK_SPACE){
	     		    ship.move(e.getKeyCode());
	     		}
	         }
	        @Override
	        public void keyReleased(KeyEvent e) {
	            ship.move(0);
	        }
	        
	});
	
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);
	init();
    }

    private void loadGameObjects() {
	for (int i = 0; i < 30; i++) {
	    beckground.add(new Star(W_WIDTH, W_HEIGHT));
	}
	ship = new Ship(W_WIDTH, W_HEIGHT);
    }

    private void init() {
	setBackground(Color.BLACK);
	setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
	setDoubleBuffered(true);

	loadGameObjects();
	ingame = true;
	score = 0;

    }

    @Override
    public void addNotify() {
	super.addNotify();

	animator = new Thread(this);
	animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D) g.create();
	
	super.paintComponent(g2);
	
	if(ingame){
	    drawObjects(g2);
	}else{
	    
	}
	
	g2.dispose();
    }

    private void cycle() {

	for (int i = 0; i < beckground.size(); i++) {
	    beckground.get(i).update();
	}
	for (int i = 0; i < aliens.size(); i++) {
	    aliens.get(i).update();
	    if(aliens.get(i).getLives() <= 0){
		aliens.remove(i);
		this.score += 10;
	    }
	}
	
	ship.update();

	
	checkCollisions();
	
	if(aliens.isEmpty())
	    loadAliens();
    }
    
    private void loadAliens(){
	
	for (int i = 0; i < NUMBER_OF_ALIENS; i++) {
	    for (int j = 2; j >= 0; j--) {
		if(j == 0){
		    if (i != 0 && i != NUMBER_OF_ALIENS -1) {
			aliens.add(new Alien(W_WIDTH, W_HEIGHT,
				(W_WIDTH / 2 - (NUMBER_OF_ALIENS / 2 * 45) - 3) + (i * 45), (W_HEIGHT/4) + (j * 25), 3));
		    }
		}
		else if(j == 1){
		    aliens.add(new Alien(W_WIDTH, W_HEIGHT, (W_WIDTH / 2 - (NUMBER_OF_ALIENS/2 * 35) - 3) + (i * 35), (W_HEIGHT/4) + (j * 45), i%2));
		}else{
		    aliens.add(new Alien(W_WIDTH, W_HEIGHT, (W_WIDTH / 2 - (NUMBER_OF_ALIENS/2 * 25) - 3) + (i * 25), (W_HEIGHT/4) + (j * 35), 2));
		}
	    }
	}
    }
    
    private void checkCollisions(){
	List<IColisionable> missiles = ship.getMissiles();
	
	for (IColisionable missile : missiles) {
	    Rectangle r1 = missile.getBounds();
	    
	    for (IColisionable alien : aliens) {
		Rectangle r2 = alien.getBounds();
		
		if(r1.intersects(r2)){
		    missile.hit();
		    alien.hit();
		}
	    }
	}
	
	Rectangle r2 = ship.getBounds();
	for (IShootable alien : aliens) {
	    missiles = alien.getMissiles();
	    
	    for (IColisionable missile : missiles) {
		Rectangle r1 = missile.getBounds();
		    
		if(r2.intersects(r1)){
		    missile.hit();
		    ship.hit();
		}
	    }
	}
    }
    
    private void drawObjects(Graphics2D g2){
	    for (int i = 0; i < beckground.size(); i++) {
		    beckground.get(i).display(g2);
		}
		for (int i = 0; i < aliens.size(); i++) {
		    aliens.get(i).display(g2);
		}
		ship.display(g2);
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Halvetica", Font.BOLD, 14));
		g2.drawString("Score: " + score, 5, 15);
		g2.drawString("Lives: " + ship.getLives(), 5, W_HEIGHT - 15); 
    }

    @Override
    public void run() {

	while (true) {

	    cycle();
	    repaint();

	    try {
		Thread.sleep(15);
	    } catch (InterruptedException e) {
		System.out.println("Interrupted: " + e.getMessage());
	    }
	}
    }
}
