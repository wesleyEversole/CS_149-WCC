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

	public Scheduler() {
		currentTime = 0;
		pid = 0;
		scheduleList = new ArrayList<>();
		myQ = null;
	}

	public void add(Process p) {
		p.setProcessID(pid++);
		scheduleList.add(p);
		System.out.print("Process: " + p + " Arrival time: " + p.getArrival());
		System.out.print(" Expected runtime: " + p.getRunningT());
		System.out.print(" Priority:" + p.getPriority());
		System.out.println();
	}

	public void exec(QueInterface q) {
		boolean moreToDo = true;
		myQ = q;
		java.util.Collections.sort(scheduleList, new Comparator<Process>() {
			public int compare(Process obj1, Process obj2) {
				return (int) (obj1.getArrival() - obj2.getArrival());
			}
		});
		while (moreToDo) {
			// add items to run queue as time evolves
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
				System.out.println();
				System.out.print(String.format("%3.0f |", currentTime));
			}
			if (currentTime != 100.0f) {
				q.next(currentTime);
			} else {
				q.shutdown();
				System.out.print("[  ] ");
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
		if (i > 0 && i < 5) {
			return ((HPF) myQ).getQue(i).averageTurnAround();
		}
		return myQ.averageTurnAround();
	}

	public float averageWaitTime(int i) {
		if (i > 0 && i < 5) {
			return ((HPF) myQ).getQue(i).averageWaitTime();
		}
		return myQ.averageWaitTime();
	}

	public float averageResponseTime(int i) {
		if (i > 0 && i < 5) {
			return ((HPF) myQ).getQue(i).averageResponseTime();
		}
		return myQ.averageResponseTime();

	}

	public int throughput(int i) {
		if (i > 0 && i < 5) {
			return ((HPF) myQ).getQue(i).throughput();
		}
		return myQ.throughput();
	}

	private void displayRunStatistics() {
		String className = myQ.getClass().getName();
		if (className.length()>3 && className.substring(0, 3).equals("HPF")) {
			for (int i = 1; i < 5; i++) {
				System.out.println("Priority " + i + " queue");
				System.out.println("Average turnaround time:"
						+ averageTurnAround(i));
				System.out.println("   Average waiting time:"
						+ averageWaitTime(i));
				System.out.println("  Average response time:"
						+ averageResponseTime(i));
				System.out.println("             Throughput:" + throughput(i));
			}

			System.out.println("All queues");
			System.out.println("Average turnaround time:"
					+ averageTurnAround(0));
			System.out.println("   Average waiting time:" + averageWaitTime(0));
			System.out.println("  Average response time:"
					+ averageResponseTime(0));
			System.out.println("             Throughput:" + throughput(0));

		} else {
			System.out.println("Average turnaround time:"
					+ averageTurnAround(0));
			System.out.println("   Average waiting time:" + averageWaitTime(0));
			System.out.println("  Average response time:"
					+ averageResponseTime(0));
			System.out.println("             Throughput:" + throughput(0));
		}
	}
}
