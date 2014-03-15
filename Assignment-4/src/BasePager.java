public class BasePager implements Pager {

	protected MemorySystem memory;
	protected int hits;
	protected String name;
	private int vSize = 10;
	private int rSize = 4;

	public BasePager() {
		memory = new MemorySystem(vSize, rSize);
		name = "Base";
	}

	public int getHits() {
		return hits;
	}
	@Override
	public void pageAccess(int pageNum) {
		if (pageNum < 0 || pageNum >= memory.virtualSize) {
			System.err.println("Illegal memory page access to page "+ pageNum);
			System.exit(666);
		}
		hits++;
		memory.accessMemory(pageNum);
		for(int i = 0; i < memory.virtualSize; i++){
			if(memory.getVirTual()[i].hasNoReference() || memory.getVirTual()[i].hasPageBeenUsed()){
				System.out.println("VM[" + i +  "] ---> ----" );
			}
			else{
				System.out.print("VM[" + i +  "] ---> RM[" + memory.getVirTual()[i].getReference() + "]");
				System.out.print("    Use Count: " + memory.getVirTual()[i].getFrequency());				
				System.out.println("    Last Accessed: " + memory.getVirTual()[i].getLastUse());				

			}
			
		}
		System.out.println();
	}

	public void pageFault(int rpage, int pageNum) {
		int numberOfReferences = memory.areReferencesFull();
		if(numberOfReferences < memory.realSize){
			memory.getVirTual()[pageNum].setReference(numberOfReferences);
			System.out.println("Page " + pageNum + " refers to page frame " + numberOfReferences);
		}
		else{
			memory.getVirTual()[pageNum].setReference(rpage);
			System.out.println("Page " + pageNum + " refers to page frame " + rpage);

		}

	}

	public String name() {
		return name;
	}
	protected Boolean isPageInReal(int pageNumber) {
		return memory.isPageLoaded(pageNumber);
	}

}
