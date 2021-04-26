package Memory;
import java.util.Random;

public class MemoryAllocationTable {
    public final int PAGESIZE; // words
    public final int USERMEMORY; // words
    Random rand = new Random();

    private Memory memory;
    private int ptr;

    public MemoryAllocationTable(Memory memory){
        this.memory = memory;
        PAGESIZE = memory.getPAGESIZE();
        USERMEMORY = memory.getUSERMEMORYSIZE();
    }

    public void create() {
        int[] memoryTable = new int[memory.getBLOCKSIZE()];
        for(int i=0; i<memory.getBLOCKSIZE(); i++) {
            int newAdd = rand.nextInt(4 * memory.getBLOCKSIZE());
            if (memory.isEmpty(newAdd)) {
                memoryTable[i] = newAdd;
                memory.setIsUsed(newAdd);
            }
            else i--;
        }
        ptr = memoryTable[0]*16;
        for(int i=0; i< memory.getBLOCKSIZE(); i++) {
            memory.write(ptr + i, Word.intToWord(memoryTable[i]));
        }
    }

    public int getRealAddress(int x, int y){
        return Word.wordToInt(memory.read(ptr + x)) *16 + y;
    }

    public void printVMMemory() {
        int j = 0;
        for(int i=0; i< 16; i++){
            for(int y=0; y< 16; y++){
                System.out.print(Integer.toHexString(j++).toUpperCase() + ":" + Word.wordToInt(memory.read( Word.wordToInt(memory.read(ptr + i)) * 16 + y)) + " ");
            }
        }
        System.out.println();
    }

    public int getPtr() {
        return ptr;
    }

    public void setPtr(int ptr) {
        this.ptr = ptr;
    }
}
