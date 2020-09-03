
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
	private String activeDifficulty;
	
	/**
	 * Constructor
	 */
	public Settings() {
		markDuplicates = true;
		markEmpty = false;
		unique = false;
		activeDifficulty = "beginner";
	}
	
	/**
	 * Getter for active difficulty.
	 * 
	 * @return The active difficulty
	 */
	public String getActiveDifficulty() {
		return activeDifficulty;
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
	 * Setter for active difficulty.
	 * 
	 * @param newDifficulty The active difficulty
	 */
	public void setActiveDifficulty(String newDifficulty) {
		activeDifficulty = newDifficulty;
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
