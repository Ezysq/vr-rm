package Memory;

import RM.CPU;
import RM.RM;
import base.Semaphore;

public class MMU {
    private final int MAXVM = 4;
    private CPU cpu;
    private Memory memory;
    private RM rm;
    private MemoryAllocationTable userTable;
    private Semaphore semaphore;

    public MMU(CPU cpu, RM rm){
        this.cpu = cpu;
        this.rm = rm;
        this.memory = new Memory();
        userTable = new MemoryAllocationTable(memory);
        this.semaphore = new Semaphore();
    }

    public void saveCommand(int x, int y, String cmd){ // turetu buti kanalu irengini
        int commandKey = cpu.parseCmd(cmd);
        Word word = new Word();
        word.setByte(0, (byte)commandKey);
        if(cpu.getX() != -1){
            word.setByte(1, (byte)cpu.getX());
        }
        if(cpu.getY() != -1){
            word.setByte(2, (byte)cpu.getY());
        }
        memory.write(userTable.getRealAddress(x, y), word);
    }

    public int loadCommand(int x, int y){
        cpu.setX(memory.read(userTable.getRealAddress(x, y)).getByte(1));
        cpu.setY(memory.read(userTable.getRealAddress(x, y)).getByte(2));
        return memory.read(userTable.getRealAddress(x, y)).getByte(0);
    }

    public int readFromAdd(int x, int y){
        return Word.wordToInt(memory.read(userTable.getRealAddress(x, y)));
    }

    public void writeToAdd(int x, int y, int word){
        memory.write(userTable.getRealAddress(x, y), Word.intToWord(word));
    }

    public int readFromSMAdd(int address){
        return Word.wordToInt(memory.read(address));
    }

    public void writeToSMAdd(int address, int word){
        memory.write(address, Word.intToWord(word));
    }

    public void newVMTable() {
        userTable.create();
        System.out.println("newVMTable created");
        cpu.setPTR(userTable.getPtr());
    }

    public void printMemory() {
        memory.printMemory();
    }

    public void printVMMemory() {
        userTable.printVMMemory();
    }

    public int getBLOCKSIZE() { return memory.getBLOCKSIZE(); }

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

    public void saveCPUStates(int VMn){
        int add = 1024 + VMn * 16, j=0;
        int registers[] = cpu.getRegisters();
        for(int i:registers){
            memory.write(add + j++, Word.intToWord(i));
        }
    }

    public void loadCPUStates(int VMn){
        int add = 1024 + VMn * 16;
        int registers[] = new int[8];
        for(int i=0; i < 8; i++){
            registers[i] = Word.wordToInt(memory.read(add+i));
            if(i == 1)
                userTable.setPtr(registers[i]);
        }
        cpu.loadRegisters(registers);
    }

}
