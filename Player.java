import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import java.awt.Color;

public class Player {
	private ImageIcon character;
	private int difficulty;
	private JFrame frame;
	
	public Player (int difficulty, JFrame frame) {
		this.difficulty = difficulty;
		this.frame = frame;
		if (difficulty == 1)
			character = new ImageIcon("images/fishy1.png");
		else if (difficulty == 2)
			character = new ImageIcon("images/fishy2.png");
		else if (difficulty == 3)
			character = new ImageIcon("images/fishy3.png");
		else
			character = new ImageIcon("images/fishysam.png"); //uploads image based on what difficulty
	}
	
	public ImageIcon getImage() {
		return character; //getter method
	}
	
	public void playerWin() {
		Win winScreen = new Win(frame);
		frame.setContentPane(winScreen); //goes to next screen
		frame.revalidate();
	}
	
	public void samWin() {
		SamWon samWinScreen = new SamWon(frame);
		frame.setContentPane(samWinScreen); //goes to next screen
		frame.revalidate();
	}
	
	public void playerLose() {
		Lose loseScreen = new Lose(frame);
		frame.setContentPane(loseScreen); //goes to next screen
		frame.revalidate();
	}
	
	public void samLose() {
		SamLost samLoseScreen = new SamLost(frame); 
		frame.setContentPane(samLoseScreen); //goes to next screen
		frame.revalidate();
	}
}
