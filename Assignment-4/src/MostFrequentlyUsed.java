public class MostFrequentlyUsed extends BasePager {

	public MostFrequentlyUsed() {
		super();
		name = "MFU";
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
