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
				memory.loadReal(numberOfReferences, pageNum);
				hits--;
			}
			else {
				int index = (int) Math.floor((Math.random() * memory.realSize));
				//rpage = memory.getVirTual()[index].getReference();
				//memory.getVirTual()[index].free();
				pageFault(index,pageNum);
			}
			super.pageAccess(pageNum);
		}		
	}
}
