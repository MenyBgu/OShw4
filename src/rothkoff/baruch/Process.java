package rothkoff.baruch;

public class Process {
    private final String id;
    private final int size;
    private MemoryTree memory;

    public Process(String id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setMemory(MemoryTree memory) {
        this.memory = memory;
    }

    public MemoryTree getMemory() {
        return memory;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id='" + id + '\'' +
                ", size=" + size +
                ", memory=" + memory +
                '}';
    }

    public String getId() {
        return id;
    }

    public void release() {
        memory.free();
    }

    public int getInternalFragmentation() {
        return this.memory.getSize()-this.size;
    }
}
