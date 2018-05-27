package rothkoff.baruch;

import java.util.stream.Stream;

public class MemoryTree {
    private MemoryTree left;
    private MemoryTree right;
    private int size;
    private int startAddress;
    private int endAddress;
    private Process process;
    private boolean isChildBusy;

    public MemoryTree(int power) {
        this(power, 0);
    }

    private MemoryTree(int power, int startAddress) {
        this.size = (int) Math.pow(2, power);
        this.startAddress = startAddress;
        this.endAddress = startAddress + size - 1;
        isChildBusy = false;
        if (--power > 0) {
            this.left = new MemoryTree(power, startAddress);
            this.right = new MemoryTree(power, startAddress + power);
        }
    }

    public boolean allocate(Process process) {
        if (process.getSize() < this.size / 2) {
            if (left.allocate(process)) {
                isChildBusy = true;
                return true;
            }
            if (right.allocate(process)) {
                isChildBusy = true;
                return true;
            }
            return false;
        }
        if (process.getSize() > this.size)
            return false;
        if (this.process == null && !isChildBusy) {
            this.process = process;
            process.setMemory(this);
            return true;
        }
        return false;
    }

    public Stream<MemoryTree> stream() {
        if (this.isChildBusy) {
            return Stream.concat(left.stream(), right.stream());
        }
        return Stream.of(this);
    }

    @Override
    public String toString() {
        return "MemoryTree{" +
                "startAddress=" + startAddress +
                ", endAddress=" + endAddress +
                '}';
    }
}
