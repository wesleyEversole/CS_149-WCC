public class NextFitSwap extends BaseSwapper {

	private int lastlocation;

	public NextFitSwap(Memory m) {
		super(m);
		lastlocation = 0;
	}

	@Override
	public Boolean load(Process p) {
		int processSize = p.getSize();
		if (isMemoryAvailable(processSize)) {
			MemoryBlock mb;
            int limit = 0;
			for (int blockNum = lastlocation; limit<2; blockNum++) {
				if (blockNum>= free.size()) {
					limit++;
					blockNum = 0;
				}
				mb = free.get(blockNum);
				lastlocation=blockNum;
				if (processSize <=mb.getSize()) {
					MemoryBlock pmb = new MemoryBlock(mb.getStart(), processSize);
					allocated.add(pmb);	
					super.removeFromFree(mb, pmb.getEnd(), processSize);
					super.load(p, pmb);
					break;
				}
			}
			if (lastlocation >= free.size()) {
				lastlocation = 0;
			}
			return true;
		}
		return false;
	}

	@Override
	public String name() {
		return "Next Fit";
	}
}
