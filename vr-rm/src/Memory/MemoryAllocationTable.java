package Memory;
import java.util.Random;

public class MemoryAllocationTable {
    public final int BLOCKSIZE = 16*16; // words
    public final int USERMEMORY = 4 * BLOCKSIZE; // words
    Random rand = new Random();

    private Memory memory;
    private int memoryTable[];

    public MemoryAllocationTable(Memory memory){
        this.memory = memory;
        memoryTable = new int[BLOCKSIZE];
        for(int i=0; i<BLOCKSIZE; i++) {
            int newAdd = rand.nextInt(USERMEMORY);
            if (memory.isEmpty(newAdd)) {
                memoryTable[i] = newAdd;
                memory.setIsUsed(newAdd);
            }
            else i--;
        }
    }

    public int getRealAddress(int address){
        return memoryTable[address];
    }

    public void test(){
        for(int i=0; i<BLOCKSIZE; i++) {
            System.out.println(i + " " + memoryTable[i]);
        }
    }

    public void printVMMemory() {
        int j = 0;
        for(int i:memoryTable){
            System.out.print(j++ + ":" + Word.wordToInt(memory.read(i)) + " ");
        }
        System.out.println();
    }
}
// Load 1.txt