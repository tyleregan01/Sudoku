
/**
 * A class to store all 
 * References used listed at the bottom.
 * 
 * @author Tyler Egan
 */
public class Settings {
	private boolean markDuplicates;
	private boolean markEmpty;
	
	/**
	 * Constructor
	 */
	public Settings() {
		markDuplicates = true;
		markEmpty = false;
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
	
	/**
	 * Switch the markEmpty setting.
	 */
	public void updateMarkEmpty() {
		markEmpty = !markEmpty;
	}
	
	/**
	 * Returns whether Empty should be marked or not.
	 * 
	 * @return true or false
	 */
	public boolean getMarkEmpty() {
		return markEmpty;
	}
}
