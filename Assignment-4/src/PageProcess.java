
public class PageProcess {
private Pager runPage;
	public PageProcess(Pager p){
	runPage=p;
}
public void run(){
	int page = 0;
	// randomly picks a page to load
	
	runPage.pageAccess(page);
}
}
