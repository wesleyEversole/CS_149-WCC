public class LeastRecentlyUsed extends BasePager {
	public LeastRecentlyUsed() {
		super();
		name = "LRU";
	}

	@Override
	public void pageAccess(int pageNum) {

		if (isPageInReal(pageNum)) {
			super.pageAccess(pageNum);
			// page is already loaded
		} else {
			// find page to load
			int rpage = Integer.MIN_VALUE;
			// .... algorithm goes here ........
			pageFault(rpage, pageNum);
		}
	}

}
