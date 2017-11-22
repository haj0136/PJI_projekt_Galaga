package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import core.Ship;
import core.Star;

public class Galaga extends JPanel implements Runnable {

    private final int W_HEIGHT = 600;
    private final int W_WIDTH = 500;
    private final int NUMBER_OF_ALIENS = 1;

    private Thread animator;

    private List<IGameObject> beckground;
    private List<IColisionable> aliens;
    private IMovable ship;

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

	for (int i = 0; i < beckground.size(); i++) {
	    beckground.get(i).display(g2);
	}
	for (int i = 0; i < aliens.size(); i++) {
	    aliens.get(i).display(g2);
	}
	ship.display(g2);
	
	
	g2.dispose();
    }

    private void cycle() {

	for (int i = 0; i < beckground.size(); i++) {
	    beckground.get(i).update();
	}
	
	ship.update();
    }
    
    private void loadAliens(){
	for (int i = 0; i < NUMBER_OF_ALIENS; i++) {
	    aliens.add(new Alien(W_WIDTH, W_HEIGHT, 10, 10));
	}
    }

    @Override
    public void run() {

	while (true) {

	    cycle();
	    repaint();
	    
	    if(aliens.isEmpty())
		loadAliens();

	    try {
		Thread.sleep(15);
	    } catch (InterruptedException e) {
		System.out.println("Interrupted: " + e.getMessage());
	    }
	}
    }
}
