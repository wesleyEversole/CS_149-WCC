import java.util.ArrayList;

/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class HPF implements QueInterface {
	private ArrayList<QueInterface> listOfQueues;
	private boolean mode;

	// Highest Priority First
	public HPF(boolean m) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#next()
	 */
	@Override
	public void next() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			return;
		}
		for (int priority = 0; priority < 4; priority++) {
			if (!listOfQueues.get(priority).isEmpty()) {
				listOfQueues.get(priority).next();
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

	@Override
	public float turnAround() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float waitTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float responseTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int throughput() {
		// TODO Auto-generated method stub
		return 0;
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
}
