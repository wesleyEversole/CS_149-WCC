public class RandomPick extends BasePager {

	public RandomPick() {
		super();
		name = "Random Pick";
	}

	@Override
	public void pageAccess(int pageNum) {
		super.pageAccess(pageNum); // mark virtual page as accessed now
		if (!isPageInReal(pageNum)) {
			// Load this page into a random real page
			int rpage = (int) Math.floor((Math.random() * memory.realSize));
			// load 
			pageFault(rpage, pageNum);
		}
	}
}
