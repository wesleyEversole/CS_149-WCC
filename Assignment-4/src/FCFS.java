import java.util.ArrayList;

/**
 * @author wesley
 * 
 */
public class FCFS extends BaseQue implements QueInterface {
	private ArrayList<Process> processQue;
	private int lastProcessInMemory;
	
	public FCFS() {
		super();
		processQue = new ArrayList<Process>();
		lastProcessInMemory = Integer.MIN_VALUE;
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
		loadProcess();
	}

	public void loadProcess() {
		// attempt to fill memory with processes
		int idx;
		if (lastProcessInMemory<0) {
			idx = 0;
		} else {
			idx = lastProcessInMemory;
		}
		Boolean loadSucceeded = true;
		idx--;
		while (loadSucceeded) {
			idx++;
			if (idx<processQue.size()) {
				loadSucceeded = swapper.load(processQue.get(idx));
			} else {
				loadSucceeded = false;
				idx = processQue.size();
			}
		}
		lastProcessInMemory = idx;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#next()
	 */
	@Override
	public void next(float quanta) {
		if (processQue.size()==0) {
			if (debug) {
				// Code for process scheduling debug
				System.out.print(" [   Startup          ] |");
			}
			return;
		}
		Process p = processQue.get(0);
		loadProcess();
		// swap process into memory (if needed)
		if (debug) {
			p.debugOn();
		} else  {
			p.debugOff();
		}
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
