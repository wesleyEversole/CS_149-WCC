public class BestFit extends BaseSwapper {	
	public BestFit(Memory m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Boolean load(Process p) {
		int processSize = p.getSize();
		if (isMemoryAvailable(processSize)) {
			MemoryBlock mb;
			for (int blockNum=0; blockNum<free.size(); blockNum++) {
				mb=free.get(blockNum);
				//find smallest block here 
			}
			if (processSize <=mb.getSize()) {
				// load process into first available memory location
				MemoryBlock pmb = new MemoryBlock(mb.getStart(), processSize);
				allocated.add(pmb);
				mb.setStart(pmb.getEnd()+1);
				mb.setSize(mb.getSize()-processSize);
				super.load(p,pmb.getStart());
				return true;
			}
		}
		return false;
	}
	@Override
	public String name() {
		return "Best Fit";
	}
}
