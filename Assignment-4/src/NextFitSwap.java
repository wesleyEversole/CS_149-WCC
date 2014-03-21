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

			for (int blockNum = lastlocation; blockNum < free.size(); blockNum++) {
				mb = free.get(blockNum);
				if (processSize <=mb.getSize()) {
					MemoryBlock pmb = new MemoryBlock(mb.getStart(), processSize);
					allocated.add(pmb);
					mb.setStart(pmb.getEnd()+1);
					mb.setSize(mb.getSize()-processSize);
				}

			}
			if (lastlocation == free.size()) {
				lastlocation = 0;
			}
		}
		return false;
	}

	@Override
	public String name() {
		return "Next Fit";
	}
}
