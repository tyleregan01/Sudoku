
/**
 * A class to store all 
 * References used listed at the bottom.
 * 
 * @author Tyler Egan
 */
public class Settings {
	private boolean markDuplicates;
	
	/**
	 * Constructor
	 */
	public Settings() {
		markDuplicates = false;
	}
	
	/**
	 * Switch the markDuplicates setting.
	 */
	public void updateMarkDuplicates() {
		markDuplicates = !markDuplicates;
	}
	
	/**
	 * Returns whether duplicates should be marked or not.
	 * 
	 * @return true or false
	 */
	public boolean getMarkDuplicates() {
		return markDuplicates;
	}
}
