public class Paging {
  public void run(PageAlgorithm algor){
	  switch(algor) {
	  case FirstInFirstOut: break;
	  case LeastRecentlyUsed: break;
	  case LeastFrequentlyUsed: break;
	  case MostFrequentlyUsed: break;
	  case RandomPick: break;
	  default:
		  System.out.println("Invaders from another dimension!!!");
		  System.exit(666);
	  }
   }
}
