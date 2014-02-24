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
		//run(SJF.class);

		// SRT
		run(SRT.class);

		// RR

		run(RR.class);

		// HPF nonpreemp

		// HPF preemp

	}

	public static void run(Class c) {
		QueInterface q = null;
		float turnAroundTime=0.0f;
		float waitingTime=0.0f;
		float responseTime=0.0f;
		int throughput=0;
		int n = 5;
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
			for (int process = 0; process < 300; process++) {
				s.add(new Process());
			}
			s.exec(q);
			turnAroundTime += s.averageTurnAround();
			waitingTime += s.averageWaitTime();
			responseTime += s.averageResponseTime();
			throughput += s.throughput();
		}
		System.out.println();
		System.out.println("Averages for "+n+" runs of "+c.getName());
		System.out.println("Average turnaround time:"+turnAroundTime/n);
		System.out.println("   Average waiting time:"+waitingTime/n);
		System.out.println("  Average response time:"+responseTime/n);
		System.out.println("             Throughput:"+throughput/n);

	}
}
