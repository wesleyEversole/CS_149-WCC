
public class MemoryPage {
	private int lastAccess;
	private int useCount;
	
	public MemoryPage() {
		lastAccess  = Integer.MIN_VALUE;
		useCount  = 0;
	}
	
	public void setAccessTime(int time) {
		lastAccess = time;
	}
	
	public int getFrequency() {
		return useCount;
	}
	
	public int getLastUse() {
		return lastAccess;
	}

	public void pageHit(int time) {
		useCount++;
		lastAccess = time;
	}
}
