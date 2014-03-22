import java.util.ArrayList;
import java.util.Comparator;

/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class Scheduler {

	private float currentTime;
	private int pid;
	private ArrayList<Process> scheduleList;
	private QueInterface myQ;
	private Boolean debug;

	public void debugOn() {
		debug = true;
		if (myQ != null) {
			myQ.debugOn();
		}
	}

	public void debugOff() {
		debug = false;
		if (myQ != null) {
			myQ.debugOff();
		}
	}

	public Scheduler() {
		currentTime = 0;
		pid = 0;
		scheduleList = new ArrayList<Process>();
		myQ = null;
		debug = false;
	}

	public void add(Process p) {
		p.setProcessID(pid++);
		scheduleList.add(p);
	}

	public void exec(QueInterface q) {
		boolean moreToDo = true;
		myQ = q;
		java.util.Collections.sort(scheduleList, new Comparator<Process>() {
			public int compare(Process obj1, Process obj2) {
				return Float.compare(obj1.getArrival(), obj2.getArrival());
			}
		});
		if (debug) {
			myQ.debugOn();
		} else {
			myQ.debugOff();
		}
		currentTime = 0.0f;
		while (currentTime<60.0f) {
			System.out.println("[Time:"+currentTime+"s]");
			while (!scheduleList.isEmpty()) {
				if (scheduleList.get(0).getArrival() <= currentTime) {
					Process p = scheduleList.remove(0);
					q.add(p,currentTime);
				} else {
					break;
				}
			}
			q.next(currentTime);
			currentTime += 1.0f;
		}
		System.out.println();
	}

	public float averageTurnAround(int i) {
		return myQ.averageTurnAround();
	}

	public float averageWaitTime(int i) {
		return myQ.averageWaitTime();
	}

	public float averageResponseTime(int i) {
		return myQ.averageResponseTime();

	}

	public int throughput(int i) {
		return myQ.throughput();
	}

	private void displayRunStatistics() {
		String className = myQ.getClass().getName();
		System.out.println("Average turnaround time:" + averageTurnAround(0));
		System.out.println("   Average waiting time:" + averageWaitTime(0));
		System.out.println("  Average response time:" + averageResponseTime(0));
		System.out.println("             Throughput:" + throughput(0));
	}
}
