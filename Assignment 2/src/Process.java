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

}
