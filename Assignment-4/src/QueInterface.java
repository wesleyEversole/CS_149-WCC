/**
 * 
 */

/**
 * @author wesley
 * 
 */
public interface QueInterface {
	public boolean isPreemptive();

	public void add(Process p,float time);
	
	public void next(float time);

	public boolean isEmpty();
	public void setSwapper(Swapper s);
	
	/**
	 * remove any jobs that have not started.
	 */
	public void shutdown();

	public void closeProcess(Process process);
	public float averageTurnAround(); 
	public float averageWaitTime() ;
	public float averageResponseTime() ;
	public int throughput(); 
	public void debugOn();
	public void debugOff();
}
