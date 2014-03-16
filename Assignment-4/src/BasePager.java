public class BasePager implements Pager {

	protected MemorySystem memory;
	protected int hits;
	protected String name;
	private int vSize = 10;
	private int rSize = 4;

	public BasePager() {
		memory = new MemorySystem(vSize, rSize);
		name = "Base";
		hits = 0;
	}

	public int getHits() {
		return hits;
	}
	@Override
	public void pageAccess(int pageNum) {
		if (pageNum < 0 || pageNum >= memory.virtualSize) {
			System.err.println("Illegal memory page access to page "+ pageNum);
			System.exit(666);
		}
		hits++;
		memory.accessMemory(pageNum);
	}

	public void pageFault(int rpage, int pageNum) {
		if (rpage <0 || rpage>= rSize|| pageNum<0 || pageNum>= vSize) {
			System.err.println("Illegal page fault at real page " + rpage + " for virtual page "+pageNum);
			System.exit(666);
		}
		hits--;
		int currentVpage = memory.getReal()[rpage];

		if (currentVpage>=0 && currentVpage<vSize) {
			// we need to swap a memory page out
			System.out.println("Swap virtual page "+currentVpage+" out from real page " + rpage);
			memory.unloadReal(rpage);
		}
		System.out.println("Swap virtual page " + pageNum+ " into real page "+ rpage);
		memory.loadReal(rpage, pageNum);
	}

	public String name() {
		return name;
	}
	protected Boolean isPageInReal(int pageNumber) {
		return memory.isPageLoaded(pageNumber);
	}

}
