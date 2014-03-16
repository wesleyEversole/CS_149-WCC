
public class MemorySystem {
	private MemoryPage[] virtual;
	private Integer[] real;
	private Integer ActivePage;
	private Integer time;
	public final int realSize;
	public final int virtualSize;
	public MemorySystem (int vsize,int rsize) {
		virtualSize = vsize;
		realSize = rsize;
		virtual = new MemoryPage[vsize];
		real = new Integer[rsize];
		for (int i=0;i<rsize;i++) {
			real[i] = Integer.MIN_VALUE;
		}
		for (int i=0;i<vsize;i++) {
			virtual[i] = new MemoryPage();
		}
		ActivePage = Integer.MIN_VALUE;
		time =0;
	}
	
	public void setVirtual(int vPosition, int realPosition){
		virtual[vPosition].setReference(real[realPosition]);
	}
	
	public MemoryPage[] getVirTual(){
		return virtual;
	}
	
	public Integer[] getReal(){
		return real;
	}
	
	public int areReferencesFull(){
		int references = 0;
		for(int i = 0; i < virtual.length; i++){
			if (!virtual[i].hasNoReference())
				references++;
		}
		
		return references;
	}
	
	public void accessMemory(int page) {
		virtual[page].pageHit(time);
		incrementTime();
		System.out.print("Hit page "+page+" |");
		for (int i=0; i<realSize; i++) {
			System.out.print(" R[" + i + "]==V[");
			if (real[i] <0) {
				System.out.print("NA");
			} else {
				System.out.print(" "+real[i]);
			}
			System.out.print("] |");
		}
		System.out.println();
	}
	
	public Boolean isPageLoaded(int page) {
		return (virtual[page].getReference()>=0);
	}
	
	public void incrementTime() {
		time++;
	}
	
	public void loadReal(int rpage, int virtualPage) {
		// copy virtual page to real page
		real[rpage] = virtualPage;
		virtual[virtualPage].setReference(rpage);
	}

	public void unloadReal(int rpage) {
		writeBack(rpage);
		int virtualPage = real[rpage];
		virtual[virtualPage].setReference(Integer.MIN_VALUE);
	}
	
	public void writeBack(int rpage) {
		// dummy function
		// real pages are just indexes to virtual so no writeBack operation needed
	}

}
