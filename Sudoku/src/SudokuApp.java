import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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
	private static final CellStyle normalCellStyle = new CellStyle("normalCellStyle", true, new Color(240, 240, 240), new Color(10, 10, 10));
	private static final CellStyle badCellStyle = new CellStyle("badCellStyle", true, new Color(255, 0, 0), new Color(10, 10, 10));
	private static final CellStyle emptyCellStyle = new CellStyle("emptyCellStyle", true, new Color(225, 225, 0), new Color(10, 10, 10));
	private static final CellStyle startCellStyle = new CellStyle("startCellStyle", false, new Color(200, 200, 200), new Color(10, 10, 10));
	
	private static Logger log;
	private static Settings sudokuSettings;

	/**
	 * Main method that runs the program.
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {
		
		/* Initialize objects */
		sudokuSettings = new Settings();
		try {
			log = Logger.getLogger("SudokuApp.Log");
			LogManager.getLogManager().reset();
			Handler fileHandler = new FileHandler("./SukoduApp.log");
			LogFormat format = new LogFormat();
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(format);
			log.setLevel(Level.ALL);
			log.addHandler(fileHandler);
			
			log.config("Log created");
			
		} catch (SecurityException | IOException e1) {
			//Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		
		log.config("Components Initialized");
		
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
		
		log.config("Listeners Added.");
		
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
		
		log.config("Menu items added.");
		
		
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
		
		log.config("Cells created");
		
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
				cells[row][column].setBox(boxNum);
				cells[row][column+1].setBox(boxNum);
				cells[row][column+2].setBox(boxNum);
								
			}
			gameWindow.add(box[boxNum]);
		}
		
		log.config("Cells added");
		
		//Add components to frame
		mainFrame.getContentPane().add(BorderLayout.NORTH, menu);
		mainFrame.getContentPane().add(BorderLayout.CENTER, gameWindow);
		
		mainFrame.setVisible(true);
		
		log.config("Board ready!\n\n");
		
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
			 * Also make necessary style updates.
			 */
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				log.info("\tInsert update");
				log.info("\t" + cell.getText() + " added to cell " + cell.getRow() + "," + cell.getColumn());
				int length = cell.getText().length();
				if (length == 1) {
					if (!("123456789".contains(cell.getText()))) {
						log.fine("\tNot a number, remove text.");
						removeText(cell, 0, 1);
					} else {
						if(sudokuSettings.getMarkDuplicates()) {
							log.fine("\tCheck cells for duplicates.");
							checkCells(cell.getBox(), cell.getColumn(), cell.getRow());
						} else {
							log.fine("\tLength 1, don't mark duplicates");
							setTempStyle(cell, normalCellStyle);
						}
					}
				} else if (length > 1) { // Force max length of 1
					log.fine("\tText length > 1. Forcing length to 1.");
					removeText(cell, 1, cell.getText().length());
					return;
				} else {
					log.warning("\tWhat should be done in this situation?");
				}
				log.fine("\tCell " + cell.getRow() + "," + cell.getColumn() + " previous number set to: " + cell.getText());
				cell.setPrevNumber(cell.getText());
			}

			/**
			 * Update style of empty cells.
			 */
			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				log.info("\tRemove Update");
				if(cell.getStyle().equals("badCellStyle")) {
					//Set variables
					ArrayList<Integer> duplicateRows = new ArrayList<Integer>();
					ArrayList<Integer> duplicateColumns = new ArrayList<Integer>();
					int column = cell.getColumn();
					int row = cell.getRow();
					int box = cell.getBox();
					//Check for duplicates
					runDuplicates(duplicateRows, duplicateColumns, box, column, row, true);
				}
				if(cell.getText().length() == 0) {
					if(sudokuSettings.getMarkEmpty()) {
						log.fine("\t\tMark empties");
						setTempStyle(cell, emptyCellStyle);
					} else {
						log.fine("\t\tDont' mark empties");
						setTempStyle(cell, normalCellStyle);
					}
				}
				log.fine("\tCell " + cell.getRow() + "," + cell.getColumn() + " previous number set to: " + cell.getText());
				cell.setPrevNumber(cell.getText());
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
			private void removeText(CellField cell, int start, int end) {
				
				//Use runnable to avoid illegal state exceptions.
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							log.info("\t\tRemoveText()");
							//If there is only 1 value, set to empty string and empty style.
							if(cell.getText().length() == 1) {
								log.fine("\t\tLength is 1");
								cell.setText("");
							}
							else {
								log.fine("\t\tLength was greater than 1");
								String newText = cell.getText().substring(start, end);
								cell.setText(newText);
							}
						} catch (StringIndexOutOfBoundsException e) {
							//Do nothing. It's fine.
						}
						log.fine("\tCell " + cell.getRow() + "," + cell.getColumn() + " previous number set to: " + cell.getText());
						cell.setPrevNumber(cell.getText());
					}
				});
			}
		});
	}
	
	/**
	 * Starting from the given cell, perform style updates as needed.
	 * 
	 * @param box The box the first cell to check is in.
	 * @param column The column of the first cell to check.
	 * @param row The row of the cell to check.
	 */
	private static void checkCells(int box, int column, int row) {

		//Initialize variables.
		log.info("\t\tCheckCells Method");
		log.info("\t\tRow: " + row + "\n\t\tColumn: " + column + "\n\t\tNumber: " + cells[row][column].getText());
		String curStyle = cells[row][column].getStyle();
		ArrayList<Integer> duplicateRows = new ArrayList<Integer>();
		ArrayList<Integer> duplicateColumns = new ArrayList<Integer>();
		
		//Run first check
		runDuplicates(duplicateRows, duplicateColumns, box, column, row, false);

		//What update is needed?
		if(duplicateRows.size() == 0) {
			log.fine("\t\tNo duplicates found");
			if(curStyle.equals("badCellStyle")) {
				//Update bad styles to good styles.
				setTempStyle(cells[row][column], normalCellStyle);
				log.fine("\t\tBad cell updated to good cell.");
				runDuplicates(duplicateRows, duplicateColumns, box, column, row, true);
				return;
			} else {
				log.fine("\t\tNo duplicates found and not bad. Return.");
				if(curStyle.equals("emptyCellStyle")){
					setTempStyle(cells[row][column], normalCellStyle);
				}
				return;
			}
		} else {
			if(curStyle.equals("badCellStyle")) {
				for(int index = 0; index < duplicateRows.size(); index++) {
					setTempStyle(cells[duplicateRows.get(index)][duplicateColumns.get(index)], badCellStyle);
				}
				runDuplicates(duplicateRows, duplicateColumns, box, column, row, true);
				log.fine("\t\tBad cell still bad.");
				return;
			} else {
				log.fine("\t\tGood cell needs updated to bad.");
				setTempStyle(cells[row][column], badCellStyle);
				log.fine("\t\tMain cell: " + row + "," + column);
				for(int index = 0; index < duplicateRows.size(); index++) {
					log.fine("\t\tCell " + duplicateRows.get(index) + ',' + duplicateColumns.get(index) + " updated to bad cell.");
					setTempStyle(cells[duplicateRows.get(index)][duplicateColumns.get(index)], badCellStyle);
				}
				log.fine("\t\tCells updated to bad.");
				return;
			}
			
		}
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
	 * Checks if the number in the first row and column set given is the same
	 * as the number given in the second row and column set.
	 * 
	 * @param rowOrig The row for the first cell.
	 * @param columnOrig The column for the first cell.
	 * @param rowNew The row for the second cell.
	 * @param columnNew The column for the second cell.
	 * @return False if the numbers are different or the row and column sets are the same. True otherwise.
	 */
	private static boolean numbersSame(int rowOrig, int columnOrig, int rowNew, int columnNew) {
		log.fine("\t\t\tNumbersSame()");
		log.fine("\t\t\tCell 1: " +  rowOrig + "," + columnOrig + " Cell 2: " + rowNew + "," + columnNew);
		String curNum = cells[rowOrig][columnOrig].getText();
		if(cells[rowNew][columnNew].getText().contains(curNum)) {
			if(rowOrig == rowNew & columnOrig == columnNew) {
				log.warning("\t\t\tSame cell checked");
				return false; //Checking the same cell
			} else {
				log.fine("\t\t\t\tDuplicate found!");
				return true;
			}
		}
		log.fine("\t\t\tNo duplicate found");
		return false;
	}
	
	/**
	 * Checks if the previous number of a cell is the same
	 * as the number given in the row and column set.
	 * 
	 * @param prevNum The number previously in the main cell.
	 * @param rowNew The row for the cell to compare to.
	 * @param columnNew The column for the cell to compare to.
	 * @return False if the numbers are different, true if they are the same.
	 */
	private static boolean numbersSame(String prevNum, int rowNew, int columnNew) {
		log.fine("\t\t\tNumbersSame()");
		log.fine("\t\t\tNumber: " +  prevNum + " Cell: " + rowNew + "," + columnNew);
		if(cells[rowNew][columnNew].getText().contains(prevNum)) {
			return true;
		}
		return false;
	}
	
	/**
	 *  Run checks on the provided cell and update styles accordingly.
	 *  Also store duplicates in provided arrays.
	 * 
	 * @param duplicateRows empty arraylist to store row information.
	 * @param duplicateColumns empty arraylist to store column information.
	 * @param box the box of the cell to check.
	 * @param column the column of the cell to check.
	 * @param row the row of the cell to check.
	 * @param usePrevNumber whether to use the previous number of the cell or not.
	 */
	private static void runDuplicates(ArrayList<Integer> duplicateRows, ArrayList<Integer> duplicateColumns, int box,
			int column, int row, boolean usePrevNumber) {
		
		//Set prevNum
		String prevNum = cells[row][column].getPrevNumber();
		//Run row checks
		for(int i = 0; i < 9; i++) {
			if(usePrevNumber) {
				if(numbersSame(prevNum, i, column)) {
					duplicateRows.add(i);
					duplicateColumns.add(column);
					log.fine("\t\t\tDuplicate cell added: " + i + "," + column);
				}
			} else {
				if(numbersSame(row, column, i, column)) {
					duplicateRows.add(i);
					duplicateColumns.add(column);
					log.fine("\t\t\tDuplicate cell added: " + i + "," + column);
				}
			}
		}
		//Update to normal
		if(usePrevNumber) {
			if(duplicateRows.size() == 1) {
				log.fine("\t\t\tOnly 1 duplicate found from old number. Set to normal");
				setTempStyle(cells[duplicateRows.get(0)][duplicateColumns.get(0)], normalCellStyle);
			}
			//Reset duplicate tracker.
			duplicateRows.clear();
			duplicateColumns.clear();
		}
		
		//Run column checks
		for(int i = 0; i < 9; i++) {
			if(usePrevNumber) {
				if(numbersSame(prevNum, row, i)) {
					duplicateRows.add(row);
					duplicateColumns.add(i);
					log.fine("\t\t\tDuplicate cell added: " + row + "," + i);
				}
			} else {
				if(numbersSame(row, column, row, i)) {
					duplicateRows.add(row);
					duplicateColumns.add(i);
					log.fine("\t\t\tDuplicate cell added: " + row + "," + i);
				}
			}
		}
		
		if(usePrevNumber) {
			if(duplicateRows.size() == 1) {
				log.fine("\t\t\tOnly 1 duplicate found from old number. Set to normal");
				setTempStyle(cells[duplicateRows.get(0)][duplicateColumns.get(0)], normalCellStyle);
			}
			//Reset duplicate tracker.
			duplicateRows.clear();
			duplicateColumns.clear();
		}
		
		//Run box checks
		int rowStart = box/3 * 3; //Smallest row number
		int rowEnd = rowStart + 3; //Last row number
		int columnStart = (box%3)*3; //Smallest column number
		int columnEnd = columnStart + 3; //Last column number
		for(int curRow = rowStart; curRow < rowEnd; curRow++) {
			for(int curColumn = columnStart; curColumn < columnEnd; curColumn++) {
				if(usePrevNumber) {
					if(numbersSame(prevNum, curRow, curColumn)) {
						duplicateRows.add(curRow);
						duplicateColumns.add(curColumn);
						log.fine("\t\t\tDuplicate cell added: " + curRow + "," + curColumn);
					}
				} else {
					if(numbersSame(row, column, curRow, curColumn)) {
						duplicateRows.add(curRow);
						duplicateColumns.add(curColumn);
						log.fine("\t\t\tDuplicate cell added: " + curRow + "," + curColumn);
					}
				}
			}
		}
		
		if(usePrevNumber) {
			if(duplicateRows.size() == 1) {
				log.fine("\t\t\tOnly 1 duplicate found from old number. Set to normal");
				setTempStyle(cells[duplicateRows.get(0)][duplicateColumns.get(0)], normalCellStyle);
			}
			//Reset duplicate tracker.
			duplicateRows.clear();
			duplicateColumns.clear();
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
	private static void setTempStyle(CellField cell, CellStyle styles) {
		log.finer("\t\t\tCell " + cell.getRow() + "," + cell.getColumn());
		log.finer("\t\t\tSet to style " + styles.name);
        cell.setEditable(styles.editable);
        cell.setBackground(styles.bgColor);
        cell.setForeground(styles.fgColor);
        cell.setStyle(styles.name);
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