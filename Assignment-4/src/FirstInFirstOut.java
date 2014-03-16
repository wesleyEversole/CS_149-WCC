public class FirstInFirstOut extends BasePager {

	int[] pages;
	int tail;
	int head;

	public FirstInFirstOut() {
		super();
		name = "FIFO";
		pages = new int[memory.realSize];
		tail = pages.length - 1;
		head = 0;
	}

	@Override
	public void pageAccess(int pageNum) {
		super.pageAccess(pageNum);
		if (!isPageInReal(pageNum)) {
			// find page to load
			loadPage(pageNum);
		}
	}

	// remove head (pages[0])
	private int loadPage(int vpage) {
		if (head > tail) {
			// real memory filled we need to page fault
			head = 0;
		}

		pageFault(head, vpage);
		return head++;
	}

}
