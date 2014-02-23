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
	private ArrayList<Process> scheduleList;
	private boolean canSchedule;
    private double maxTimeToInterrupt;
	public Scheduler() {
		maxTimeToInterrupt = 5.0;
		currentTime = 0;
		canSchedule = true;
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
					q.add(scheduleList.remove(0));
				} else {
					break;
				}
			}
			if (currentTime != 100.0f) {
			   q.next();
			} 				
			currentTime += 1.0f;

            moreToDo = !q.isEmpty();
		}
	}

	private boolean isDone() {
		return false;
	}

}
