public class BestFit extends BaseSwapper {
	public BestFit(Memory m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean load(Process p) {
		int processSize = p.getSize();
		if (isMemoryAvailable(processSize)) {
<<<<<<< HEAD
			MemoryBlock smallest = free.get(0);
			for (int blockNum = 1; blockNum < free.size(); blockNum++) {
				//find smallest that fits
				if (free.get(blockNum).getSize() < smallest.getSize()
						&& processSize <= free.get(blockNum).getSize()) {
					smallest = free.get(blockNum);
				}
			}
			// load process into first available memory location
			MemoryBlock pmb = new MemoryBlock(smallest.getStart(), processSize);
			allocated.add(pmb);
			smallest.setStart(pmb.getEnd() + 1);
			smallest.setSize(smallest.getSize() - processSize);
			super.load(p, pmb.getStart());
			return true;
=======
			MemoryBlock smallest = null;
			for (int blockNum = 0; blockNum < free.size(); blockNum++) {
				// find smallest that fit
				if (processSize <= free.get(blockNum).getSize()) {
					if (smallest == null
							|| free.get(blockNum).getSize() < smallest
									.getSize()) {
						smallest = free.get(blockNum);
					}
				}
			}
			if (smallest != null) {
				// load process into first available memory location
				MemoryBlock pmb = new MemoryBlock(smallest.getStart(),
						processSize);
				allocated.add(pmb);
				super.removeFromFree(smallest, pmb.getEnd(), processSize);
				super.load(p, pmb);
				return true;
			}
>>>>>>> FETCH_HEAD
		}
		return false;
	}

	@Override
	public String name() {
		return "Best Fit";
	}
}
