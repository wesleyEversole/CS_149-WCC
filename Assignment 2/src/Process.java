/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class Process {
	// priority should be from 1 to 4. 1 is the highest priority
	private int priority;
	// arrival should be the time at which the program is to arrive. will be
	// used in sorting
	private float arrival;
	// xrun is the expected total run time.
	private float xrun;
	// the running time should be used in the extra credit
	public float runningT;
	public float waitTime;
	private float lastQuanta;
	private int pid;

	public void setProcessID(int pid) {
		this.pid = pid;
	}
	/**
	 * @return the waitTime
	 */
	public float getWaitTime() {
		return waitTime;
	}

	/**
	 * @param waitTime the waitTime to set
	 */
	public void setWaitTime(float waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * @param priority
	 * @param arrival
	 * @param xrun
	 */
	public Process() {
		// just generated code for now.
		priority = priorityMaker();
		arrival = arrivalMaker();
		xrun = xrunMaker();
		runningT = xrun;
		lastQuanta = 0.0f;
	}

	/**
	 * @param quanta the lastQuanta to set
	 */
	public void setLastQuanta(float quanta) {
		this.lastQuanta = quanta;
	}

	public int getPriority() {
		return priority;
	}

	private void setPriority(int priority) {
		this.priority = priority;
	}

	public float getArrival() {
		return arrival;
	}

	private void setArrival(float arrival) {
		this.arrival = arrival;
	}

	public float getXrun() {
		return xrun;
	}

	private void setXrun(float xrun) {
		this.xrun = xrun;
	}

	public float getRunningT() {
		return runningT;
	}

	public void setRunningT(float runningT) {
		this.runningT = runningT;
	}

	private float xrunMaker() {
		// should return random float from 0.1-10
		return 0;
	}

	private int priorityMaker() {
		// will make a random priority from 1-4
		return 0;
	}

	private float arrivalMaker() {
		// need code to get a random float from 0 to 99
		// might want to make it unable to use the same number more then once
		return 0;

	}
	
	public void run(float quanta) {
		// handle wait time accumulation
		waitTime += quanta - lastQuanta -1;
		lastQuanta = quanta;
		System.out.println(this);
	}

	public float getResponseTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getTurnAroundTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "P"+pid;
	}

}
