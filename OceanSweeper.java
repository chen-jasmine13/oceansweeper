import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import java.awt.Color;

public class OceanSweeper{

	public static void main(String[] args) {	
		//creates game frame
        JFrame frame = new JFrame("OceanSweeper");
		
		//starts with title screen
        TitleScreen title = new TitleScreen(frame);
        frame.add(title);
		
		//changes frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1215, 900);
        frame.setVisible(true);
		frame.setResizable(false); 
	}
}

class ColorConstants {
	//import list of custom colors for the mine numbers
	public static final Color[] COLOR_NUMBERS = {
		new Color(33, 147, 255),
		new Color(0, 173, 52),
		new Color(236, 54, 60),
		new Color(154, 47, 225),
		new Color(255, 216, 78),
		new Color(20, 218, 224),
		new Color(100, 100, 100),
		new Color(195, 195, 195)
    };
	
	//other custom colors
    public static final Color LIGHT_BLUE = new Color(133, 163, 224);
    public static final Color DARK_BLUE = new Color(113, 148, 218);
    public static final Color LIGHT_BROWN = new Color(230, 204, 179);
    public static final Color DARK_BROWN = new Color(223, 190, 159);
    public static final Color SOFT_RED = new Color(200, 38, 70);
	public static final Color HIGHLIGHT_BROWN = new Color(243, 229, 216);
	public static final Color HIGHLIGHT_BLUE = new Color(193, 208, 240);
	public static final Color START_BLUE = new Color(41, 82, 163);
}

class DefaultButton extends JButton {
	private ImageIcon character = null; //initialize icon as null
	
	public DefaultButton(int row, int column) {
		setBorderPainted(false); //removes border
		
		//makes checkered pattern with light and dark blue squares
		if (row % 2 == 0 && column % 2 == 0)
			setBackground(ColorConstants.LIGHT_BLUE);
		else if (row % 2 != 0 && column % 2 != 0) 
			setBackground(ColorConstants.LIGHT_BLUE);
		else 
			setBackground(ColorConstants.DARK_BLUE);
		
		if (column == 0) {
			setForeground(ColorConstants.START_BLUE);
			setText(">"); //adds arrows on the starting column
		}
	}

	public void resizeFont(int size) {
		setFont(new Font("SansSerif", Font.BOLD, size)); //changes font based on given size
	}
}
