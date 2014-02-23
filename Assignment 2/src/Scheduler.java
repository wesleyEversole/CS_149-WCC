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

	public Scheduler() {
		currentTime = 0;
		pid = 0;
		scheduleList = new ArrayList<>();
	}

	public void add(Process p) {
		scheduleList.add(p);
	}
	
	public void exec(QueInterface q) {
		boolean moreToDo = true;
		
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
			if (currentTime != 100.0f) {
			   q.next(currentTime);
			} 				
			currentTime += 1.0f;

            moreToDo = !q.isEmpty();
		}
		displayRunStatistics();
	}
	
	private void displayRunStatistics() {
		
	}
}
