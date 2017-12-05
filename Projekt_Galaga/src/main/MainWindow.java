package main;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import core.Ship;

public class MainWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 2427719776404652569L;

    public MainWindow() {
	initUI();
    }

    private void initUI() {
	add(new Galaga());
	setResizable(false);
	pack();

	setTitle("Galaga");
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	try {
	    setIconImage(ImageIO.read(new File("src/resources/Galagaship.png")));
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		JFrame ex = new MainWindow();
		ex.setVisible(true);
	    }
	});
    }
}
