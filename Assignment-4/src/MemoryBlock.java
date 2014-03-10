
public class MemoryBlock {
	private int start;
	private int end;
	private int size;
	
	public MemoryBlock(int start, int size) {
		this.start =start;
		this.size = size;
		this.end = start+size-1;
	}

	public void setSize(int size) {
		this.size=size;
	}
	
	public void setStart(int start) {
		this.start=start;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
}
