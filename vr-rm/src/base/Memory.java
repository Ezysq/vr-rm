package base;

public class Memory {
    private Word memory[];

    private final int BLOCKSIZE = 16; // words
    private final int userMemorySize = BLOCKSIZE * 64;
    private final int externalMemorySize = BLOCKSIZE * 64;
}
