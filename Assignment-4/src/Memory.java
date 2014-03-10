import java.util.ArrayList;

public class Memory {
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

		for (int i = 1; i <= size; i++) {
			mem.add(i, nullProcess);
		}
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
		int psize;
		if (p.getProcessID() == Integer.MIN_VALUE) {
			// ignore request
			return;
		}
		psize = p.getSize();
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
}
