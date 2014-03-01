import java.util.ArrayList;

/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class SJF extends BaseQue implements QueInterface {
	private ArrayList<Process> processQue;
	private int index;
	public SJF() {
		super();
		index = 0;
		processQue = new ArrayList<Process>();
	}
	// Shortest Job First
	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#add(Process)
	 */
	@Override
	public void add(Process p) {
		// TODO Auto-generated method stub
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
		for(Process i : processQue){
			float xrun = i.getXrun();
			if(xrun < p.getXrun()){
				index = processQue.indexOf(i);
				p = processQue.get(0);
			}
		}
		
		p.run(quanta);
					
	if (p.getRunningT()>0.0f) {

		// return to queue we did not finish in the quanta
	} else {
		closeProcess(p);
		processQue.remove(0);
	}
/*		Process p = processQue.get(0);
		p.run(quanta);
			for(Process i : processQue){
				float xrun = i.getXrun();
				if(xrun < p.getXrun()){
					index = processQue.indexOf(i);	
					p = processQue.get(0);
				}
			}
			
						
		if (p.getRunningT()>0.0f) {
			// return to queue we did not finish in the quanta
			processQue.add(p);
		} else {
			closeProcess(p);
			processQue.remove(0);
		}  */
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SchedulingQue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return processQue.isEmpty();
	}

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
