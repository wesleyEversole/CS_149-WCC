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
		while (moreToDo) {
			// add items to run queue as time evolves
			System.out.println("Current time: "+ currentTime);
			while (!scheduleList.isEmpty()) {
				

				if (scheduleList.get(0).getArrival() <= currentTime) {
					Process p = scheduleList.remove(0);
					p.setLastQuanta(currentTime);
					p.setActualArrival(currentTime);
					q.add(p);
				} else {
					break;
				}
			}
			if (currentTime % 10 == 0) {
				if (debug) {
					// scheduler debug
					System.out.println();
					System.out.print(String.format("%3.0f |", currentTime));
				}
			}
			if (currentTime != 100.0f) {
				q.next(currentTime);
			} else {
				q.shutdown();
				if (debug) {
					// scheduler debug
					System.out.print(" [   Shutdown         ] |");
				}
			}
			currentTime += 1.0f;

			moreToDo = !q.isEmpty() || currentTime < 100.00f;
			// debug only
			// if (currentTime>200) break;
		}
		System.out.println();
		displayRunStatistics();
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
