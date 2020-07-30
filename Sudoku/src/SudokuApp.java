import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An implementation of the Sudoku game.
 * References used listed at the bottom.
 * 
 * @author Tyler Egan
 */
public class SudokuApp {
	
	/* Globals */
	private static CellField[][] cells;
	
	//Holds color and editable status for cells
	private static final CellStyle normalCellStyle = new CellStyle(true, new Color(240, 240, 240), new Color(10, 10, 10));
	private static final CellStyle badCellStyle = new CellStyle(true, new Color(255, 0, 0), new Color(10, 10, 10));
	private static final CellStyle emptyCellStyle = new CellStyle(true, new Color(225, 225, 0), new Color(10, 10, 10));
	private static final CellStyle startCellStyle = new CellStyle(false, new Color(200, 200, 200), new Color(10, 10, 10));
	
	private static Settings sudokuSettings;

	/**
	 * Main method that runs the program.
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {
		
		/* Initialize custom objects */
		sudokuSettings = new Settings();
		
		/* Initialize components */
		JFrame mainFrame = new JFrame("Sudoku");
		JMenuBar menu = new JMenuBar();
		// Main Menus.
		JMenu gameMenu = new JMenu("Game");
		JMenu optionsMenu = new JMenu("Options");
		//Game menu items.
		JMenuItem newGame = new JMenuItem("New Game");
		//Options menu items.
		JCheckBoxMenuItem markDuplicates = new JCheckBoxMenuItem("Mark Duplicates");
		//Other components.
		JPanel gameWindow = new JPanel();
		JPanel[] grid = new JPanel[9];
		cells = new CellField[9][9];
		
		/* Update Components */
		//Set up Frame.
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(450, 450);
		
		//Add items to menus.
		gameMenu.add(newGame);
		optionsMenu.add(markDuplicates);
		//Add menus to toolbar.
		menu.add(gameMenu);
		menu.add(optionsMenu);
		//add listeners to menu items.
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearCells();
			}
		});
		
		//Set up game window
		gameWindow.setLayout(new GridLayout(3,3));
		gameWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Create and format boxes and cells and add to game window.
		for(int box = 0; box < 9; box++) {
			grid[box] = new JPanel();
			grid[box].setLayout(new GridLayout(3,3));
			grid[box].setBorder(BorderFactory.createLineBorder(Color.black));
			for(int boxCellNum = 0; boxCellNum < 9; boxCellNum++) {
				cells[box][boxCellNum] = new CellField(box, boxCellNum);
				setPermStyle(cells[box][boxCellNum]); //Style settings that will never change.
				setTempStyle(cells[box][boxCellNum], emptyCellStyle); //Style settings that can change.
				grid[box].add(cells[box][boxCellNum]);
				addCellListener(cells[box][boxCellNum]);
			}
			gameWindow.add(grid[box]);
		}
		
		//Add components to frame
		mainFrame.getContentPane().add(BorderLayout.NORTH, menu);
		mainFrame.getContentPane().add(BorderLayout.CENTER, gameWindow);
		
		mainFrame.setVisible(true);
		
	}
	

	/**
	 * Adds a listener object to provided cell field.
	 * Majority of the code for responding to user inputs, aside from menu interactions.
	 * 
	 * @param cell The CellField to add a listener to.
	 */
	private static void addCellListener(CellField cell) {
		cell.getDocument().addDocumentListener(new DocumentListener() {

			/**
			 * Ensures a single number is the only string allowed in CellField.
			 */
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				int length = cell.getText().length();
				if (length == 1) {
					setTempStyle(cell, normalCellStyle);
					if (!("123456789".contains(cell.getText()))) {
						removeText(cell, 0, 1);
					} else if(sudokuSettings.getMarkDuplicates()) {
						checkCells();
					}
				} else if (length > 1) { // Force max length of 1
					removeText(cell, 1, cell.getText().length());
				} else {
					// TODO
				}
			}

			/**
			 * Update style of empty cells.
			 */
			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				setTempStyle((JTextField) cell, emptyCellStyle);
			}

			/**
			 * I don't think this can be called with JTextFields. Print in case it happens.
			 */
			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("CHANGEDUPDATE from cellListener");
				
			}
			
			/**
			 * Removes text to force the cell to stay with 1 number and no other text.
			 * Also updates cell style.
			 * 
			 * @param cell The cell to remove text from.
			 * @param start The first character to keep.
			 * @param end The character to stop at.
			 */
			private void removeText(JTextField cell, int start, int end) {
				//Use runnable to avoid illegal state exceptions.
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						//If there is only 1 value, set to empty string and empty style.
						if(cell.getText().length() == 1) {
							cell.setText("");
							setTempStyle((JTextField) cell, emptyCellStyle);
						}
						else {
							cell.setText(cell.getText().substring(start, end));
							setTempStyle((JTextField) cell, normalCellStyle);
						}
					}
				});
			}
		});
		
	}
	
	private static void checkCells() {
		
	}
	
	/**
	 * Reset all cells to empty for a new game.
	 */
	private static void clearCells() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				cells[i][j].setText("");
				setTempStyle(cells[i][j], emptyCellStyle);
			}
		}
	}
	
	/**
	 * Sets styles that will never change, such as font.
	 * 
	 * @param cell The field to style.
	 */
	private static void setPermStyle(JTextField cell) {
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cell.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	/**
	 * Sets styles that may change, such as color.
	 * 
	 * @param cell The field to style.
	 * @param styles The styles to apply.
	 */
	private static void setTempStyle(JTextField cell, CellStyle styles) {
        cell.setEditable(styles.editable);
        cell.setBackground(styles.bgColor);
        cell.setForeground(styles.fgColor);
	}
}

/* ALL REFERENCES USED
 * For documentation: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
 * For getting started: https://www.guru99.com/java-swing-gui.html
 * For quick and simple answers like setting a border: stackoverflow.com
 */