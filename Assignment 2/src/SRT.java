import java.util.ArrayList;


/**
 * @author wesley
 * 
 */
public class SRT extends BaseQue implements QueInterface {
	// Shortest Remaining Time
	private ArrayList<Process> processQue;

	
	public SRT(){
		processQue = new ArrayList<>();
	}
	
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
		
		int index = 0;
		float runtime = 1;
		for(Process i : processQue){
			float xrun = i.getXrun();
			if(xrun < runtime){
				index = processQue.indexOf(i);
				runtime = xrun;
			}
		}
		Process p = processQue.get(index);
		p.run(quanta);
		closeProcess(p);
		processQue.remove(index);
		
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
		return true;
	}


}
