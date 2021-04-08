package Memory;
import java.util.Random;

public class MemoryAllocationTable {
    public final int PAGESIZE; // words
    public final int USERMEMORY; // words
    Random rand = new Random();

    private Memory memory;
    private int memoryTable[];

    public MemoryAllocationTable(Memory memory){
        this.memory = memory;
        PAGESIZE = memory.getPAGESIZE();
        USERMEMORY = memory.getUSERMEMORYSIZE();
        memoryTable = new int[PAGESIZE];
        for(int i=0; i<PAGESIZE; i++) {
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
        for(int i=0; i<PAGESIZE; i++) {
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