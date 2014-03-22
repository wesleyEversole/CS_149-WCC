import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BaseSwapper implements Swapper {
	private int count;
	protected Memory mem;
	protected LinkedList<MemoryBlock> allocated;
	protected LinkedList<MemoryBlock> free;

	protected void deleteProcess(Process p) {
		//System.out.println("delete process "+p.name());
		if (p.getProcessID() == Integer.MIN_VALUE) {
			// ignore request
			return;
		}

//        dumpAllMemoryLists();
//        System.out.println(p);
        
		int ai = allocated.indexOf(p.getMemoryBlock());
		MemoryBlock mb = allocated.remove(ai);
		
		if (free.size()==0) {
			free.add(mb);
			return;
		}
		
		// find where to put back into the free list
		// compact consecutive free blocks.
		// prior and next
		
		int idx = 0;
		MemoryBlock mblast, mbcur;
		Iterator iter = free.iterator();
		Integer mbloc = mb.getStart();
		Integer mbsize = mb.getSize();
		Integer mbend = mb.getEnd();
		mblast = null;
		mbcur = null;

		while (iter.hasNext()) {
			mbcur = (MemoryBlock) iter.next();
			if (mbcur.getEnd() < mbloc) {
				mblast = mbcur;
				idx++;
				continue;
			}
			if (mbcur.getStart() > mbend) {
				// found insertion point
				break;
			}
			idx++;// should never actually execute for proper loop
		}
		// now we know the insertion point
		if (mblast != null) {
			// middle or far right
			if (mblast.getEnd() == mbloc - 1) {
				// merge left
				mblast.setEnd(mbend);
				mblast.setSize(mblast.getSize() + mbsize);
				
				if (mbcur.getStart() == mbend + 1) {
					// merge right
					mblast.setEnd(mbcur.getEnd());
					mblast.setSize(mblast.getSize()+mbcur.getSize());
					free.remove(mbcur);
				}
				mbend = 0; // allocated not needed
				mbsize = 0;
				mbloc = 0;
			} else if (mbcur.getStart() == mbend + 1) {
				// merge right
				mbcur.setStart(mbloc);
				mbcur.setSize(mbcur.getSize() + mbsize);
				mbend = 0; // allocated not needed
				mbsize = 0;
				mbloc = 0;
			} 
		} else if (mbcur.getStart() == mbend + 1) {
			// far left && touching first slot
			// merge right
			mbcur.setStart(mbloc);
			mbcur.setSize(mbcur.getSize() + mbsize);
			mbend = 0; // allocated not needed
			mbsize = 0;
			mbloc = 0;
		}
		
		//System.out.println("mbsize="+mbsize+" mbloc="+mbloc+ " mbend="+mbend);
		if (mbsize!=0 && mbloc!=0 && mbend !=0 && mb !=null) {
			// put it back on free list at idx
			free.add(idx, mb);
		}
		// finally clear the actual memory and display memory contents
		mem.deleteProcess(p);
		mem.display(p.getLastQuanta());
//        System.out.println("Allocated Memory after delete");
//        dumpAllMemoryLists();
	}

	public BaseSwapper(Memory m) {
		count = 0;
		mem = m;
		allocated = new LinkedList<>();
		free = new LinkedList<>();
		// at the start all of memory is available
		MemoryBlock mb = new MemoryBlock(1, m.size);
		free.add(mb);
	}

	public Boolean isMemoryAvailable(int processSize) {
		// not efficient as the swapper has to redo this same
		//   search
		for (MemoryBlock mb: free) {
			if (processSize <=mb.getSize()) {
				return true;
			}
		}
		return false;
	}
	
	private String dumpMemory(LinkedList<MemoryBlock> memoryList) {
		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("#  Size: "+memoryList.size()+"\n");
		for (MemoryBlock mb: memoryList) {
			sb.append("#    Start:"+mb.getStart()+ " End:"+mb.getEnd()+" Size:"+mb.getSize()+"\n");
		}
		return sb.toString();
	}

	private void dumpAllMemoryLists() {
		System.out.println("####################################################");
        System.out.println("# Allocated memory");
        System.out.print(dumpMemory(allocated));
        System.out.println("# Free memory");
        System.out.print(dumpMemory(free));
        System.out.println("####################################################");
	}
	@Override
	public Boolean load(Process p) {
		return false;
	}
	public Boolean load(Process p,MemoryBlock mb) {
        count++;
        System.out.println("load process "+p.name()+" size " + p.getSize()+ "MB duration " +
             p.getXrun()+ " to location "+ mb.getStart());
        mem.addProcess(p,mb.getStart());
        p.setMemoryBlock(mb);
        
		if (mb.getSize()<=0) {
			// we used the whole block ... delete it from the list
			//System.out.println("Free memory block in free"+mb);
			free.remove(mb);
		}
        //dumpAllMemoryLists();
		return false;
	}

	@Override
	public int getSwapCount() {
		return count;
	}

	@Override
	public String name() {
		return "Base";
	}

	@Override
	public void swap(Process p) {
		// dead code - never called ?
		//System.out.println("Swap process");
		deleteProcess(p);
		System.exit(-255);
	}

	@Override
	public void unload(Process p) {
		System.out.println("unload process "+p.name()+ " from location "+p.getMemoryBlock().getStart());
		deleteProcess(p);
	}

	public void removeFromFree(MemoryBlock smallest, int end, int processSize) {
		smallest.setStart(end + 1);
		smallest.setSize(smallest.getSize() - processSize);
		if (smallest.getEnd()<smallest.getStart()) {
			// we used the whole block ... delete it from the list
			free.remove(smallest);
		}
	}

}
