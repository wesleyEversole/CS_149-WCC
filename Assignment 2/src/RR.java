import java.util.ArrayList;


/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class RR implements QueInterface {
	private ArrayList<Process> processQue; 
	public RR() {
		processQue = new ArrayList<>(); ;
	}

	/**
	 * @param runningQueue
	 */

	// Round Robin
	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#add(Process)
	 */
	@Override
	public void add(Process p) {
		processQue.add(p);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#next()
	 */
	@Override
	public float next(float qunata) {
		//do things
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		
		return processQue.isEmpty();
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

	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return true;
	}

}
