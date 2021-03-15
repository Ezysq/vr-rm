package base;

public class Memory {
    private Word data[];

    private final int BLOCKSIZE = 16; // words
    private final int userMemorySize = BLOCKSIZE * 64;
    private final int externalMemorySize = BLOCKSIZE * 64;
    private CPU cpu;

    public Memory(CPU cpu){
        this.cpu = cpu;
    }
}
