/**
 * 
 */

/**
 * Really just keeps all the statistics
 * 
 * @author wesley
 *
 */
public class BaseQue {
	/**
	 * Wait is the sum of all wait times for a process
	 */
	protected float totalWait;
	/**
	 * number of completed processes
	 */
	protected int completedProcesses;
	/**
	 * response time it length of first wait time for a process
	 */
	protected float totalResponseTime;
	/**
	 * total turn around time
	 */
	protected float totalTurnAroundTime;
	
	public BaseQue() {
		totalWait = 0.0f;
		completedProcesses = 0;
		totalResponseTime = 0.0f;
		totalTurnAroundTime = 0.0f;
	}

	protected void closeProcess(Process process) {
		totalWait += process.getWaitTime();
		totalResponseTime += process.getResponseTime();
		totalTurnAroundTime += process.getTurnAroundTime();
		completedProcesses++;
	}

	public float averageTurnAround() {
		return totalTurnAroundTime/completedProcesses;
	}

	public float averageWaitTime() {
		return totalWait/completedProcesses;
	}

	public float averageResponseTime() {
		return totalResponseTime/completedProcesses;
	}

	public int throughput() {
		return completedProcesses;
	}

}
