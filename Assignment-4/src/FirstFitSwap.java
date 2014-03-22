import com.sun.org.apache.bcel.internal.generic.ALOAD;

public class FirstFitSwap extends BaseSwapper {

	public FirstFitSwap(Memory m) {
		super(m);
	}

	/*
	 * This method load processes into the main memory. If the process is not
	 * active(not already in memory) and if memory is full then swap out a
	 * process.
	 * 
	 * @param p loading process.
	 */
	@Override
	public Boolean load(Process p) {
		int processSize = p.getSize();
		if (isMemoryAvailable(processSize)) {
			MemoryBlock mb;
			for (int blockNum=0; blockNum<free.size(); blockNum++) {
				mb=free.get(blockNum);
				if (processSize <=mb.getSize()) {
					// load process into first available memory location
					MemoryBlock pmb = new MemoryBlock(mb.getStart(), processSize);
					allocated.add(pmb);
					mb.setStart(pmb.getEnd()+1);
					mb.setSize(mb.getSize()-processSize);
					super.load(p,pmb);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String name() {
		return "First Fit";
	}
}
