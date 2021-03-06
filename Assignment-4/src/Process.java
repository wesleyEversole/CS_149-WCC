import java.util.ArrayList;
import java.util.Arrays;
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
	private MemoryBlock memB;
	private int sizeMB;
	private int memLoc;
	private Boolean debug;
	final static private ArrayList<Integer> legalSizes = new ArrayList<>(Arrays.asList(5,11,17,31));

	public void debugOn() {
		debug = true;
	}

	public void debugOff() {
		debug = false;
	}

	public MemoryBlock getMemoryBlock() {
		return memB;
	}

	public void setLocation(int memoryLocation) {
		memLoc = memoryLocation;
	}

	private static String nameList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz" + "0123456789!@#$%^&*-+~?/|=";
	private static int nameSize = nameList.length();

	public String name() {
		// write single character process name
		// special null process is "."
		if (pid < 0) {
			return ".";
		} else {
			int i = pid % nameSize;
			return (nameList.substring(i, i + 1));
		}
	}

	public int getLocation() {
		if (memB == null) {
			return memLoc;
		} else {
			return memB.getStart();
		}
	}

	public void setSize(int size) {
		if (legalSizes.contains(size)) {
			sizeMB = size;
		} else {
			System.err.println("Illegal process size requested: "+ size);
			Thread.dumpStack();
			System.exit(666);
		}
	}

	public int getSize() {
		if (memB == null) {
			return sizeMB;
		} else {
			return memB.getSize();
		}
	}

	public void setProcessID(int pid) {
		if (this.pid >= 0) {
			this.pid = pid;
		} else {
			System.err
					.println("System error- trying to set pid of null process to"
							+ pid + " Process is " + this);
			Thread.dumpStack();
		}
	}

	public int getProcessID() {
		return pid;
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
		arrival = 0;
		xrun = round10th(xrunMaker());
		setSize(sizeMBMaker());
		setLocation(Integer.MIN_VALUE);
		runningT = xrun;
		lastQuanta = 0.0f;
		pid = 0;
		memB = null;
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
		return (float) (Math.ceil(Math.random() * 5.0));
	}

	private int sizeMBMaker() {
		return legalSizes.get((int) (Math.floor(Math.random() * legalSizes.size())));
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
		if (getLocation() < 0) {
			return String.format(" P%3d(%s) M(NA) Size(%2d) |", pid, name(),
					getSize());
		} else {
			return String.format(" P%3d(%s) M(%d) Size(%2d) |", pid, name(),
					getLocation(), getSize());
		}
	}

	public double getLastQuanta() {
		return lastQuanta;
	}

	public void setMemoryBlock(MemoryBlock mb) {
		memB = mb;
	}

}
