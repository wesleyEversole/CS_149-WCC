import java.util.ArrayList;

/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class HPF extends BaseQue implements QueInterface {
	private ArrayList<QueInterface> listOfQueues;
	private boolean mode;

	// Highest Priority First
	public HPF(boolean m) {
		listOfQueues = new ArrayList<QueInterface>();
		for (int i = 1; i < 5; i++) {
			if (m) {
				listOfQueues.add(new RR());
			} else {
				listOfQueues.add(new FCFS());
			}
		}
		mode = m;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#add(Process)
	 */
	@Override
	public void add(Process p) {
		if (p != null) {
			int priority = p.getPriority() - 1;
			if (priority >= 0 && priority <= 3) {
				listOfQueues.get(priority).add(p);
			}
		}
	}

	public QueInterface getQue(int i) {
		if (i>0 && i<5) {
			return listOfQueues.get(i-1);
		} else {
			return this;
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#next()
	 */
	@Override
	public void next(float quanta) {
		if (isEmpty()) {
			return;
		}
		for (int priority = 0; priority < 4; priority++) {
			if (!listOfQueues.get(priority).isEmpty()) {
				listOfQueues.get(priority).next(quanta);
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		if (listOfQueues.isEmpty()) {
			return true;
		}
		for (QueInterface q : listOfQueues) {
			if (!q.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see QueInterface#isPreemptive()
	 */
	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return mode;
	}

	@Override
	public void shutdown() {
		if (!listOfQueues.isEmpty()) {
			for (QueInterface q : listOfQueues) {
				q.shutdown();
			}
		}
	}

	public float averageTurnAround() {
		QueInterface qi;
		totalTurnAroundTime = 0.0f;
		for(int i=0; i<4;i++) {
			qi=listOfQueues.get(0);
			totalTurnAroundTime += qi.averageTurnAround()*qi.throughput();
		}
		return totalTurnAroundTime / throughput();
	}

	public float averageWaitTime() {
		QueInterface qi;
		totalWait = 0.0f;
		for(int i=0; i<4;i++) {
			qi=listOfQueues.get(0);
			totalWait += qi.averageWaitTime()*qi.throughput();
		}
		return totalWait / throughput();
	}

	public float averageResponseTime() {
		QueInterface qi;
		totalResponseTime = 0.0f;
		for(int i=0; i<4;i++) {
			qi=listOfQueues.get(0);
			totalResponseTime += qi.averageResponseTime()*qi.throughput();
		}
		return totalResponseTime / throughput();
	}

	public int throughput() {
		if (completedProcesses==0) {
			QueInterface qi;
			for(int i=0; i<4;i++) {
				qi=listOfQueues.get(0);
				completedProcesses += qi.throughput();
			}
		}
		return completedProcesses;
	}
}
