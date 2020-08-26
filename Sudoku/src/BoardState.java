import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;

public class BoardState {
	
	
	private ArrayList<HashSet<Integer>> boxSet;
	private ArrayList<HashSet<Integer>> columnSet;
	private ArrayList<HashSet<Integer>> rowSet;
	private HashSet<Integer> fullSet = new HashSet<Integer>();
	
	private Logger log;
	
	/**
	 * Constructor
	 * 
	 * @param logger The logger used in your driver class.
	 */
	public BoardState(Logger logger) {
		log = logger;
		boxSet = new ArrayList<HashSet<Integer>>();
		columnSet = new ArrayList<HashSet<Integer>>();
		rowSet = new ArrayList<HashSet<Integer>>();
		fullSet.addAll(Arrays.asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9}));
		
		for(int i = 0; i < 9; i++) {
			boxSet.add(i, new HashSet<Integer>(fullSet));
			columnSet.add(i, new HashSet<Integer>(fullSet));
			rowSet.add(i, new HashSet<Integer>(fullSet));
		}
	}
	
	

	/**
	 * Returns a HashSet containing all numbers that can legally be entered
	 * into the provided cell.
	 * 
	 * @param row The row the cell is in.
	 * @param column The column the cell is in.
	 * @return HashSet of available numbers.
	 */
	public HashSet<Integer> getCellSet(int row, int column) {
		HashSet<Integer> cellSet = new HashSet<Integer>(fullSet);
		log.finer("\t\t\tboxSet: " + boxSet.get(((row/3)*3)+(column/3)));
		log.finer("\t\t\tcolumnSet: " + columnSet.get(column));
		log.finer("\t\t\trowSet: " + rowSet.get(row));
		cellSet.retainAll(boxSet.get(((row/3)*3)+(column/3)));
		cellSet.retainAll(columnSet.get(column));
		cellSet.retainAll(rowSet.get(row));
		return cellSet;
	}



	/**
	 * Adds the provided number to the board.
	 * 
	 * @param row The row of the cell that was updated.
	 * @param column The column of the cell that was updated.
	 * @param newText The new value that was added to the cell.
	 */
	public void newNumberUpdate(int row, int column, String newText) {
		try {
			int newNum = Integer.parseInt(newText);
			log.fine("\t\t\tRemove number " + newNum);
			rowSet.get(row).remove(newNum);
			log.finer("\t\t\tNew row set:    " + rowSet.get(row));
			columnSet.get(column).remove(newNum);
			log.finer("\t\t\tNew column set: " + columnSet.get(column));
			int box = ((row/3)*3)+(column/3);
			boxSet.get(box).remove(newNum);
			log.finer("\t\t\tNew box set:    " + boxSet.get(box));
		} catch (Exception e) {
			System.out.println(e);
		}
	}



	/**
	 * Clears the value of the provided cell from the board.
	 * 
	 * @param cells The 2D array of all cells.
	 * @param row The row of the cell that was updated.
	 * @param column The column of the cell that was updated.
	 */
	public void removeUpdate(CellField[][] cells, int row, int column) {
		//Initialize variables.
		String removedNum = cells[row][column].getPrevNumber();
		boolean rowFound = false;
		boolean columnFound = false;
		//Search row and column
		for(int i = 0; i < 9; i++) {
			if(cells[i][column].getText().equals(removedNum) & (i != row)){
				rowFound = true;
			}
			if(cells[row][i].getText().equals(removedNum) & (i != column)){
				columnFound = true;
			}
		}
		//Run box checks
		boolean boxFound = false;
		int rowStart = (row/3)*3;
		int columnStart = (column/3)*3;
		for(int curRow = rowStart; curRow < rowStart+3; curRow++) {
			for(int curColumn = columnStart; curColumn < columnStart+3; curColumn++) {
				if(cells[curRow][curColumn].getText().equals(removedNum) & (curRow != row) & (curColumn != column)){
					boxFound = true;
				}
			}
		}
		//Update board info
		if(!rowFound) {
			rowSet.get(row).add(Integer.parseInt(removedNum));
		}
		if(!columnFound) {
			columnSet.get(column).add(Integer.parseInt(removedNum));
		}
		if(!boxFound) {
			boxSet.get(cells[row][column].getBox()).add(Integer.parseInt(removedNum));
		}
	}
}
