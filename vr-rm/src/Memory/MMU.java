package Memory;

import RM.CPU;
import RM.RM;
import base.Semaphore;

public class MMU {
    private final int MAXVM = 4;
    private CPU cpu;
    private Memory memory;
    private RM rm; // nenaudojamas gal reikia istrint
    private MemoryAllocationTable userTable[];
    private Semaphore semaphore;

    private int VMindex = -1;

    public MMU(CPU cpu, RM rm){
        this.cpu = cpu;
        this.rm = rm;
        this.memory = new Memory(/*cpu*/);
        userTable = new MemoryAllocationTable[MAXVM];
        this.semaphore = new Semaphore();
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
        memory.write(userTable[VMindex].getRealAddress(add), word); // CIAAAAAAAAAAAAAAAAAAAAAAA
    }

    public int loadCommand(int add){
        cpu.setX(memory.read(userTable[VMindex].getRealAddress(add)).getByte(1));
        cpu.setY(memory.read(userTable[VMindex].getRealAddress(add)).getByte(2));
        return memory.read(userTable[VMindex].getRealAddress(add)).getByte(0);
    }

    public int readFromAdd(int address){
        return Word.wordToInt(memory.read(userTable[VMindex].getRealAddress(address)));
    }

    public void writeToAdd(int address, int word){
        memory.write(userTable[VMindex].getRealAddress(address), Word.intToWord(word));
    }

    public int readFromSMAdd(int address){
        return Word.wordToInt(memory.read(address));
    }

    public void writeToSMAdd(int address, int word){
        memory.write(address, Word.intToWord(word));
    }

   /* public void test(){
        semaphore.test();
    }*/

    public void newVMTable(int newVM) {
        userTable[newVM] = new MemoryAllocationTable(memory);
        VMindex = newVM;
        System.out.println("newVMTable created");
    }

    public void setVMindex(int VMindex) {
        this.VMindex = VMindex;
    }

    public void isSMLocked(int x){

    }

    public void printMemory() {
        memory.printMemory();
    }

    public void printVMMemory() {
        userTable[rm.currVM].printVMMemory();
    }

    public int getBLOCKSIZE() {
        return memory.getBLOCKSIZE();
    }

    public int getPAGESIZE() {
        return memory.getPAGESIZE();
    }

    public int getUSERMEMORYSIZE() {
        return memory.getUSERMEMORYSIZE();
    }

    public boolean isLocked(int x) {
        return semaphore.isLocked(x);
    }

    public void lockBlock(int x) {
        semaphore.lockBlock(x);
    }

    public void unlockBlock(int x) {
        semaphore.unlockBlock(x);
    }
}
