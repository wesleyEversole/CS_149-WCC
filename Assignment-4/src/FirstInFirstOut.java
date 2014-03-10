
public class FirstInFirstOut extends BasePager{
	
	public FirstInFirstOut() {
		super();
		name = "FIFO";
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
			pageFault(rpage,pageNum);
		}
	}
}
