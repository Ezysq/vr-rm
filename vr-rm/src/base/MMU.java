package base;

public class MMU {
    public final static int BLOCKSIZE = 16*16; // words
    private CPU cpu;
    private Memory memory;
    private RM rm;

    public MMU(CPU cpu, RM rm){
        cpu = cpu;
        rm = rm;
        memory = new Memory(cpu);
    }

    public void saveComand(int index){

    }

}
