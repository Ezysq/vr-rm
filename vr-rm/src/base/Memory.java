package base;

public class Memory {
    private Word data[];

    private final int BLOCKSIZE = 16; // words
    private final int userMemorySize = BLOCKSIZE * 64;
    private final int externalMemorySize = BLOCKSIZE * 64;
    private CPU cpu;

    public Memory(CPU cpu){
        this.cpu = cpu;
        data = new Word[userMemorySize + externalMemorySize];
        for (int i = 0; i < userMemorySize + externalMemorySize; i++){
            data[i] = new Word();
        }
    }

    public void write(int address, Word word){
        System.out.println(Word.wordToInt(word));
        data[address] = word.clone();
    }

    public Word read(int address){
        return data[address];
    }
}
