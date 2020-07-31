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
		JMenu newGame = new JMenu("New Game");
		JMenuItem beginnerGame = new JMenuItem("Beginner Game");
		JMenuItem intermediateGame = new JMenuItem("Intermediate Game");
		JMenuItem expertGame = new JMenuItem("Expert Game");
		JMenuItem blankGame = new JMenuItem("Blank Game");
		//Options menu items.
		JCheckBoxMenuItem markDuplicates = new JCheckBoxMenuItem("Mark Duplicates");
		JCheckBoxMenuItem markEmpty = new JCheckBoxMenuItem("Mark Empty Cells");
		//Other components.
		JPanel gameWindow = new JPanel();
		JPanel[] box = new JPanel[9];
		cells = new CellField[9][9];
		
		/* Update Components */
		//Frame
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(450, 450);
		//Game menu.
		beginnerGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearCells("beginner");
			}
			
		});
		blankGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearCells("blank");
			}
			
		});
		//Options menu.
		markDuplicates.setSelected(true);
		markDuplicates.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sudokuSettings.updateMarkDuplicates();
				updateCells();
			}
			
		});
		markEmpty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sudokuSettings.updateMarkEmpty();
				updateCells();
			}
			
		});
		
		//Add items to menus.
		newGame.add(beginnerGame);
		newGame.add(intermediateGame);
		newGame.add(expertGame);
		newGame.add(blankGame);
		gameMenu.add(newGame);
		optionsMenu.add(markDuplicates);
		optionsMenu.add(markEmpty);
		//Add menus to toolbar.
		menu.add(gameMenu);
		menu.add(optionsMenu);
		
		//Set up game window
		gameWindow.setLayout(new GridLayout(3,3));
		gameWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Create and format boxes and cells and add to game window.
		for(int row = 0; row < 9; row++) {
			for(int column = 0; column < 9; column++) {
				cells[row][column] = new CellField(row, column);
				setPermStyle(cells[row][column]); //Style settings that will never change.
				if(sudokuSettings.getMarkEmpty()) {
					setTempStyle(cells[row][column], emptyCellStyle); //Style settings that can change.
				} else {
					setTempStyle(cells[row][column], normalCellStyle); //Style settings that can change.
				}
				addCellListener(cells[row][column]);
			}
		}
		
		for(int boxNum = 0; boxNum < 9; boxNum++) {
			box[boxNum] = new JPanel();
			box[boxNum].setLayout(new GridLayout(3,3));
			box[boxNum].setBorder(BorderFactory.createLineBorder(Color.black));
			int start = boxNum/3 * 3; //Results in 0, 3, and 6
			int end = start + 3;
			int column = (boxNum%3)*3; //Results in 0, 3, and 6 at different times.
			for(int row = start; row < end; row++) {
				box[boxNum].add(cells[row][column]);
				box[boxNum].add(cells[row][column+1]);
				box[boxNum].add(cells[row][column+2]);
			}
			gameWindow.add(box[boxNum]);
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
				if(sudokuSettings.getMarkEmpty()) {
					setTempStyle((JTextField) cell, emptyCellStyle);
				} else {
					setTempStyle((JTextField) cell, normalCellStyle);
				}
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
							try {
								//If there is only 1 value, set to empty string and empty style.
								if(cell.getText().length() == 1) {
									cell.setText("");
									setTempStyle((JTextField) cell, emptyCellStyle);
								}
								else {
									cell.setText(cell.getText().substring(start, end));
									setTempStyle((JTextField) cell, normalCellStyle);
								}
							} catch (StringIndexOutOfBoundsException e) {
								//Do nothing. It's fine.
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
	private static void clearCells(String state) {
		
		//Reset board to try puzzle again.
		if(state.equals("reset")) {
			//TODO
		} else {
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) {
					cells[i][j].setText("");
					if(sudokuSettings.getMarkEmpty()) {
						setTempStyle(cells[i][j], emptyCellStyle);
					} else {
						setTempStyle(cells[i][j], normalCellStyle);
					}
					
				}
			}
			if(state.equals("beginner")) {
				
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
	
	/**
	 * Update the styles of all cells based on settings.
	 */
	private static void updateCells() {
		String curText;
		for(int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				curText = cells[i][j].getText();
				if(curText.equals("")) {
					if(sudokuSettings.getMarkEmpty()) {
						setTempStyle(cells[i][j], emptyCellStyle);
					} else {
						setTempStyle(cells[i][j], normalCellStyle);
					}
				}
			}
		}
	}
}

/* ALL REFERENCES USED
 * For documentation: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
 * For getting started: https://www.guru99.com/java-swing-gui.html
 * For quick and simple answers like setting a border: stackoverflow.com
 */