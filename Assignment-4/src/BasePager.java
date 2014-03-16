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
		System.out.println("Virtual page " + memory.getReal()[rpage]+" at real page "+rpage + " is evicted");
		memory.getVirTual()[pageNum].setReference(rpage);

		memory.unloadReal(rpage);
		memory.loadReal(rpage, pageNum);
		hits--;
	}

	public String name() {
		return name;
	}
	protected Boolean isPageInReal(int pageNumber) {
		return memory.isPageLoaded(pageNumber);
	}

}
