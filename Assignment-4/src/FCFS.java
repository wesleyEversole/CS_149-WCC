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
	public void add(Process p, float quanta) {
		p.setLastQuanta(quanta);
		p.setActualArrival(quanta);
		processQue.add(p);
		loadProcess(quanta);
	}

	public void loadProcess(float quanta) {
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
		//System.out.println("Lastprocess "+lastProcessInMemory);
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
		loadProcess(quanta); // fill memory with processes
		
		Process p;
		// execute all processes in memory
		//  remove any process that completes
		for( int pid=lastProcessInMemory-1; pid >=0; pid--) {
			
			p = processQue.get(pid);
			//System.out.println("Execute process "+pid+":"+p.name()+" time remaining "+p.runningT);
			p.setLastQuanta(quanta);
			p.run(quanta);
			if (p.getRunningT()>0.0f) {
			} else {
				closeProcess(p);
				processQue.remove(pid);
				lastProcessInMemory--;
				//System.out.println("Lastprocess "+lastProcessInMemory);
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
