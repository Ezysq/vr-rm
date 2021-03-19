package base;

public class MMU {
    public final static int BLOCKSIZE = 16*16; // words
    private CPU cpu;
    private Memory memory;
    private RM rm;

    public MMU(CPU cpu, RM rm){
        this.cpu = cpu;
        this.rm = rm;
        this.memory = new Memory(cpu);
    }

    public void saveCommand(int add, String cmd){
        int commandKey = cpu.parseCmd(cmd);
        Word word = new Word();
        word.setByte(0, (byte)commandKey);
        if(cpu.getX() != -1){
            word.setByte(1, (byte)cpu.getX());
        }
        if(cpu.getY() != -1){
            word.setByte(2, (byte)cpu.getY());
        }
        memory.write(add, word);
    }

    public int loadCommand(int add){
        cpu.setX(memory.read(add).getByte(1));
        cpu.setY(memory.read(add).getByte(2));
        return memory.read(add).getByte(0);
    }

    public int readFromAdd(int address){
        return Word.wordToInt(memory.read(address));
    }

    public void writeToAdd(int address, int word){
        memory.write(address, Word.intToWord(word));
    }

    public void test(){
        for(int i=0; i < 16; i++){
            System.out.println("test: " + Word.wordToInt(memory.read(i)));
            System.out.println("test2: " + (int)memory.read(i).getByte(0) + (int)memory.read(i).getByte(1) + (int)memory.read(i).getByte(2));

        }
    }

}
