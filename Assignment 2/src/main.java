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
		//run(SRT.class);

		// RR

		//run(RR.class);

		// HPF nonpreemp
		run(HPFnonpreempt.class);

		// HPF preemp
		run(HPFpreempt.class);

	}

	private static float safeFloat(float f) {
		return Float.isNaN(f)?0:f;
	}
	
	public static void run(Class c) {
		QueInterface q = null;
		float[] turnAroundTime={0.0f,0.0f,0.0f,0.0f,0.0f};
		float[] waitingTime={0.0f,0.0f,0.0f,0.0f,0.0f};
		float[] responseTime={0.0f,0.0f,0.0f,0.0f,0.0f};
		int[] throughput={0,0,0,0,0};
		int n = 5;		
		boolean multiQue= false;
		String name = c.getName();
		if (name.length()>2 && name.substring(0, 3).equals("HPF")) {
			multiQue = true;
		}
		
		System.out.println("===================================================");
		System.out.println("Running Scheduler with "+name+" style queue");
		
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
			for (int process = 0; process < 100; process++) {
				s.add(new Process());
			}
			s.exec(q);
			turnAroundTime[0] += s.averageTurnAround(0);
			waitingTime[0] += s.averageWaitTime(0);
			responseTime[0] += s.averageResponseTime(0);
			throughput[0] += s.throughput(0);
			if (multiQue) {
				// get extra stats for priority queues
				for (int qi=0; qi<4; qi++) {
					// todo: use temp value and handle NaN values
					turnAroundTime[qi+1] += safeFloat(((HPF)q).getQue(qi).averageTurnAround());
					waitingTime[qi+1] += safeFloat(((HPF)q).getQue(qi).averageWaitTime());
					responseTime[qi+1] += safeFloat(((HPF)q).getQue(qi).averageResponseTime());
					throughput[qi+1] += safeFloat(((HPF)q).getQue(qi).throughput());
				}
			}
		}


		System.out.println();
		System.out.println("Averages for "+n+" runs of "+name);
		
		if (multiQue) {
			// get extra stats for priority queues
			for (int i=1; i<5; i++) {
				System.out.println();
				System.out.println("Priority "+i);
				System.out.println("Average turnaround time: "+turnAroundTime[i]/n);
				System.out.println("   Average waiting time:"+waitingTime[i]/n);
				System.out.println("  Average response time:"+responseTime[i]/n);
				System.out.println("             Throughput:"+throughput[i]/n);
			}
		}

		System.out.println();
		System.out.println("Average turnaround time:"+turnAroundTime[0]/n);
		System.out.println("   Average waiting time:"+waitingTime[0]/n);
		System.out.println("  Average response time:"+responseTime[0]/n);
		System.out.println("             Throughput:"+throughput[0]/n);

	}
}
