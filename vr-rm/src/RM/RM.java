package RM;

import Memory.MMU;
import VM.VM;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RM {
    private CPU cpu;
    private MMU mmu;
    private Interrupt interrupt;
    private IODevice IO;

    private final int SUPERVISOR = 0;
    private final int USER = 1;
    private final int MAXVM = 4;
    private VM VMs[];
    private boolean VMsb[];
    private int workingVMs;
    public int currVM = -1;

    public RM() {
        IO = new IODevice();
        interrupt = new Interrupt(IO);
        cpu = new CPU(IO, interrupt);
        mmu = new MMU(cpu, this);
        cpu.setMmu(mmu);
        workingVMs = 0;
        VMs = new VM[4];
        VMsb = new boolean[4];
    }

    public void loadProgram(){
        boolean start = false;
        int wCount= 16;
        cpu.setMODE(USER);
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("E:\\GIT\\vr-rm\\1.txt"));
            while(fileReader.ready()){
                String currentLine = fileReader.readLine();
                if(currentLine.isEmpty()){
                    continue;
                }
                if(!start) {
                    if (currentLine.equals("$STR")) {
                        start = true;
                        if(workingVMs == MAXVM) {
                            System.out.println("Visos VM sukurtos");
                            return;
                        }
                        System.out.println("kuriama nuaja VM");
                        for(int i = 0; i<MAXVM; i++){
                            if(!VMsb[i]) {
                                currVM = i;
                                VMsb[i] = true;
                                break;
                            }
                        }
                        VMs[currVM] = new VM(currVM); // new VM(memory, cpu, (workingVMs));
                        mmu.newVMTable();
                        workingVMs++;
                        wCount= 16;

                    } else continue;
                }
                if(currentLine.equals("$END")) {
                    mmu.saveCommand(wCount / 16, wCount %16, currentLine);
                    start = false;
                    mmu.saveCPUStates(currVM);
                    continue;
                }
                mmu.saveCommand(wCount / 16, wCount %16, currentLine);
                wCount++;
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.println("BufferedReader exception.");
            e.printStackTrace();
        }
    }

    public void executeProgram(){
        System.out.println("currvm: " + currVM);
        System.out.println("Executing");
        cpu.setMODE(USER);
        cpu.setIC(16);
        int command = mmu.loadCommand(cpu.getIC()/16, cpu.getIC()%16);
        while(command != cpu.get$STR()){
            cpu.setIC(cpu.getIC() +1);
            command = mmu.loadCommand(cpu.getIC()/16, cpu.getIC()%16);
        }
        while(command != cpu.get$END()){
            cpu.interpretCmd(command);
            if(interrupt.checkInterrupt() == 1){
                break;
            }
            cpu.setIC(cpu.getIC() +1);
            command = mmu.loadCommand(cpu.getIC()/16, cpu.getIC()%16);
        }
    }

    public void debugProgram(){
        System.out.println("Debugging");
        cpu.setIC(16);
        int command = mmu.loadCommand(cpu.getIC()/16, cpu.getIC()%16);
        while(command != cpu.get$STR()){
            cpu.setIC(cpu.getIC() +1);
            command = mmu.loadCommand(cpu.getIC()/16, cpu.getIC()%16);
        }
        while(command != cpu.get$END()){
            cpu.printRegisters();
            System.out.println("cmd: " + command);
            printVMMemory();
            printMemory();
            cpu.interpretCmd(command);
            if(interrupt.checkInterrupt() == 1){
                break;
            }
            try {
                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cpu.setIC(cpu.getIC() +1);
            command = mmu.loadCommand(cpu.getIC()/16, cpu.getIC()%16);
        }
    }

    public void printMemory() {
        mmu.printMemory();
    }

    public void printVMMemory() {
        mmu.printVMMemory();
    }

    public boolean VMsExists(){
        return workingVMs != 0;
    }

    public boolean VMExists(int i){
        if(i >=0 && i < 4)
            return  VMsb[i];
        return false;
    }

    public void selectProgram(int temp) {
        currVM = temp;
        mmu.loadCPUStates(currVM);
        interrupt.resetTimer();
    }
}
