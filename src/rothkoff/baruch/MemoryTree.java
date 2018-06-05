package rothkoff.baruch;

import java.util.stream.Stream;

public class MemoryTree {
    private MemoryTree left;
    private MemoryTree right;
    private int size;
    private int startAddress;
    private int endAddress;
    private int allocated;

    public MemoryTree(int size) {
        this(size, 0);
    }

    private MemoryTree(int size, int startAddress) {
        this.size = size;
        this.startAddress = startAddress;
        this.endAddress = startAddress + this.size - 1;
        allocated = 0;
        if (this.size > 2) {
            this.left = new MemoryTree(this.size/2, startAddress);
            this.right = new MemoryTree(this.size/2, startAddress + this.size/2);
        }
    }

    public boolean allocate(Process process) {
        if (this.size-this.allocated<process.getSize()) return false;
        if (process.getSize() > size / 2) {   // my size is the best option, no go down
            if (allocated > 0) return false;
            process.setMemory(this);
            allocated = this.size;
            return true;
        }
        if (left.allocate(process)) {
            updateAllocated();
            return true;
        }
        if (right.allocate(process)) {
            updateAllocated();
            return true;
        }
        return false;
    }

    private void updateAllocated() {
        if(this.left!=null){
            this.allocated = left.allocated+right.allocated;
        }
    }

    @Override
    public String toString() {
        return "MemoryTree{" +
                "startAddress=" + startAddress +
                ", endAddress=" + endAddress +
                '}';
    }

    public int externalFragmentation() {
        return this.size-this.allocated;
    }

    public int getSize() {
        return size;
    }
}
