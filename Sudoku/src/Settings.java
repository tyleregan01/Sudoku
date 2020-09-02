
/**
 * A class to store all 
 * References used listed at the bottom.
 * 
 * @author Tyler Egan
 */
public class Settings {
	private boolean markDuplicates;
	private boolean markEmpty;
	private boolean unique;
	
	/**
	 * Constructor
	 */
	public Settings() {
		markDuplicates = true;
		markEmpty = false;
		unique = false;
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
	 * Returns whether Empty should be marked or not.
	 * 
	 * @return true or false
	 */
	public boolean getMarkEmpty() {
		return markEmpty;
	}
	
	/**
	 * Returns whether boards must be unique or not.
	 * 
	 * @return true or false
	 */
	public boolean getUnique() {
		return unique;
	}
	
	/**
	 * Switch the markDuplicates setting.
	 */
	public void updateMarkDuplicates() {
		markDuplicates = !markDuplicates;
	}
	
	/**
	 * Switch the markEmpty setting.
	 */
	public void updateMarkEmpty() {
		markEmpty = !markEmpty;
	}
	
	/**
	 * Switch the unique settings.
	 */
	public void updateUnique() {
		unique = !unique;
	}
}
