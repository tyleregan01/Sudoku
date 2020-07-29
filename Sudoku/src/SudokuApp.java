import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
/**
 * An implementation of the Sudoku game.
 * References used listed at the bottom.
 * 
 * @author Tyler Egan
 */
public class SudokuApp {
	
//	private static JFrame mainFrame;
//	private static JTextArea test;
	private static JTextPane[] cells;

	public static void main(String[] args) {
		
		//Initialize components
		JFrame mainFrame = new JFrame("Sudoku");
		JMenuBar menu = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New Game");
		JPanel gameWindow = new JPanel();
		JPanel[] grid = new JPanel[9];
		cells = new JTextPane[81];
		
		//Set up Frame
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(450, 450);
		
		//Update Components
		menu.add(gameMenu);
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearCells();
			}
		});
		gameMenu.add(newGame);
		gameWindow.setLayout(new GridLayout(3,3));
		gameWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		//Initialize variables for loop
		
		CellListener cellListener = new CellListener();
		
		int cellNum = 0;
		//Cells used to hold numbers,
		for(int gridNum = 0; gridNum < 9; gridNum++) {
			grid[gridNum] = new JPanel();
			grid[gridNum].setLayout(new GridLayout(3,3));
			grid[gridNum].setBorder(BorderFactory.createLineBorder(Color.black));
			for(int gridCellNum = 0; gridCellNum < 9; gridCellNum++) {
				cellNum = (gridNum*9) + gridCellNum;
				cells[cellNum] = new JTextPane();
				setStyle(cells[cellNum]);
				cells[cellNum].setBorder(BorderFactory.createLineBorder(Color.black));
				grid[gridNum].add(cells[cellNum]);
				cells[cellNum].getDocument().addDocumentListener(cellListener);
			}
			gameWindow.add(grid[gridNum]);
		}
		
		//Add components to frame
		mainFrame.getContentPane().add(BorderLayout.NORTH, menu);
		mainFrame.getContentPane().add(BorderLayout.CENTER, gameWindow);

		mainFrame.setVisible(true);
	}

	private static void setStyle(JTextPane pane) {
		SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(attributes, "TIMES NEW ROMAN");
        StyleConstants.setFontSize(attributes, 24);
        
        StyledDocument doc = pane.getStyledDocument();
        doc.setParagraphAttributes(0, 2, attributes, false);
	}
	
	private static void clearCells() {
		for(int i = 0; i < cells.length; i++) {
			cells[i].setText("");
		}
	}
}

/* ALL REFERENCES USED
 * For documentation: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
 * For getting started: https://www.guru99.com/java-swing-gui.html
 * For quick and simple answers like setting a border: stackoverflow.com
 * For setStyle: https://coderanch.com/t/339702/java/Alignment-centering-text-JTextPane-work
 */