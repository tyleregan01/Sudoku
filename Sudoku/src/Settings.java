
/**
 * A class to store all 
 * References used listed at the bottom.
 * 
 * @author Tyler Egan
 */
public class Settings {
	private boolean markDuplicates;
	
	public Settings() {
		markDuplicates = false;
	}
	
	/**
	 * Switch the markDuplicates setting.
	 */
	public void updateMarkDuplicates() {
		markDuplicates = !markDuplicates;
	}
	
	public boolean getMarkDuplicates() {
		return markDuplicates;
	}
}
