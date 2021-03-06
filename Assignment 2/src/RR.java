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
		super();
		processQue = new ArrayList<Process>();
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
		if (processQue.size()==0) {
			System.out.print("[  ] ");
			return;
		}
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

	@Override
	public void shutdown() {
		// remove any process that is not active
		int i=0;
		while(i < processQue.size()) {
			if (processQue.get(i).isActive()){
				i++;
			} else {
				processQue.remove(i);
			}
		}
	}
}
