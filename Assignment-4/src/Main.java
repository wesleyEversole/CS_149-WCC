
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
	private static void runPager(Pager p, int references) {
		PageProcess pp = new PageProcess(p);
		for (int i=0; i<references; i++) {
			pp.run();
		}
	}
	
	private static void pageSet() {
		int numberPageReferences = 100;
		int numberTrials = 5;
		
		for (PageAlgorithm pa: PageAlgorithm.values()) {
			Pager pager = null;
			Double hitRatio;
			Double totalHitRatio;
			hitRatio = 0.0;
			totalHitRatio = 0.0;
			System.out.println();
			
			for (int trial=1; trial <=numberTrials; trial++) {
				switch(pa) {
				case FirstInFirstOut:
					pager = new FirstInFirstOut();
					break;
				case LeastRecentlyUsed:
					pager = new LeastRecentlyUsed();
					break;
				case LeastFrequentlyUsed:
					pager = new LeastFrequentlyUsed();
					break;
				case MostFrequentlyUsed:
					pager = new MostFrequentlyUsed();
					break;
				case RandomPick:
					pager = new RandomPick();
					break;
				}
				runPager(pager,numberPageReferences);
				hitRatio = (pager.getHits()*1.0)/(numberPageReferences*1.0);
				System.out.println(pager.name() + " Trial "+trial+" Hit Ratio " + hitRatio);
				totalHitRatio += hitRatio;
			}
			System.out.println(pager.name()+ " Average Hit Ratio "+ totalHitRatio/numberTrials);
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Assignment 4 - by West Code Custom");
		System.out.println("Swapping Algorithm Tests");
		//swapSet();
		System.out.println();
		System.out.println(header);
		System.out.println("Paging Algorithm Tests");
		pageSet();
	}

}