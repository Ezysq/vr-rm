package base;

public class MMU {
    public final static int BLOCKSIZE = 16*16; // words
    private CPU cpu;
    private Memory memory;
    private RM rm;

    public MMU(CPU cpu, RM rmn){
        this.cpu = cpu;
        this.rm = rmn;
        memory = new Memory(cpu);
    }

    public void saveComand(int add, String cmd){
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

    public void test(){
        for(int i=0; i < 16; i++){
            System.out.println(Word.wordToInt(memory.read(i)));
        }
    }

}
