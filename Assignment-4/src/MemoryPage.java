
public class MemoryPage {
	private int lastAccess;
	private int useCount;
	private int memoryReference;
	
	public MemoryPage() {
		lastAccess  = Integer.MIN_VALUE;
		useCount  = 0;
		memoryReference = -1;
	}
	
	public void setReference(int r){
		memoryReference = r;
	}
	
	public int getReference(){
		return memoryReference;
	}
	
	public void free(){
		setReference(-1);
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
