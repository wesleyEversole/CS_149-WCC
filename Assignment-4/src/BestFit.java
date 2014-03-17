public class BestFit extends BaseSwapper {	
	public BestFit(Memory m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Boolean load(Process p) {
		super.load(p);
		return false;
	}
	@Override
	public String name() {
		return "Best Fit";
	}
}
