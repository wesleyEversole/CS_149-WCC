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
	public void load(Process p) {
		super.load(p);
		if (!mem.hasProcess(p.getProcessID()) && mem.isFull()) {
			for (Process memp: mem)
			{
				
			}
		}
	}

	@Override
	public String name() {
		return "First Fit";
	}
}
