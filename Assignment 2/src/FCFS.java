/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class FCFS implements QueInterface {
	// First Come First Served
	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#add(Process)
	 */
	@Override
	public void add(Process p) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#next()
	 */
	@Override
	public float next(float quanta) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
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

	/* (non-Javadoc)
	 * @see QueInterface#isPreemptive()
	 */
	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return false;
	}

}
