public class FirstInFirstOut extends BasePager{
	
	int[] pages;
	int head;
	int tail;
	
	public FirstInFirstOut() {
		super();
		name = "FIFO";
		pages = new int[memory.realSize];
		head = 0;
		tail = pages.length - 1;
	}

	@Override
	public void pageAccess(int pageNum) {
		
		if (isPageInReal(pageNum))  {
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
				int index = 0;
				rpage = memory.getVirTual()[pages[index]].getReference();
				memory.getVirTual()[pages[index]].free();	
				pageFault(rpage,pageNum);
			}
			reorder();
			pages[tail] = pageNum;	
			super.pageAccess(pageNum);
		}
	}
	
	//remove head (pages[0])
	void reorder(){
		for(int i = 0; i < pages.length - 1; i++){
			pages[i] = pages[i+1];
		}
	}
	
}
