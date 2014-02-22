import java.util.ArrayList;

/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class HPF implements QueInterface {
	private ArrayList<RR> listOfRR;
	private boolean mode;

	// Highest Priority First
	public HPF(boolean m) {
		   for (int i=1; i<5;i++) {
		      listOfRR.add(new RR());
		   }
		   mode=m;
		}
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
		if(listOfRR.isEmpty()){
			return listOfRR.isEmpty();
		}
		for(RR rr:listOfRR){
			if(!rr.isEmpty()){
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
	/* (non-Javadoc)
	 * @see QueInterface#isPreemptive()
	 */
	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return mode;
	}

}
