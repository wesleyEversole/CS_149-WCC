public class RandomPick extends BasePager {

	public RandomPick() {
		super();
		name = "Random Pick";
	}

	@Override
	public void pageAccess(int pageNum) {

		if (isPageInReal(pageNum)) {
			super.pageAccess(pageNum);
			// page is already loaded
		} else {
			// find page to load
			int rpage = Integer.MIN_VALUE;
			pageNum = (int) Math.floor((Math.random() * memory.realSize));
			pageFault(rpage, pageNum);
		}
	}
}
