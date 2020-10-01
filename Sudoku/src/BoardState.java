import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;

public class BoardState {
	
	
	private ArrayList<HashSet<Integer>> cellSet;
	private HashSet<Integer> fullSet = new HashSet<Integer>();
	
	private Logger log;
	
	/**
	 * Constructor
	 * 
	 * @param logger The logger used in your driver class.
	 */
	public BoardState(Logger logger) {
		log = logger;
		cellSet = new ArrayList<HashSet<Integer>>(81);
		fullSet.addAll(Arrays.asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9}));
		
		//Set every HashSet to a new fullSet
		for(int i = 0; i < 81; i++) {
			cellSet.add(i, new HashSet<Integer>(fullSet));
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
		return cellSet.get(row*9 + column);
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
			//Remove the newText from all related HashSets
			int newNum = Integer.parseInt(newText);
			log.fine("\t\t\tRemove number " + newNum);
			for(int i = 0; i < 9; i++) {
				//Row update
				cellSet.get(i*9 + column).remove(newNum);
				log.finest("\t\t\tCell set " + i + "," + column +  ": " + cellSet.get(i*9 + column));
				//Column update
				cellSet.get(row*9 + i).remove(newNum);
				log.finest("\t\t\tNew cell set " + row + "," + i +  ": " + cellSet.get(row*9 + i));
			}
			
			//Box update
			int rowStart = (row/3)*3;
			int columnStart = (column/3)*3;
			for(int curRow = rowStart; curRow < rowStart+3; curRow++) {
				for(int curColumn = columnStart; curColumn < columnStart+3; curColumn++) {
					cellSet.get(curRow*9 + curColumn).remove(newNum);
					log.finest("\t\t\tNew cell set " + curRow + "," + curColumn +  ": " + cellSet.get(curRow*9 + curColumn));
				}
			}
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
		log.finest("\t\tBoardState -> removeUpdate started");
		/*Only update if that cell's value is not in any related cell (row, column, and box)*/
		
		//Initialize variables.
		String removedNum = cells[row][column].getPrevNumber();
		log.finer("\t\tCell " + row + "," + column + " previous number: " + removedNum);
		log.finer("\t\tCheck row " + row);
		//Update row and column. Skip provided row and column.
		for(int i = 0; i < 9; i++) {
			//Update row
			log.finest("\t\tChecking if cell " + row + "," + i + " can have " + removedNum);
			if(!checkCell(cells, row, column, row, i, removedNum)) {
				cellSet.get(row*9 + i).add(Integer.parseInt(removedNum));
				log.finest("\t\tCell " + row + "," + i + " updated cellSet: " + cellSet.get(row*9 + i));
			}
		}
		log.finer("\t\tCheck column " + column);
		for(int i = 0; i < 9; i++) {
			//Update column
			log.finest("\t\tChecking if cell " + i + "," + column + " can have " + removedNum);
			if(i!=row & !checkCell(cells, row, column, i, column, removedNum)) {
				cellSet.get(i*9 + column).add(Integer.parseInt(removedNum));
				log.finest("\t\tCell " + i + "," + column + " updated cellSet: " + cellSet.get(i*9 + column));
			}
		}
		//Update box (skip provided row and column because they have already been checked).
		int rowStart = (row/3)*3;
		int columnStart = (column/3)*3;
		//Run box checks
		log.finer("\t\tCheck box " + cells[row][column].getBox());
		for(int curRow = rowStart; curRow < rowStart+3; curRow++) {
			for(int curColumn = columnStart; curColumn < columnStart+3; curColumn++) {
				log.finest("\t\tChecking if cell " + curRow + "," + curColumn + " can have " + removedNum);
				if((curRow != row) & (curColumn != column) & !checkCell(cells, row, column, curRow, curColumn, removedNum)){
					cellSet.get(curRow*9 + curColumn).add(Integer.parseInt(removedNum));
					log.finest("\t\tCell " + curRow + "," + curColumn + " updated cellSet: " + cellSet.get(curRow*9 + curColumn));
				}
			}
		}
		log.finer("\t\tBoardState -> removeUpdate finished");
	}
	
	private boolean checkCell(CellField[][] cells, int origRow, int origColumn, int row, int column, String text) {
		for(int i = 0; i < 9; i++) {
			if(i != origRow) {
				log.finest("\t\t\tcell " + i + "," + column + " contains " + cells[i][column].getText());
				if(cells[i][column].getText().equals(text)){
					log.finest("\t\t\tCell " + row + "," + column + " cannot have " + text);
					return true;
				}
			}
		}
		for(int i = 0; i < 9; i++) {
			if(i != origColumn){
				log.finest("\t\t\tcell " + row + "," + i + " contains " + cells[row][i].getText());
				if(cells[row][i].getText().equals(text)){
					log.finest("\t\t\tCell " + row + "," + column + " cannot have " + text);
					return true;
				}
			}
		}
		int rowStart = (row/3)*3;
		int columnStart = (column/3)*3;
		for(int curRow = rowStart; curRow < rowStart+3; curRow++) {
			for(int curColumn = columnStart; curColumn < columnStart+3; curColumn++) {
				//All these &s ensure we do not check the original cell as well as skip cells already checked.
				if(!((curRow == origRow & curColumn == origColumn) | (curRow == row) | (curColumn == column))) {
					log.finest("\t\t\tcell " + curRow + "," + curColumn + " contains " + cells[curRow][curColumn].getText());
					if(cells[curRow][curColumn].getText().equals(text)){
						log.finest("\t\t\tCell " + row + "," + column + " cannot have " + text);
						return true;
					}
				}
			}
		}
		return false;
	}



	/**
	 * Reset the board to a new empty board.
	 */
	public void reset() {	
		for(int i = 0; i < 81; i++) {
			cellSet.set(i, new HashSet<Integer>(fullSet));
		}
	}
}
