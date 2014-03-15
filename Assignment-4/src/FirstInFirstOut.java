public class FirstInFirstOut extends BasePager{
	
	int[] pages;
	int latest;
	int tail;
	
	public FirstInFirstOut() {
		super();
		name = "FIFO";
		pages = new int[super.memory.realSize];
		latest = 0;
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
			if(super.memory.isVirtualFull() == super.memory.realSize){
				rpage = memory.getVirTual()[pages[0]].getReference();
				memory.getVirTual()[pages[0]].free();				
			}
			reorder();
			pages[tail] = pageNum;	
			pageFault(rpage,pageNum);
		}
	}
	
	//remove head (pages[0])
	void reorder(){
		for(int i = 0; i < pages.length - 1; i++){
			pages[i] = pages[i+1];
		}
	}
	
}
