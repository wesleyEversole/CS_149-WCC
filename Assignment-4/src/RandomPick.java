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
			// .... algorithm goes here ........
			int numberOfReferences = memory.areReferencesFull();
			if(numberOfReferences < memory.realSize){
				pageFault(numberOfReferences, pageNum);
			}
			else {
				int randomPage = (int) Math.floor((Math.random() * memory.virtualSize));
				rpage = memory.getVirTual()[randomPage].getReference();
				memory.getVirTual()[randomPage].free();
				System.out.println("Virtual at " + randomPage + " is evicted");
				pageFault(rpage,pageNum);
			}
			super.pageAccess(pageNum);
		}		
	}
}
