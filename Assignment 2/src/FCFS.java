import java.util.ArrayList;

/**
 * @author wesley
 * 
 */
public class FCFS extends BaseQue implements QueInterface {
	private ArrayList<Process> processQue;
	
	public FCFS() {
		super();
		processQue = new ArrayList<Process>();
	}
	// First Come First Served
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
		if (processQue.size()==0) {
			System.out.print("[  ] ");
			return;
		}
		Process p = processQue.get(0);
		p.run(quanta);
		if (p.getRunningT()>0.0f) {
		} else {
			closeProcess(p);
			processQue.remove(0);
		} 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return  processQue.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see QueInterface#isPreemptive()
	 */
	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return false;
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
