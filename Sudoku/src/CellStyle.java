

import java.awt.Color;

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
