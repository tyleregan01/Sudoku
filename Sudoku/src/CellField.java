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
	private int boxNum;
	private int cellNum;
	private String style;

	/**
	 * Constructor
	 * 
	 * @param box The box this CellField resides in.
	 * @param cell The cell this CellField resides in.
	 */
	public CellField(int box, int cell) {
		super();
		boxNum = box;
		cellNum = cell;
	}
	
	/**
	 * Returns the box this CellField is in.
	 * 
	 * @return box number.
	 */
	public int getBoxNum() {
		return boxNum;
	}
	
	/**
	 * Returns the cell this CellField is in.
	 * 
	 * @return cell number.
	 */
	public int getCellNum() {
		return cellNum;
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
	 * Setter for the current style applied to his CellField
	 * 
	 * @param style The style applied to this CellField
	 */
	public void setStyle(String style) {
		this.style = style;
	}
}
