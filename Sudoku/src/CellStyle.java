

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
	public String name;
	

	/**
	 * Constructor
	 */
	public CellStyle(String name, boolean edit, Color bg, Color fg) {
		this.name = name;
		editable = edit;
		bgColor = bg;
		fgColor = fg;
	}
}
