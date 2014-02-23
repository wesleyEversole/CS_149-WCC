import java.util.ArrayList;

/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class RR extends BaseQue implements QueInterface {
	private ArrayList<Process> processQue;

	public RR() {
		processQue = new ArrayList<>();
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
	public void next(float quanta) {
		// do things
		Process p = processQue.remove(0);
		p.run(quanta);
		if (p.getRunningT()>0.0f) {
			// return to queue we did not finish in the quanta
			processQue.add(p);
		} else {
			closeProcess(p);
		}
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
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return true;
	}

}
