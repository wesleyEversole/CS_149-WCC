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

	// Highest Priority First
	public HPF() {
		   for (int i=1; i<5;i++) {
		      listOfRR.add(new RR());
		   }
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
	public Process next() {
		// TODO Auto-generated method stub
		return null;
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

}