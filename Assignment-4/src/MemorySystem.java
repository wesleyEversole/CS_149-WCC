
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
		ActivePage = Integer.MIN_VALUE;
		time =0;
	}
	
	public void accessMemory(int realPage) {
		virtual[real[realPage]].setAccessTime(time);
	}
	
	public Boolean isPageLoaded(int page) {
		return true;
	}
	
	public void incrementTime() {
		time++;
	}
}
