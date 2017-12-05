package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import core.Alien;
import core.IColisionable;
import core.IGameObject;
import core.IMovable;
import core.IShootable;
import core.Ship;
import core.Star;
import scoreSaver.HighScoreManager;
import utils.IO;

public class Galaga extends JPanel implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = -7633361599519463715L;

    private final int W_HEIGHT = 700;
    private final int W_WIDTH = 500;
    private final int NUMBER_OF_ALIENS = 8;

    private Thread animator;
    private boolean ingame;

    private List<IGameObject> beckground;
    private List<IShootable> aliens;
    private IMovable ship;
    private int score;
    private int level;
    HighScoreManager hm;

    public Galaga() {
	beckground = new ArrayList<>();
	aliens = new ArrayList<>();

	addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
		    ship.move(e.getKeyCode());
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
		    ship.move(e.getKeyCode());
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		    ship.move(e.getKeyCode());
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		    if (ingame == false) {
			resetGame();
			ingame = true;
		    }
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		    System.exit(ERROR);
		}
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
		    ship.move(e.getKeyCode() * -1);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
		    ship.move(e.getKeyCode() * -1);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		    ship.move(e.getKeyCode() * -1);
		}
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
	level = 0;

	hm = new HighScoreManager();

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

	if (ingame) {
	    drawObjects(g2);
	} else {
	    drawGameOver(g2);
	}

	g2.dispose();
    }

    private void cycle() {

	if (ingame) {
	    for (int i = 0; i < beckground.size(); i++) {
		beckground.get(i).update();
	    }
	    for (int i = 0; i < aliens.size(); i++) {
		aliens.get(i).update();
		if (aliens.get(i).getLives() <= 0) {
		    aliens.remove(i);
		    this.score += 10;
		}
	    }
	    ship.update();
	    if (ship.getLives() <= 0) {
		ingame = false;
		hm.addScore(IO.enter("Enter your nickname", ""), this.score);
	    }
	    checkCollisions();
	    if (aliens.isEmpty())
		loadAliens();
	}
    }

    private void loadAliens() {

	this.level++;
	for (int i = 0; i < NUMBER_OF_ALIENS; i++) {
	    for (int j = 2; j >= 0; j--) {
		if (j == 0) {
		    if (i != 0 && i != NUMBER_OF_ALIENS - 1) {
			aliens.add(
				new Alien(W_WIDTH, W_HEIGHT, (W_WIDTH / 2 - (NUMBER_OF_ALIENS / 2 * 45) - 3) + (i * 45),
					(W_HEIGHT / 4) + (j * 25), 3, level));
			if (i != 1 && i != NUMBER_OF_ALIENS - 2) {
			    if (level % 3 == 0) {
				aliens.add(new Alien(W_WIDTH, W_HEIGHT,
					(W_WIDTH / 2 - (NUMBER_OF_ALIENS / 2 * 55) - 3) + (i * 55),
					(W_HEIGHT / 4) + (j - 1 * 35), 5, level));
			    } 
			}
		    }
		} else if (j == 1) {
		    aliens.add(new Alien(W_WIDTH, W_HEIGHT, (W_WIDTH / 2 - (NUMBER_OF_ALIENS / 2 * 35) - 3) + (i * 35),
			    (W_HEIGHT / 4) + (j * 45), i % 2, level));
		} else {
		    aliens.add(new Alien(W_WIDTH, W_HEIGHT, (W_WIDTH / 2 - (NUMBER_OF_ALIENS / 2 * 25) - 3) + (i * 25),
			    (W_HEIGHT / 4) + (j * 35), 2, level));
		}
	    }
	}
    }

    private void checkCollisions() {
	List<IColisionable> missiles = ship.getMissiles();

	for (IColisionable missile : missiles) {
	    Rectangle r1 = missile.getBounds();

	    for (IColisionable alien : aliens) {
		Rectangle r2 = alien.getBounds();

		if (r1.intersects(r2)) {
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

		if (r2.intersects(r1)) {
		    missile.hit();
		    ship.hit();
		}
	    }
	}
    }

    private void drawObjects(Graphics2D g2) {
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
	g2.drawString("Level: " + level, W_WIDTH - 70, 15);
    }

    private void drawGameOver(Graphics2D g2) {
	// game over text
	String msg = "Game Over";
	Font small = new Font("Goudy Stout", Font.BOLD, 40);
	FontMetrics fm = getFontMetrics(small);

	g2.setColor(Color.YELLOW);
	g2.setFont(small);
	g2.drawString(msg, (W_WIDTH - fm.stringWidth(msg)) / 2, W_HEIGHT / 3);

	// score text

	g2.setFont(new Font("Goudy Stout", Font.PLAIN, 20));
	g2.drawString("Score: " + this.score, W_WIDTH / 2 - 100, W_HEIGHT / 3 + 45);

	// top 5 score
	g2.setFont(new Font("Halvetica", Font.PLAIN, 15));
	g2.drawString("Top 5 score :", W_WIDTH / 2 - 50, W_HEIGHT / 3 + 70);

	String line;
	int size = hm.getScores().size();
	if (size > 5){
	    size = 5;
	}
	for (int i = 0; i < size; i++) {
	    line = i + 1 + ". " + hm.getScores().get(i).getName() + " - " + hm.getScores().get(i).getScore();
	    g2.drawString(line, W_WIDTH / 2 - 60, W_HEIGHT / 3 + 90 + i * 15);
	}

	// options text
	g2.setFont(new Font("Halvetica", Font.ITALIC, 15));
	g2.drawString("ENTER = retry", 10, W_HEIGHT - 100);
	g2.drawString("ESC = exit", 10, W_HEIGHT - 80);

    }

    private void resetGame() {
	this.ship = new Ship(W_WIDTH, W_HEIGHT);
	this.score = 0;
	this.aliens.clear();
	this.level = 0;
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
