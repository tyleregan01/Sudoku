

import java.awt.Color;

/**
 * Small class to hold styles for cells.
 * 
 * @author Tyler Egan
 */
public class CellStyle {
	
	public boolean editable;
	public Color bgColor;
	public Color fgColor;
	

	/**
	 * Constructor
	 */
	public CellStyle(boolean edit, Color bg, Color fg) {
		editable = edit;
		bgColor = bg;
		fgColor = fg;
	}
}
