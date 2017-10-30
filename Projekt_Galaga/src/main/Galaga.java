package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import core.IGameObject;
import core.IMovable;
import core.Ship;
import core.Star;

public class Galaga extends JPanel implements Runnable {

    private final int W_HEIGHT = 600;
    private final int W_WIDTH = 500;

    private final int INITIAL_X = 0;
    private final int INITIAL_Y = 0;

    private Random rand = new Random();

    private Image star;
    private Thread animator;
    private int x, y;

    private List<IGameObject> beckground;
    private List<IGameObject> gameObjects;
    private IMovable ship;

    public Galaga() {
	beckground = new ArrayList<>();

	init();
    }

    private void loadImage() {

	ImageIcon ii = new ImageIcon("src/images/Bluegalaxian.png");
	star = ii.getImage();
    }

    private void loadGameObjects() {
	for (int i = 0; i < 30; i++) {
	    beckground.add(new Star(W_WIDTH, W_HEIGHT));
	}
	beckground.add(new Ship(W_WIDTH, W_HEIGHT));
    }

    private void init() {
	setBackground(Color.BLACK);
	setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
	setDoubleBuffered(true);

	loadImage();
	loadGameObjects();

	x = INITIAL_X;
	y = INITIAL_Y;
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
	drawStar(g2);
	
	
	g2.dispose();
    }

    private void drawStar(Graphics g) {

	g.drawImage(star, x, y, this);
	Toolkit.getDefaultToolkit().sync();
    }

    private void cycle() {

	for (int i = 0; i < beckground.size(); i++) {
	    beckground.get(i).update();
	}

	// ************************************************************************
	x += 1;
	y += 2;

	if (y > W_HEIGHT) {

	    y = INITIAL_Y;
	    x = INITIAL_X;
	}
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
