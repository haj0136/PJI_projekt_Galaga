package main;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	public MainWindow(){
		initUI();
	}
	
	private void initUI() {
		add(new Galaga());
		setResizable(false);
		pack();
		
		setTitle("Galaga");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{ 
			setIconImage(ImageIO.read(new File("src/images/Galagaship.png")));
		} 
	    catch (IOException e){
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
