import java.util.ArrayList;

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
		scheduleList.add(p);
	}
	
	public void exec(QueInterface q) {
		boolean moreToDo = true;
		myQ = q;
		while (moreToDo)  {
			// add items to run queue as time evolves
			while (!scheduleList.isEmpty()) {

				if (scheduleList.get(0).getArrival()<=currentTime)   {
					Process p = scheduleList.remove(0);
					p.setProcessID(pid++);
					p.setLastQuanta(currentTime);
					q.add(p);
				} else {
					break;
				}
			}
			if (currentTime%10 == 0) {
				System.out.println();
				System.out.print(currentTime+" |");
			}
			if (currentTime != 100.0f) {
			   q.next(currentTime);
			} 				
			currentTime += 1.0f;

            moreToDo = !q.isEmpty() && currentTime<1000.00f;
		}
		displayRunStatistics();
	}
	
	public float averageTurnAround() {
		return myQ.averageTurnAround();
	}
	public float averageWaitTime()  {
		return myQ.averageWaitTime();
	}
	
	public float averageResponseTime() {
		return myQ.averageResponseTime();
		
	}
	public int throughput() {
		return myQ.throughput();
	}
	
	private void displayRunStatistics() {
		
	}
}
