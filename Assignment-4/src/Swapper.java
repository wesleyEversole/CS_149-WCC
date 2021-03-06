
public interface Swapper {
	/**
	 * load process into memory
	 * @param p process
	 * @return true if loaded, false otherwise
	 */
	public Boolean load(Process p);
	/**
	 * unloads process from memory and returns to free pool
	 * @param p process to remove from memory
	 */
	public void unload(Process p);
	/**
	 * get number of processes swapped into memory
	 * @return number of processes swapped into memory
	 */
	public int getSwapCount();
	/**
	 * swapper algorithm name
	 * @return name of swapper algorithm
	 */
	public String name();
	/**
	 * remove process from memory
	 * @param p process to remove
	 */
	public void swap(Process p);
	
}
