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
	
	public void next();

	public boolean isEmpty();

	public float turnAround();

	public float waitTime();

	public float responseTime();

	public int throughput();
}
