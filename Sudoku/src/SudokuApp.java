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
	
//	private static JFrame mainFrame;
//	private static JTextArea test;
	private static JTextField[] cells;
	private static Settings sudokuSettings;
	
	//Attribute sets for each style used in application.
	private static CellStyle normalCellStyle;
	private static CellStyle badCellStyle;
	private static CellStyle emptyCellStyle;
	private static CellStyle startCellStyle;

	public static void main(String[] args) {
		
		/* Initialize objects through method calls */
		setCellStyles();
		
		/* Initialize components */
		JFrame mainFrame = new JFrame("Sudoku");
		JMenuBar menu = new JMenuBar();
		// Main Menus
		JMenu gameMenu = new JMenu("Game");
		JMenu optionsMenu = new JMenu("Options");
		//Game menu items
		JMenuItem newGame = new JMenuItem("New Game");
		//Options menu items
		JCheckBoxMenuItem markDuplicates = new JCheckBoxMenuItem("Mark Duplicates");
		//Other components
		JPanel gameWindow = new JPanel();
		JPanel[] grid = new JPanel[9];
		cells = new JTextField[81];
		
		sudokuSettings = new Settings();
		
		//Set up Frame
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(450, 450);
		
		/* Update Components */
		//Add items to menus
		gameMenu.add(newGame);
		optionsMenu.add(markDuplicates);
		//Add menus to toolbar
		menu.add(gameMenu);
		menu.add(optionsMenu);
		//Other updates
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearCells();
			}
		});
		gameWindow.setLayout(new GridLayout(3,3));
		gameWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Initialize variables for loop
		int cellNum = 0;
		//Cells used to hold numbers,
		for(int gridNum = 0; gridNum < 9; gridNum++) {
			grid[gridNum] = new JPanel();
			grid[gridNum].setLayout(new GridLayout(3,3));
			grid[gridNum].setBorder(BorderFactory.createLineBorder(Color.black));
			for(int gridCellNum = 0; gridCellNum < 9; gridCellNum++) {
				cellNum = (gridNum*9) + gridCellNum;
				cells[cellNum] = new JTextField();
				setStyle(cells[cellNum], emptyCellStyle);
				cells[cellNum].setBorder(BorderFactory.createLineBorder(Color.black));
				grid[gridNum].add(cells[cellNum]);
				addCellListener(cells[cellNum]);
			}
			gameWindow.add(grid[gridNum]);
		}
		
		//Add components to frame
		mainFrame.getContentPane().add(BorderLayout.NORTH, menu);
		mainFrame.getContentPane().add(BorderLayout.CENTER, gameWindow);

		mainFrame.setVisible(true);
		
	}
	

	private static void addCellListener(JTextField cell) {
		cell.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				int length = cell.getText().length();
				if (length == 1) {
					setStyle(cell, normalCellStyle);
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

			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				//TODO Look into changing SimpleAttributeSet to Style or AttributeSet
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			private void removeText(JTextField cell, int start, int end) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if(cell.getText().length() == 1) {
							cell.setText("");
							setStyle((JTextField) cell, normalCellStyle);
						}
						else {
							cell.setText(cell.getText().substring(start, end));
							setStyle((JTextField) cell, normalCellStyle);
						}
					}
				});
			}
		});
		
	}

	private static void setStyle(JTextField cell, CellStyle styles) {
        cell.setEditable(styles.editable);
        cell.setBackground(styles.bgColor);
        cell.setForeground(styles.fgColor);
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	}
	
	private static void clearCells() {
		for(int i = 0; i < cells.length; i++) {
			cells[i].setText("");
		}
	}
	
	private static void checkCells() {
		
	}
	
	private static void setCellStyles() {
		//Set styles for potential cell states.
		normalCellStyle = new CellStyle(true, new Color(240, 240, 240), new Color(10, 10, 10));
        badCellStyle = new CellStyle(true, new Color(255, 0, 0), new Color(10, 10, 10));
        emptyCellStyle = new CellStyle(true, new Color(225, 225, 0), new Color(10, 10, 10));
		startCellStyle = new CellStyle(false, new Color(200, 200, 200), new Color(10, 10, 10));
	}
}

/* ALL REFERENCES USED
 * For documentation: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
 * For getting started: https://www.guru99.com/java-swing-gui.html
 * For quick and simple answers like setting a border: stackoverflow.com
 * For setStyle: https://coderanch.com/t/339702/java/Alignment-centering-text-JTextPane-work
 */