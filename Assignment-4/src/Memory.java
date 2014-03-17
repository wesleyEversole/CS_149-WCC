import java.util.ArrayList;
import java.util.Iterator;

public class Memory implements Iterable<Process>{
	public int size;
	private ArrayList<Process> mem;
	private Process nullProcess;

	public Memory(int size) {
		if (size < 0 || size > 100) {
			System.err.println("Memory limited to 100MB");
			System.exit(1);
		}
		mem = new ArrayList<>();
		mem.ensureCapacity(size);
		nullProcess = new Process();
		nullProcess.setProcessID(Integer.MIN_VALUE);

		for (int i = 0; i <= size; i++) {
			mem.add(nullProcess);
		}
		this.size = size;
	}

	/*
	 * Method isFull checks if the memory is full.
	 * 
	 * @return boolean true if it is full.
	 */
	public boolean isFull() {
		if (mem.size() == 100) {
			return true;
		}
		return false;
	}

	/*
	 * Method hasProcess check if the process is in the memory
	 * 
	 * @param pid
	 *
	 * @return boolean true if process is in memory, false otherwise.
	 */
	public boolean hasProcess(int pid) {
		for (Process current : mem) {
			if (current.getProcessID() == pid) {
				return true;
			}
		}
		return false;
	}

	public void deleteProcess(Process p) {
		int psize;
		int ploc;
		if (p.getProcessID() == Integer.MIN_VALUE) {
			// ignore request
			return;
		}
		psize = p.getSize();
		ploc = p.getLocation();

		for (int l = ploc; l < ploc + psize; l++) {
			mem.set(l, nullProcess);
		}

		display();
	}

	public void addProcess(Process p, int ploc) {
		if (p.getProcessID() == Integer.MIN_VALUE) {
			// ignore request
			return;
		}
		int psize = p.getSize();
		p.setLocation(ploc);

		for (int l = ploc; l < ploc + psize; l++) {
			if (mem.get(l) != nullProcess) {
				System.err.println("Illegal process add to location " + ploc
						+ " for process " + ploc);
			}
			mem.set(l, p);
		}
		display();
	}

	private void display() {
		for (int i = 1; i <= size; i++) {
			System.out.print(mem.get(i).name());
		}
		System.out.println();
	}

	@Override
	public Iterator<Process> iterator() {
		// TODO Auto-generated method stub
		return mem.iterator();
	}
}
