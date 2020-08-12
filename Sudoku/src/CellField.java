import javax.swing.JTextField;

/**
 * Extension of JTextField that contains position information.
 * 
 * @author Tyler Egan
 */
public class CellField extends JTextField {
	
	/**
	 * Automatically generated UID
	 */
	private static final long serialVersionUID = -4715287002563915673L;
	private int box;
	private int column;
	private int row;
	private String style;
	private String prevNumber;

	/**
	 * Constructor
	 * 
	 * @param box The box this CellField resides in.
	 * @param cell The cell this CellField resides in.
	 */
	public CellField(int row, int column) {
		super();
		this.column = column;
		this.row = row;
		this.prevNumber = "";
	}
	
	/**
	 * Returns the box this cell is in.
	 * 
	 * @return box number.
	 */
	public int getBox() {
		return box;
	}
	
	/**
	 * Returns the column this cell is in.
	 * 
	 * @return column number
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * The last number that was in this cell.
	 * 
	 * @return The previous number
	 */
	public String getPrevNumber() {
		return prevNumber;
	}
	
	/**
	 * Returns the row this cell is in.
	 * 
	 * @return row number.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for the current style applied to his CellField
	 * 
	 * @return The current style
	 */
	public String getStyle() {
		return style;
	}
	
	/**
	 * Set the box this cell is in.
	 * 
	 * @param box The box number this cell is in.
	 */
	public void setBox(int box) {
		this.box = box;
	}
	
	/**
	 * Set the number that is currently in this cell so it's available when a new number is added.
	 * 
	 * @param number The number to set.
	 */
	public void setPrevNumber(String number) {
		prevNumber = number;
	}
	
	/**
	 * Setter for the current style applied to his CellField
	 * 
	 * @param style The style applied to this CellField
	 */
	public void setStyle(String style) {
		this.style = style;
	}
}
