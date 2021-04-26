package Memory;

public class Memory {
    private Word data[];
    private boolean isUsed[];

    private final int BLOCKSIZE = 16; // words
    private final int PAGESIZE = BLOCKSIZE * 16; // words
    private final int USERMEMORYSIZE = PAGESIZE * 4;

    public Memory(){
        data = new Word[USERMEMORYSIZE + PAGESIZE];
        isUsed = new boolean[4 * BLOCKSIZE];
        for (int i = 0; i < USERMEMORYSIZE + PAGESIZE ; i++){
            data[i] = new Word();
        }
    }

    public void write(int address, Word word){
       // System.out.println(Word.wordToInt(word));
        data[address] = word.clone();
    }

    public Word read(int address) {
        return data[address];
    }

    public boolean isEmpty(int address){
        return !isUsed[address];
    }

    public void setIsUsed(int address){
        isUsed[address] = true;
    }

    public void printMemory() {
        int j=0;
        for(Word word:data){
            System.out.print(Integer.toHexString(j++).toUpperCase() + ":" + Word.wordToInt(word) + " ");
        }
        System.out.println();
    }

    public int getBLOCKSIZE() {
        return BLOCKSIZE;
    }

    public int getPAGESIZE() {
        return PAGESIZE;
    }

    public int getUSERMEMORYSIZE() {
        return USERMEMORYSIZE;
    }
}
