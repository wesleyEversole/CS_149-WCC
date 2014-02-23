/**
 * 
 */

/**
 * @author wesley
 * 
 */
public interface QueInterface {
	public boolean isPreemptive();

	public void add(Process p);
	
	public void next(float quanta);

	public boolean isEmpty();
}
