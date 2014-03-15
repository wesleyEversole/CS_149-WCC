
public class PageProcess {

	private Pager runPage;
	private int page = Integer.MIN_VALUE;
	
	public PageProcess(Pager p){
		runPage=p;
	}
	
	public void run(){
		int di=0;
		if (page == Integer.MIN_VALUE) {
			page = 0;
		} else {
			int rnd = (int) Math.floor(Math.random()*10.0);
			// random jump
			if (rnd >= 0 && rnd < 7) {
				di = (int) (Math.floor(Math.random()*3.0)-1.0);
			} else {
				di = (int) (Math.floor(Math.random()*7.0)+2.0);
			}
		}
		page += di;
		if (page <0) { page +=10;}
		else {
			page %= 10;
		}
		runPage.pageAccess(page);
	}
	
}
