package Memory;

import RM.CPU;

public class Memory {
    private Word data[];
    private boolean isUsed[];

    private final int BLOCKSIZE = 16; // words
    private final int userMemorySize = BLOCKSIZE * 64;
    private final int externalMemorySize = BLOCKSIZE * 64;
   //private CPU cpu;

    public Memory(/*CPU cpu*/){
        //this.cpu = cpu;
        data = new Word[userMemorySize];
        isUsed = new boolean[userMemorySize];
        for (int i = 0; i < userMemorySize ; i++){
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
        int j = 0;
        for(Word word:data){
            System.out.print(j++ + ":" + Word.wordToInt(word) + " ");
        }
    }
}
