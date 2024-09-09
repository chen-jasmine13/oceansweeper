import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import java.awt.Color;
import javax.sound.sampled.*;

public class Win extends JPanel implements MouseListener{
    private JFrame frame;
    private ImageIcon background;
	private Clip backgroundMusic;

    public Win(JFrame frame) {
		//creates frame
        this.frame = frame;
        setLayout(null);
		setBackground(Color.WHITE);

        ImageIcon gif = new ImageIcon("images/win.gif");

        JLabel wingif = new JLabel(gif);
        wingif.setBounds(0, 20, gif.getIconWidth(), 700);
		
		//resizes images and text
		JTextArea text = new JTextArea("You got the treasure.\nYou win!"); 
		text.setFont(new Font("Monospaced", Font.PLAIN, 40));
		text.setBackground(Color.WHITE); 
		text.setEditable(false); 
		text.setBounds(600, 200, 600, 200); 

        //exit button
        JButton exitButton = new JButton("E x i t");
        exitButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        exitButton.addMouseListener(this); 
		
		//exit button
        exitButton.setBackground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBounds(525,750, 150, 30);
		
		try { //plays win sound
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/winsound.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
			backgroundMusic.start();
        } 
		catch (Exception e) {
            e.printStackTrace();
        }
		
        // add items to the panel
		add(text); 
        add(wingif);
        add(exitButton);
    }

		
    public void mouseClicked(MouseEvent me) {
		System.exit(0);
	}
	//other mouse events
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}

}


class Lose extends JPanel implements MouseListener{
    private JFrame frame;
    private ImageIcon background;
	private Clip backgroundMusic;

    public Lose(JFrame frame) {
		//creatse frame
        this.frame = frame;
        setLayout(null);
		setBackground(Color.BLACK);


        ImageIcon gif = new ImageIcon("images/lose.gif");

        JLabel losegif = new JLabel(gif);
        losegif.setBounds(50, 200, gif.getIconWidth(), gif.getIconHeight());
		
		//resizes and adjusts items + text
		JTextArea text = new JTextArea("You blew up a mine and \ngot eaten by a shark. \nYou lose!"); 
		text.setFont(new Font("Monospaced", Font.PLAIN, 40));
		text.setBackground(Color.BLACK); 
		text.setForeground(Color.WHITE);
		text.setEditable(false); 
		text.setBounds(600, 200, 600, 200); 

        //exit button
        JButton exitButton = new JButton("E x i t");
        exitButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        exitButton.addMouseListener(this); 
		
		//exit button
        exitButton.setBackground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBounds(525,750, 150, 30);
		
		try { //plays loss sound
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/losesound.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
			backgroundMusic.start();
        } 
		catch (Exception e) {
            e.printStackTrace();
        }
		
        // add components to the panel
		add(text); 
        add(losegif);
        add(exitButton);
    }

		
    public void mouseClicked(MouseEvent me) {
		System.exit(0);
	}
	//other mouse events
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}

}

class SamLost extends JPanel implements MouseListener{
    private JFrame frame;
    private ImageIcon background;
	private Clip backgroundMusic;

    public SamLost(JFrame frame) {
		//creates frame and initializes variables
        this.frame = frame;
        setLayout(null);
		setBackground(Color.BLACK);
		
        ImageIcon gif = new ImageIcon("images/samlost.gif");

        JLabel samgif = new JLabel(gif);
        samgif.setBounds(200, 100, gif.getIconWidth(), gif.getIconHeight());
		
		//resizes screen and adjusts stuff
		JTextArea text = new JTextArea("You blew up a mine and \ngot catapulted into a \nhungry Samuel G's mouth. \nYou lose!"); 
		text.setFont(new Font("Monospaced", Font.PLAIN, 40));
		text.setBackground(Color.BLACK); 
		text.setForeground(Color.WHITE);
		text.setEditable(false); 
		text.setBounds(600, 200, 600, 200); 

        //exit button
        JButton exitButton = new JButton("E x i t");
        exitButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        exitButton.addMouseListener(this); 
		
		//exit button
        exitButton.setBackground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBounds(525,750, 150, 30);
		
		try { //plays loss sound
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/losesound.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
			backgroundMusic.start();
        } 
		catch (Exception e) {
            e.printStackTrace();
        }
		
        // add components to the panel
		add(text); 
        add(samgif);
        add(exitButton);
    }

		
    public void mouseClicked(MouseEvent me) {
		System.exit(0);
    }
	//other mouse events
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}

}

class SamWon extends JPanel implements MouseListener{
	//creates frame
    private JFrame frame;
    private ImageIcon background;
	private Clip backgroundMusic;

    public SamWon(JFrame frame) {
		//creates frame and initializes variables
        this.frame = frame;
        setLayout(null);
		setBackground(Color.WHITE);

        ImageIcon gif = new ImageIcon("images/samwon.gif");

        JLabel samgif = new JLabel(gif);
        samgif.setBounds(50, 20, gif.getIconWidth(), 700);
		
		//resizes screen and adjusts stuff
		JTextArea text = new JTextArea("You got the treasure. \nSamuel Du (you) is\nso happy now!\nYou win!"); 
		text.setFont(new Font("Monospaced", Font.PLAIN, 40));
		text.setBackground(Color.WHITE); 
		text.setEditable(false); 
		text.setBounds(600, 200, 600, 200); 

        //exit button
        JButton exitButton = new JButton("E x i t");
        exitButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        exitButton.addMouseListener(this); 
		
		//exit button
        exitButton.setBackground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBounds(525,750, 150, 30);
		
		try { //plays win sound
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/winsound.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
			backgroundMusic.start();
        } 
		catch (Exception e) {
            e.printStackTrace();
        }
		
        // add components to the panel
		add(text); 
        add(samgif);
        add(exitButton);
    }

		
    public void mouseClicked(MouseEvent me) {
		System.exit(0);
	}
	//other mouse events
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}

}
