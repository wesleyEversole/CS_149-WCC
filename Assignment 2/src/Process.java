import java.util.Random;

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
	private float actualArrival = Float.NaN;
	// xrun is the expected total run time.
	private float xrun;
	// the running time should be used in the extra credit
	public float runningT;
	public float waitTime;
	public float firstRun = Float.NaN;
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
	 * @param waitTime
	 *            the waitTime to set
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
		arrival = round10th(arrivalMaker());
		xrun = round10th(xrunMaker());
		runningT = xrun;
		lastQuanta = 0.0f;
	}

	private float round10th(float f) {
		return (float) (Math.rint(f * 10.0f) / 10.0f);
	};

	/**
	 * @param quanta
	 *            the lastQuanta to set
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

	public void setActualArrival(float arrival) {
		this.actualArrival = arrival;
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
		return (float) (Math.random() * 10.0);
	}

	private int priorityMaker() {
		// will make a random priority from 1-4
		return (int) (Math.floor((Math.random() * 4.0) + 1.0));
	}

	private float arrivalMaker() {
		// need code to get a random float from 0 to 99
		// might want to make it unable to use the same number more then once
		return (float) (Math.random() * 99.0);

	}

	public boolean isActive() {
		return (!Float.isNaN(firstRun));
	}

	public void run(float quanta) {
		// handle wait time accumulation
		waitTime += quanta - lastQuanta;
		if (waitTime >= 0) {
			--waitTime;
		}
		lastQuanta = quanta;
		runningT -= 1.0f;
		if (Float.isNaN(firstRun)) {
			firstRun = quanta;
		}
		System.out.print(this);
	}

	public float getResponseTime() {
		// TODO Auto-generated method stub
		return firstRun - actualArrival;
	}

	public float getTurnAroundTime() {
		// TODO Auto-generated method stub
		return lastQuanta - actualArrival;
	}

	@Override
	public String toString() {
		return String.format("P%3d ", pid);
	}

}
