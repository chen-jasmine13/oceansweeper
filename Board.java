import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import java.awt.Color;
import javax.sound.sampled.*;

public class Board extends JPanel implements MouseListener, KeyListener{
	//creates and initializes variables
    private int[][] board;
	private boolean[][] visited, flagged, safeTile;
	private int flagCounter, rows, columns, size, safeMax, trueFlag, characterX, characterY, treasureY, level;
	private DefaultButton[][] text;
	private boolean leftPressed, rightPressed, gameStart;
	private Player gamePlayer;
	private ImageIcon CLOSED_TREASURE = new ImageIcon("images/closed_treasure.png");
	private ImageIcon OPEN_TREASURE = new ImageIcon("images/open_treasure.png");
	private Clip backgroundMusic;

	// constructor 
    public Board(int level, JFrame frame){  
		this.level = level;
		try { //changes to game music
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/gamesound.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } 
		catch (Exception e) {
            e.printStackTrace();
        }
		if (level == 1){ //creates board based on level given
			size = 25; //changes font size based on button size
			gamePlayer = new Player(1, frame);
			board = new int[8][10]; //width and height of board
			text = new DefaultButton[8][10];
			visited = new boolean[8][10];
			flagged = new boolean[8][10];
			safeTile = new boolean[8][10];
			safeMax = (int) (Math.random()*3)+1; //random amount of safe squares
			treasureY = (int) (Math.random()*8); //random y coordinate for treasure to spawn
			flagCounter = 15;
			rows = 8; 
			columns = 10;
			this.setLayout(new GridLayout(8,10));
		} 
		else if (level == 2){
			size = 19;
			gamePlayer = new Player(2, frame);
			board = new int[14][18];
			text = new DefaultButton[14][18];
			visited = new boolean[14][18];
			flagged = new boolean[14][18];
			safeTile = new boolean[14][18];
			safeMax = (int) (Math.random()*5)+5;
			treasureY = (int) (Math.random()*14);
			flagCounter = 40;
			rows = 14; 
			columns = 18;
			this.setLayout(new GridLayout(14,18));
		}
		else if (level == 3){
			size = 16;
			gamePlayer = new Player(3, frame);
			board = new int[20][25];
			text = new DefaultButton[20][25];
			visited = new boolean[20][25];
			flagged = new boolean[20][25];
			safeTile = new boolean[20][25];
			safeMax = (int) (Math.random()*7)+7;
			treasureY = (int) (Math.random()*20);
			flagCounter = 99;
			rows = 20; 
			columns = 25;
			this.setLayout(new GridLayout(20,25));
		}
		else {
			size = 16;
			gamePlayer = new Player(4, frame);
			board = new int[20][25];
			text = new DefaultButton[20][25];
			visited = new boolean[20][25];
			flagged = new boolean[20][25];
			safeTile = new boolean[20][25];
			safeMax = (int) (Math.random()*7)+7;
			treasureY = (int) (Math.random()*20);
			flagCounter = 99;
			rows = 20; 
			columns = 25;
			this.setLayout(new GridLayout(20,25));
		}
    }
	
	public void keyPressed(KeyEvent e) { //moves fish based on the key pressed
		if (e.getKeyCode() == KeyEvent.VK_W && characterY-1 >= 0 && visited[characterY-1][characterX] ) {
			printNumber(characterY, characterX); 
			text[characterY][characterX].setIcon(null); //takes fish away from original tile
			text[characterY-1][characterX].setText("");
			text[characterY-1][characterX].setIcon(gamePlayer.getImage()); //adds to new tile
			characterY--; //updates the coordinates of the character
		}
		else if (e.getKeyCode() == KeyEvent.VK_A && characterX-1 >= 0 && visited[characterY][characterX-1]) {
			printNumber(characterY, characterX);
			text[characterY][characterX].setIcon(null);
			text[characterY][characterX-1].setText("");
			text[characterY][characterX-1].setIcon(gamePlayer.getImage());
			characterX--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_S && characterY+1 < rows && visited[characterY+1][characterX]) {
			printNumber(characterY, characterX);
			text[characterY][characterX].setIcon(null);
			text[characterY+1][characterX].setText("");
			text[characterY+1][characterX].setIcon(gamePlayer.getImage());
			characterY++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_D && characterX+1 < columns && visited[characterY][characterX+1]) {
			printNumber(characterY, characterX);
			text[characterY][characterX].setIcon(null);
			text[characterY][characterX+1].setText("");
			text[characterY][characterX+1].setIcon(gamePlayer.getImage());
			characterX++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_Q && characterX-1 >= 0 && characterY-1 >= 0 && visited[characterY-1][characterX-1]) {
			printNumber(characterY, characterX);
			text[characterY][characterX].setIcon(null);
			text[characterY-1][characterX-1].setText("");
			text[characterY-1][characterX-1].setIcon(gamePlayer.getImage());
			characterY--;
			characterX--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_E && characterX+1 < columns && characterY-1 >= 0 && visited[characterY-1][characterX+1]) {
			printNumber(characterY, characterX);
			text[characterY][characterX].setIcon(null);
			text[characterY-1][characterX+1].setText("");
			text[characterY-1][characterX+1].setIcon(gamePlayer.getImage());
			characterY--;
			characterX++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_Z && characterX-1 >= 0 && characterY+1 < rows && visited[characterY+1][characterX-1]) {
			printNumber(characterY, characterX);
			text[characterY][characterX].setIcon(null);
			text[characterY+1][characterX-1].setText("");
			text[characterY+1][characterX-1].setIcon(gamePlayer.getImage());;
			characterY++;
			characterX--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_X && characterX+1 < columns && characterY+1 < rows && visited[characterY+1][characterX+1]) {
			printNumber(characterY, characterX);
			text[characterY][characterX].setIcon(null);
			text[characterY+1][characterX+1].setText("");
			text[characterY+1][characterX+1].setIcon(gamePlayer.getImage());
			characterY++;
			characterX++;
		}
		//checks if the tile it has moved to is the tile with treasure
		if (characterY == treasureY && characterX == columns-1 && level <= 3) {
			backgroundMusic.stop();
			text[characterY][characterX].setIcon(null);
			text[characterY][characterX].setIcon(OPEN_TREASURE);
			gamePlayer.playerWin(); //if the character reaches the treasure tile, character wins
			return;
		}
		else if (characterY == treasureY && characterX == columns-1) {
			backgroundMusic.stop();
			text[characterY][characterX].setIcon(null);
			text[characterY][characterX].setIcon(OPEN_TREASURE);
			gamePlayer.samWin(); //if the character reaches the treasure tile, character wins
			return;
		}
	}
	
	//rewrites other keyevents
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	
	public void mousePressed(MouseEvent me) { 
		for (int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				if (!gameStart) { //start the game on first click
					if (c == 0 && text[r][c] == me.getSource()) {
						gameStart = true;
						safeTile[r][c] = true;
						safeMax--;
						makeSafe(r, c, rows, columns);
						this.addFlags(safeTile);
						this.addNumbers();
						this.removeArrow(); //removes arrows from gamestart
						clearSquare(r, c, rows, columns); //clears the square that was clicked
						text[r][c].setIcon(gamePlayer.getImage());
						text[treasureY][columns-1].setIcon(CLOSED_TREASURE); //treasure spawns
						characterX = c;
						characterY = r;
					}
				}
				else {
					if (text[r][c] == me.getSource()) { //once game has started, check for right/left click
						if (me.getButton() == MouseEvent.BUTTON1)
							leftPressed = true; //this is so when the player clicks with both left click and right click at the same time, it can register to check action as something different
						else if (me.getButton() == MouseEvent.BUTTON3)
							rightPressed = true;
						checkAction(r, c); 
					}
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent me) { //makes it so the player must click both together in rapid sucession in order to chord
		if (me.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		else if (me.getButton() == MouseEvent.BUTTON3)
			rightPressed = false;
	}
	
	public void mouseEntered(MouseEvent me) { //changes color to highlight when mouse hovers over
		for (int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				if (text[r][c] == me.getSource()) {
					if (visited[r][c] && !flagged[r][c]) 
						text[r][c].setBackground(ColorConstants.HIGHLIGHT_BROWN);
					else if (!flagged[r][c])
						text[r][c].setBackground(ColorConstants.HIGHLIGHT_BLUE);
				}
			}
		}
	}
	
	public void mouseExited(MouseEvent me) {
		for (int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				if (text[r][c] == me.getSource()) {
					if (visited[r][c] && !flagged[r][c]) { //changes color back to normal, keeping the checkered pattern
						if (r % 2 == 0 && c % 2 == 0)
							text[r][c].setBackground(ColorConstants.LIGHT_BROWN);
						else if (r % 2 != 0 && c % 2 != 0) 
							text[r][c].setBackground(ColorConstants.LIGHT_BROWN);
						else 
							text[r][c].setBackground(ColorConstants.DARK_BROWN);
					}
					else if (!flagged[r][c]){
						if (r % 2 == 0 && c % 2 == 0)
							text[r][c].setBackground(ColorConstants.LIGHT_BLUE);
						else if (r % 2 != 0 && c % 2 != 0) 
							text[r][c].setBackground(ColorConstants.LIGHT_BLUE);
						else 
							text[r][c].setBackground(ColorConstants.DARK_BLUE);
					}
						
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent me) {}
	
	public void checkAction(int r, int c) { //checks what the action was so it can respond
		if (leftPressed && rightPressed && checkFlags(r, c, rows, columns)) {
			chordSquare(r, c, rows, columns);
		}
		else if (rightPressed) {
			flagSquare(r, c);
		}
		else {
			clearSquare(r, c, rows, columns);
		}
	}
	
	public void makeSafe(int row, int column, int maxR, int maxC) { //creates a random island of safe squares each time
		if (row < 0 || column < 0 || row >= maxR || column >= maxC || safeMax <= 0)
			return; //ends if safeMax is reached

		int randomSafe = (int) (Math.random() * 4) + 1;

		if (randomSafe == 1 || randomSafe == 2 || randomSafe == 3) { //creates 75% for the square to become safe
			safeTile[row][column] = true;
			safeMax--;
		}

		makeSafe(row - 1, column, maxR, maxC); //calls itself to continue to process, until safeMax is reached
		makeSafe(row, column + 1, maxR, maxC);
		makeSafe(row, column - 1, maxR, maxC);
		makeSafe(row + 1, column, maxR, maxC);
		makeSafe(row + 1, column + 1, maxR, maxC);
		makeSafe(row + 1, column - 1, maxR, maxC);
		makeSafe(row - 1, column - 1, maxR, maxC);
		makeSafe(row - 1, column + 1, maxR, maxC);
	}
	
	public void addFlags(boolean[][] safe) {	
		//places flags 
		while (flagCounter > 0) {
			for (int r = 0; r < rows; r++){
				for(int c = 0; c < columns; c++){
					if (canFlag(r, c, rows, columns)) { //checks if box can be flagged before placing (cannot be adjacent to safe square)
						trueFlag = (int) (Math.random()*9)+1; // 1 in 9 chance to be a mine
						if (trueFlag == 1 && board[r][c]!= -1) {
							board[r][c] = -1; //-1 represents bomb
							flagCounter--;
						}
						if (flagCounter == 0) 
							break; //once all flags are placed, break
					}
				}
				if (flagCounter == 0)
						break;
			}
		}
	}
	
	public boolean canFlag(int row, int column, int maxR, int maxC) { //checks if any adjacent tiles are safe tiles, if they are the mine cannot be there
		if (safeTile[row][column])
			return false;
		else if (row-1 >= 0 && safeTile[row-1][column])
			return false;
		else if (column+1 < maxC && safeTile[row][column+1])
			return false;
		else if (column-1 >= 0 && safeTile[row][column-1])
			return false;
		else if (row+1 < maxR && safeTile[row+1][column])
			return false;
		else if (row-1 >=0 && column-1 >= 0 && safeTile[row-1][column-1])
			return false;
		else if (row+1 < maxR && column+1 < maxC && safeTile[row+1][column+1])
			return false;
		else if (row-1 >= 0 && column+1 < maxC && safeTile[row-1][column+1])
			return false;
		else if (row+1 < maxR && column-1 >= 0 && safeTile[row+1][column-1])
			return false;
		else if (row == treasureY && column == columns-1) //the treasure chest square cannot be a mine
			return false;
		else
			return true;
	}
	
	public void flagSquare(int row, int column) {
		if (!visited[row][column]) {
			if (!flagged[row][column]) {
				text[row][column].setBackground(ColorConstants.SOFT_RED); //colors flagged squares red
				flagged[row][column] = true; //checks off as flagged
			}
			else {
				if (row % 2 == 0 && column % 2 == 0)
					text[row][column].setBackground(ColorConstants.LIGHT_BLUE);
				else if (row % 2 != 0 && column % 2 != 0) 
					text[row][column].setBackground(ColorConstants.LIGHT_BLUE);
				else 
					text[row][column].setBackground(ColorConstants.DARK_BLUE);
				flagged[row][column] = false; //unchecked as flag
			}
		}
	}
	
	public boolean checkFlags(int row, int column, int maxR, int maxC) { //checks if enough flags have been placed around a regular tile, so the player can chord square
		int flags = board[row][column], flagsPlaced = 0;
		if (row-1 >= 0 && !visited[row-1][column] && flagged[row-1][column])
			flagsPlaced++; //accumulates flagsplaced
		if (column+1 < maxC && !visited[row][column+1] && flagged[row][column+1])
			flagsPlaced++;
		if (column-1 >= 0 && !visited[row][column-1] && flagged[row][column-1])
			flagsPlaced++;
		if (row+1 < maxR && !visited[row+1][column] && flagged[row+1][column])
			flagsPlaced++;
		if (row-1 >=0 && column-1 >= 0 && !visited[row-1][column-1] && flagged[row-1][column-1])
			flagsPlaced++;
		if (row+1 < maxR && column+1 < maxC && !visited[row+1][column+1] && flagged[row+1][column+1])
			flagsPlaced++;
		if (row-1 >= 0 && column+1 < maxC && !visited[row-1][column+1] && flagged[row-1][column+1])
			flagsPlaced++;
		if (row+1 < maxR && column-1 >= 0 && !visited[row+1][column-1] && flagged[row+1][column-1])
			flagsPlaced++;
		
		return flags == flagsPlaced; //returns true if amount of flags placed is equal to the amount that it actually has
	}
	
	public void clearOne(int row, int column) {
		
		if (board[row][column] == -1) {
			if (level <= 3) {
				backgroundMusic.stop(); //stops music if -1 is revealed and game is lost
				gamePlayer.playerLose();		
			}
			else {
				backgroundMusic.stop(); //stops music if -1 is revealed and game is lost
				gamePlayer.samLose();
			}
			return;
		}
		
		if (!flagged[row][column]) { 
			visited[row][column] = true; 
			if (board[row][column] > 0 || board[row][column] < 0)
				printNumber(row, column);
			if (row % 2 == 0 && column % 2 == 0)
				text[row][column].setBackground(ColorConstants.LIGHT_BROWN); //changes background color
			else if (row % 2 != 0 && column % 2 != 0) 
				text[row][column].setBackground(ColorConstants.LIGHT_BROWN);
			else 
				text[row][column].setBackground(ColorConstants.DARK_BROWN);
		}
	}
	
	public void chordSquare(int row, int column, int maxR, int maxC) { //clears each square around the square being chorded 
		if (row - 1 >= 0 && !visited[row-1][column] && !flagged[row-1][column]) //must be in bounds, not visited, and not flagged
			if (board[row-1][column] == 0)
				clearSquare(row-1, column, maxR, maxC); //clears as 0 if it is 0 (this will allow it to continue to clear)
			else
				clearOne(row-1, column); //only clear one if its not a 0
		if (column + 1 < maxC && !visited[row][column+1] && !flagged[row][column+1])
			if (board[row][column+1] == 0)
				clearSquare(row, column+1, maxR, maxC);
			else
				clearOne(row, column+1);
		if (column - 1 >= 0 && !visited[row][column-1] && !flagged[row][column-1])
			if (board[row][column-1] == 0)
				clearSquare(row, column-1, maxR, maxC);
			else
				clearOne(row, column-1);
		if (row + 1 < maxR && !visited[row+1][column] && !flagged[row+1][column])
			if (board[row+1][column] == 0)
				clearSquare(row+1, column, maxR, maxC);
			else
				clearOne(row+1, column);
		if (row - 1 >= 0 && column - 1 >= 0 && !visited[row-1][column-1] && !flagged[row-1][column-1])
			if (board[row-1][column-1] == 0)
				clearSquare(row-1, column-1, maxR, maxC);
			else
				clearOne(row-1, column-1);
		if (row + 1 < maxR && column + 1 < maxC && !visited[row+1][column+1] && !flagged[row+1][column+1])
			if (board[row+1][column+1] == 0)
				clearSquare(row+1, column+1, maxR, maxC);
			else
				clearOne(row+1, column+1);
		if (row - 1 >= 0 && column + 1 < maxC && !visited[row-1][column+1] && !flagged[row-1][column+1])
			if (board[row-1][column+1] == 0)
				clearSquare(row-1, column+1, maxR, maxC);
			else
				clearOne(row-1, column+1);
		if (row + 1 < maxR && column - 1 >= 0 && !visited[row+1][column-1] && !flagged[row+1][column-1])
			if (board[row+1][column-1] == 0)
				clearSquare(row+1, column-1, maxR, maxC);
			else
				clearOne(row+1, column-1);
	}

	
	
	public void clearSquare(int row, int column, int maxR, int maxC) { //if a square that is cleared is 0, it sets off a chain reactions of all other 0s adjacent to the original being revealed as well
		
		if (row < 0 || column < 0 || row >= maxR || column >= maxC || visited[row][column] || flagged[row][column])
			return;

		visited[row][column] = true;
		
		if (board[row][column] == -1) {
			if (level <= 3) {
				backgroundMusic.stop(); //stops music if -1 is revealed and game is lost
				gamePlayer.playerLose();
			}
			else {
				backgroundMusic.stop(); //stops music if -1 is revealed and game is lost
				gamePlayer.samLose();
			}
			return;
		}
	
		if (board[row][column] > 0 || board[row][column] < 0) //even if its 0, it goes back to the brown buttons
			printNumber(row, column);
		if (row % 2 == 0 && column % 2 == 0) //makess sure it can go back to treasure
			text[row][column].setBackground(ColorConstants.LIGHT_BROWN);
		else if (row % 2 != 0 && column % 2 != 0) 
			text[row][column].setBackground(ColorConstants.LIGHT_BROWN);
		else 
			text[row][column].setBackground(ColorConstants.DARK_BROWN);

		if (board[row][column] == 0) { //if the current tile is a 0, then it keeps on going until there are no 0s left
			clearSquare(row - 1, column, maxR, maxC);
			clearSquare(row, column + 1, maxR, maxC);
			clearSquare(row, column - 1, maxR, maxC);
			clearSquare(row + 1, column, maxR, maxC);
			clearSquare(row + 1, column + 1, maxR, maxC);
			clearSquare(row + 1, column - 1, maxR, maxC);
			clearSquare(row - 1, column - 1, maxR, maxC);
			clearSquare(row - 1, column + 1, maxR, maxC);
		}
	}
	
	public void printNumber(int row, int column) {
		int num = board[row][column];
		if (num == 0 || (row == treasureY && column == columns-1)) { //if text is 0 or if its on the treasure chest, remove the text
			text[row][column].setText("");
			return;
		}
		for (int i = 0; i < 9; i++){
			if (num == i+1) { //prints the number based on the color assigned to the array (+1 because array index starts at 0)
				text[row][column].setForeground(ColorConstants.COLOR_NUMBERS[i]);
				text[row][column].setText(" " + board[row][column]);
				break;
			}
		}
	}
	
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(new Font("SansSerif", Font.BOLD, 18)); //changes graphics
    }
	
	public void addNumbers() {
		//checking flags for inner squares 
		for(int r = 1; r < rows-1; r++){
			for(int c = 1; c < columns-1; c++){
				if (board[r][c] == 0) {
					if (board[r-1][c] == -1)
						board[r][c]++;
					if (board[r+1][c] == -1)
						board[r][c]++;
					if (board[r][c+1] == -1)
						board[r][c]++;	
					if (board[r][c-1] == -1)
						board[r][c]++;
					if (board[r+1][c+1] == -1)
						board[r][c]++;
					if (board[r-1][c-1] == -1)
						board[r][c]++;	
					if (board[r-1][c+1] == -1)
						board[r][c]++;	
					if (board[r+1][c-1] == -1)
						board[r][c]++;
				}
			}
		}
		
		// accounts for top side
		for(int c = 1; c < columns-1; c++) {
			int row = 0;
			if (board[row][c] == 0) {
				if(board[row][c-1] == -1)
					board[row][c]++;
				if(board[row][c+1] == -1)
					board[row][c]++;
				if(board[row+1][c] == -1)
					board[row][c]++;
				if (board[row+1][c+1] == -1)
					board[row][c]++;
				if (board[row+1][c-1] == -1)
					board[row][c]++;
			}
		}
		
		//accounts for bottom side
		for(int c = 1; c < columns-1; c++) {
			int row = rows-1; 
			if (board[row][c] == 0) {
				if(board[row][c-1] == -1)
					board[row][c]++;
				if(board[row][c+1] == -1)
					board[row][c]++;
				if(board[row-1][c] == -1)
					board[row][c]++;
				if (board[row-1][c+1] == -1)
					board[row][c]++;
				if (board[row-1][c-1] == -1)
					board[row][c]++;
			}
		}
		
		//accounts for right side
		for(int r = 1; r < rows-1; r++) {
			int column = 0; 
			if (board[r][column] == 0) {
				if(board[r-1][column] == -1)
					board[r][column]++;
				if(board[r+1][column] == -1)
					board[r][column]++;
				if(board[r][column+1] == -1)
					board[r][column]++;
				if (board[r-1][column+1] == -1)
					board[r][column]++;
				if (board[r+1][column+1] == -1)
					board[r][column]++;
			}
		}
		
		//accounts for left side
		for(int r = 1; r < rows-1; r++) {
			int column = columns-1; 
			if (board[r][column] == 0) {
				if(board[r-1][column] == -1)
					board[r][column]++;
				if(board[r+1][column] == -1)
					board[r][column]++;
				if(board[r][column-1] == -1)
					board[r][column]++;
				if (board[r-1][column-1] == -1)
					board[r][column]++;
				if (board[r+1][column-1] == -1)
					board[r][column]++;
			}
		}
		
		//upper right corner
		if (board[0][0] == 0) {
			if (board[1][0] == -1)
				board[0][0] ++; 
			if(board [0][1] == -1)
				board[0][0]++; 
			if(board[1][1]== -1)
				board[0][0]++; 
		}
		 
		//upper left corner
		if (board[0][columns-1] == 0) {
			if (board[0][columns-2] == -1)
				board[0][columns-1] ++; 
			if(board[1][columns-1] ==-1)
				board[0][columns-1] ++;
			if (board[1][columns-2] == -1)
				board[0][columns-1] ++;
		}
		
		//bottom right corner
		if (board[rows-1][0] == 0) {
			if(board[rows-2][0] == -1)
				board[rows-1][0] ++; 
			if(board[rows-2][1] == -1) 
				board[rows-1][0] ++; 
			if(board[rows-1][1] == -1) 
				board[rows-1][0] ++; 
		}
		
		//bottom left corner 
		if (board[rows-1][columns-1] == 0) {
			if(board[rows-2][columns-2] == -1) 
				board[rows-1][columns-1] ++; 
			if(board[rows-2][columns-1] == -1)
				board[rows-1][columns-1] ++; 
			if(board[rows-1][columns-2] == -1) 
				board[rows-1][columns-1] ++; 
		}
		
	}
	
	public void initiateBoard() { 
		for (int r = 0; r  < rows; r++){
            for(int c = 0; c < columns; c++){
				text[r][c] = new DefaultButton(r, c); //creates blank buttons
				text[r][c].resizeFont(size); //sets font based on given size in board constructor
				this.add(text[r][c]);
				text[r][c].addMouseListener(this); //makes it interactive
				text[r][c].addKeyListener(this);
            }
        }
	}
	
	public void removeArrow() { 
		for (int r = 0; r  < rows; r++){
            for(int c = 0; c < columns; c++){
				text[r][c].setText(""); //resets the text back to blank
            }
        }
	}

} 
