import java.util.ArrayList;

/**
 * 
 */

/**
 * @author wesley
 * 
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// this program should take command line input to determine which
		// algorithm is going to be used.

		// FCFS
		//run(FCFS.class);

		// SJF
		run(SJF.class);

		// SRT
		//run(SRT.class);

		// RR

		//run(RR.class);

		// HPF nonpreemp
		//run(HPFnonpreempt.class);

		// HPF preemp
		//run(HPFpreempt.class);

	}

	public static void run(Class c) {
		QueInterface q = null;
		float[] turnAroundTime={0.0f,0.0f,0.0f,0.0f,0.0f};
		float[] waitingTime={0.0f,0.0f,0.0f,0.0f,0.0f};
		float[] responseTime={0.0f,0.0f,0.0f,0.0f,0.0f};
		int[] throughput={0,0,0,0,0};
		int n = 5;		
		boolean multiQue= false;
		
		//if (q.getClass().getName().substring(0, 3).equals("HPF")) {
		//	multiQue = true;
		//} 
		
		System.out.println("===================================================");
		System.out.println("Running "+c.getName());
		
		for (int i = 0; i < n; i++) {
			// run each scheduler n times
			try {
				q = (QueInterface) c.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Scheduler s = new Scheduler();
			for (int process = 0; process < 30; process++) {
				s.add(new Process());
			}
			s.exec(q);
			turnAroundTime[0] += s.averageTurnAround(0);
			waitingTime[0] += s.averageWaitTime(0);
			responseTime[0] += s.averageResponseTime(0);
			throughput[0] += s.throughput(0);
		}

		System.out.println();
		System.out.println("Averages for "+n+" runs of "+c.getName());
		System.out.println("Average turnaround time:"+turnAroundTime[0]/n);
		System.out.println("   Average waiting time:"+waitingTime[0]/n);
		System.out.println("  Average response time:"+responseTime[0]/n);
		System.out.println("             Throughput:"+throughput[0]/n);

	}
}
