
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
	
	public int isVirtualFull(){
		int references = 0;
		for(int i = 0; i < virtual.length; i++){
			if (virtual[i].getReference() != -1)
				references++;
		}
		
		return references;
	}
	
	public void accessMemory(int page) {
		virtual[page].setAccessTime(time);
	}
	
	public Boolean isPageLoaded(int page) {
		return (virtual[page].getReference() != -1);
	}
	
	public void incrementTime() {
		time++;
	}
}
