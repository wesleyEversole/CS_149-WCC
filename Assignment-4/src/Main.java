
public class Main {

	/**
	 * @param args
	 */
	
	private static String header = "=============================================";
	
	private static void runSwap(Swapper swap) {
		QueInterface q = new FCFS();
		q.setSwapper(swap);
		Scheduler s = new Scheduler();
		for (int process = 0; process < 300; process++) {
			s.add(new Process());
		}
		s.exec(q);
	}
	
	private static void swapSet() {
		int totalSwaps =0;
		int numTrials = 5;
		for(SwapAlgorithm sa: SwapAlgorithm.values()) {
			totalSwaps = 0;
			Swapper swap=null;
			for(int trial=1; trial <=numTrials; trial++) {
				switch(sa) {
				case FirstFit:
					swap = new FirstFitSwap();
				}
				runSwap(swap);
				totalSwaps += swap.getSwapCount();
			}
			System.out.println(swap.name()+": Average swaps "+totalSwaps/numTrials);
		}
		
	}
	private static void pageSet() {
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Assignment 4 - by West Code Custom");
		System.out.println("Swapping Algorithm Tests");
		swapSet();
		System.out.println();
		System.out.println(header);
		System.out.println("Paging Algorithm Tests");
		pageSet();
	}

}
