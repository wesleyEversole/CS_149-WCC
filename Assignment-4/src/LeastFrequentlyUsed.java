
public class LeastFrequentlyUsed extends BasePager{
	
	public LeastFrequentlyUsed() {
		super();
		name = "LFU";
	}
	@Override
	public void pageAccess(int pageNum) {
		
		if (isPageInReal(pageNum))  {
			super.pageAccess(pageNum);
			// page is already loaded
		} else {
			// find page to load
			int rpage=Integer.MIN_VALUE;
			// .... algorithm goes here ........
			int numberOfReferences = memory.areReferencesFull();
			if(numberOfReferences < memory.realSize){
				pageFault(numberOfReferences, pageNum);
			}
			else {
				int index = 0;
				int leastUse = Integer.MAX_VALUE;
				for(int i = 0; i < memory.virtualSize; i++){
					if(memory.getVirTual()[i].getFrequency() < leastUse && memory.getVirTual()[i].getFrequency() > 0){
						leastUse = memory.getVirTual()[i].getFrequency();
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
