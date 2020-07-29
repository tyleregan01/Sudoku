import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
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
	private static Settings sudokuSettings;
	
	//Attribute sets for each style used in application.
	private static SimpleAttributeSet plainCellStyle;
	private static SimpleAttributeSet badCellStyle;
	private static SimpleAttributeSet startCellStyle;

	public static void main(String[] args) {
		
		/* Initialize objects through method calls */
		setAttributes();
		
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
		cells = new JTextPane[81];
		
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
				cells[cellNum] = new JTextPane();
				setStyle(cells[cellNum], plainCellStyle);
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
	

	private static void addCellListener(JTextPane cell) {
		cell.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				try {
					Document curDocument = documentEvent.getDocument();
					int length = curDocument.getLength();
					if (length == 1) {
						if (!("123456789".contains(curDocument.getText(0, 1)))) {
							removeText(curDocument, 0, 1);
						} else if(sudokuSettings.getMarkDuplicates()) {
							checkCells();
						}
					} else if (length > 1) { // Force max length of 1
						removeText(curDocument, 1, curDocument.getLength() - 1);
					} else {
						// TODO
					}
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			
			private void removeText(Document curDocument, int start, int end) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							curDocument.remove(start, end);
						} catch (BadLocationException e) {
							System.out.println("BAD LOCATION EXCEPTION ON INSERTUPDATE FROM CELL LISTENER");
						}
					}
				});
			}
		});
		
	}

	private static void setStyle(JTextPane pane, SimpleAttributeSet attributeSet) {
        StyledDocument doc = pane.getStyledDocument();
        doc.setParagraphAttributes(0, 2, attributeSet, false);
	}
	
	private static void clearCells() {
		for(int i = 0; i < cells.length; i++) {
			cells[i].setText("");
		}
	}
	
	private static void checkCells() {
		
	}
	
	private static void setAttributes() {
		plainCellStyle = new SimpleAttributeSet();
		StyleConstants.setAlignment(plainCellStyle, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(plainCellStyle, "TIMES NEW ROMAN");
        StyleConstants.setFontSize(plainCellStyle, 24);
        
        badCellStyle = new SimpleAttributeSet();
        StyleConstants.setAlignment(badCellStyle, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(badCellStyle, "TIMES NEW ROMAN");
        StyleConstants.setFontSize(badCellStyle, 24);
        
		startCellStyle = new SimpleAttributeSet();
		StyleConstants.setAlignment(startCellStyle, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(startCellStyle, "TIMES NEW ROMAN");
        StyleConstants.setFontSize(startCellStyle, 24);
	}
}

/* ALL REFERENCES USED
 * For documentation: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
 * For getting started: https://www.guru99.com/java-swing-gui.html
 * For quick and simple answers like setting a border: stackoverflow.com
 * For setStyle: https://coderanch.com/t/339702/java/Alignment-centering-text-JTextPane-work
 */