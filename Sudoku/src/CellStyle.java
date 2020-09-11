import java.awt.Color;

/**
 * Small class to hold styles for cells.
 * 
 * @author Tyler Egan
 */
public class CellStyle {
	
	public Color bgColor;
	public Color fgColor;
	public String name;
	

	/**
	 * Constructor
	 */
	public CellStyle(String name, Color bg, Color fg) {
		this.name = name;
		bgColor = bg;
		fgColor = fg;
	}
}
