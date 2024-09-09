import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import java.awt.Color;
import javax.sound.sampled.*;

public class TitleScreen extends JPanel implements MouseListener {
	//initializes variables
    private JFrame frame;
	private ImageIcon background;
	private static Clip backgroundMusic;


	public TitleScreen(JFrame frame) {
		//creates frame
		this.frame = frame;
		setLayout(null);
		
		//sets background
		background = new ImageIcon("images/titlebg.png"); 

		JButton startButton = new JButton("S t a r t");
		startButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		startButton.addMouseListener(this);
		startButton.setBackground(Color.WHITE);
		startButton.setFocusPainted(false);
		startButton.setBounds(525, 650, 150, 30);
		//resizes backgrounds
		
		try { //plays music
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/titlesound.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } 
		catch (Exception e) {
            e.printStackTrace();
        }
		
		add(startButton); //adds items
	}



    public void mouseClicked(MouseEvent me) {
		showRules(); //displays rules
	}

    private void showRules() { //creates new rules frame
        RulesScreen rulesScreen = new RulesScreen(frame);
        frame.setContentPane(rulesScreen);
        frame.revalidate();
    }
	
	public static void stopMusic() {
		backgroundMusic.stop();
	}

    //other mouselistener methods
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null); //changes graphics

    }
}

class RulesScreen extends JPanel implements MouseListener {
    private JFrame frame;
	private ImageIcon background;
	
    public RulesScreen(JFrame frame) {
		//creates frame
        this.frame = frame;
        setLayout(null);
		background = new ImageIcon("images/rulebg.png");
		
		//sets background
        JButton continueButton = new JButton("C o n t i n u e");
		continueButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		continueButton.addMouseListener(this);
		continueButton.setBackground(Color.WHITE);
		continueButton.setFocusPainted(false);
		continueButton.setBounds(500, 700, 200, 30);
        add(continueButton);
		
		//adding images to the game 
		ImageIcon file1 = new ImageIcon("images/rule1.png");
		JLabel rule1 = new JLabel(file1);

		ImageIcon file2 = new ImageIcon("images/rule2.png");
		JLabel rule2 = new JLabel(file2);

		ImageIcon file3 = new ImageIcon("images/rule3.png");
		JLabel rule3 = new JLabel(file3);  

		ImageIcon file4 = new ImageIcon("images/rule4.png");
		JLabel rule4 = new JLabel(file4);  
				
		//resize rules
		rule1.setBounds(30, 160, 262, 420);
		rule2.setBounds(322, 160, 262, 420);
		rule3.setBounds(614, 160, 262, 420);
		rule4.setBounds(908, 160, 262, 420);
		
		//add in items
		add(rule1); 
		add(rule2); 
		add(rule3); 
		add(rule4); 

    }
	//mouse events to continue to the next screen 
    public void mouseClicked(MouseEvent me) {
		selectScreen(); //goes to next page
    }

    private void selectScreen() {
        SelectScreen selectScreen = new SelectScreen(frame);
        frame.setContentPane(selectScreen);
        frame.revalidate();
    }

    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me){} 
    public void mouseExited(MouseEvent me) {}
		
	//paints the background 
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null); //changes graphics

    }


}

class SelectScreen extends JPanel implements MouseListener {
    private JFrame frame;
	private ImageIcon background; 

    public SelectScreen(JFrame frame) {
		//creates frame
        this.frame = frame;
        setLayout(null);
		background = new ImageIcon("images/selectbg.png"); 
	
        JButton easyButton = new JButton("E a s y"); //adding in different levels 
        easyButton.addMouseListener(this);
		
        JButton mediumButton = new JButton("M e d i u m");
        mediumButton.addMouseListener(this);
		
        JButton hardButton = new JButton("H a r d");
        hardButton.addMouseListener(this);
		
        JButton specialButton = new JButton("S p e c i a l");
        specialButton.addMouseListener(this);

		easyButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		mediumButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		hardButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		specialButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		
		//sets focus
		easyButton.setBackground(Color.WHITE);
		easyButton.setFocusPainted(false);
		mediumButton.setBackground(Color.WHITE);
		mediumButton.setFocusPainted(false);
		hardButton.setBackground(Color.WHITE);
		hardButton.setFocusPainted(false);
		specialButton.setBackground(Color.WHITE);
		specialButton.setFocusPainted(false);
		
		//resizes and places buttons elsewhere
		easyButton.setBounds(30, 500, 262, 50);
		mediumButton.setBounds(322, 500, 262, 50);
		hardButton.setBounds(614, 500, 262, 50);
		specialButton.setBounds(908, 500, 262, 50);
		
		//adds items
		add(easyButton); 
		add(mediumButton); 
		add(hardButton); 
		add(specialButton); 
		
		

    }

	public void mouseClicked(MouseEvent me) {
		if (me.getSource().getClass() == JButton.class) {
			JButton clickedButton = (JButton) me.getSource();
			if (clickedButton.getText().equals("E a s y")) //when button pressed, create game board
				startGame(1, frame);
			else if (clickedButton.getText().equals("M e d i u m"))
				startGame(2, frame);
			else if (clickedButton.getText().equals("H a r d"))
				startGame(3, frame);
			else if (clickedButton.getText().equals("S p e c i a l"))
				startGame(4, frame);
		}
	}



    public void startGame(int difficulty, JFrame frame) {
		//starts the board when called
        Board oceansweeper = new Board(difficulty, frame);
		oceansweeper.initiateBoard(); 
        frame.setContentPane(oceansweeper);
		TitleScreen.stopMusic();
        frame.revalidate();
    }

    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
	
	//paints the background 
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null);

    }
}
