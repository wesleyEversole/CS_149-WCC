public class BasePager implements Pager {

	protected MemorySystem memory;
	protected int hits;
	protected String name;

	public void BasePager(MemorySystem mem) {
		memory = mem;
		name = "Base";
	}

	public int getHits() {
		return hits;
	}
	@Override
	public void pageAccess(int pageNum) {
		if (pageNum <0 || pageNum >= memory.realSize) {
			System.err.println("Illegal memory page access to page "+pageNum);
			System.exit(666);
		}
		hits++;
		memory.accessMemory(pageNum);
	}

	public void pageFault(int rpage, int pageNum) {

	}

	public String name() {
		return name;
	}
	protected Boolean isPageInReal(int pageNumber) {
		return memory.isPageLoaded(pageNumber);
	}

}
