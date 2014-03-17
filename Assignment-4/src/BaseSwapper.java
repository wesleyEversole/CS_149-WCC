import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BaseSwapper implements Swapper {
	private int count;
	protected Memory mem;
	protected List<MemoryBlock> allocated;
	protected LinkedList<MemoryBlock> free;

	protected void deleteProcess(Process p) {
		if (p.getProcessID() == Integer.MIN_VALUE) {
			// ignore request
			return;
		}
		mem.deleteProcess(p);

		int ai = allocated.indexOf(p.getMemoryBlock());
		MemoryBlock mb = allocated.remove(ai);
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
			// might be able to merge into this one
			if (mblast.getEnd() == mbloc - 1) {
				// merge left
				mblast.setEnd(mbend);
				mblast.setSize(mblast.getSize() + mbsize);
				if (mbcur.getStart() == mbend + 1) {
					// merge right
					mbcur.setStart(mbloc);
					mbcur.setSize(mbcur.getSize() + mbsize);
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
			// merge right
			mbcur.setStart(mbloc);
			mbcur.setSize(mbcur.getSize() + mbsize);
			mbend = 0; // allocated not needed
			mbsize = 0;
			mbloc = 0;
		}
		if (mbsize==0 && mbloc==0 && mbend ==0 && mb !=null) {
			// put it back on free list at idx
			free.add(idx, mb);
		}
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
	
	@Override
	public Boolean load(Process p) {
		return false;
	}
	public Boolean load(Process p,int ploc) {
        count++;
        mem.addProcess(p,ploc);
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
		deleteProcess(p);
	}

	@Override
	public void unload(Process p) {
		mem.deleteProcess(p);
		mem.display();
	}

}
