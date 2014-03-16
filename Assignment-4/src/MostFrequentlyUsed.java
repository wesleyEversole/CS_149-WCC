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
			int numberOfReferences = memory.areReferencesFull();
			if(numberOfReferences < memory.realSize){
				pageFault(numberOfReferences, pageNum);
			}
			else {
				int index = 0;
				int mostUse = Integer.MIN_VALUE;
				for(int i = 0; i < memory.virtualSize; i++){
					if(memory.getVirTual()[i].getFrequency() > mostUse && memory.getVirTual()[i].getFrequency() > 0){
						mostUse = memory.getVirTual()[i].getFrequency();
						index = i;
					}
				}
				rpage = memory.getVirTual()[index].getReference();
				memory.getVirTual()[index].free();	
				System.out.println("Virtual at " + index + " is evicted");
				pageFault(rpage,pageNum);
			}
			super.pageAccess(pageNum);
		}
	}
}
