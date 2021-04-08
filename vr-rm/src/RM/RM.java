package RM;

import Memory.MMU;
import VM.VM;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RM {
    private CPU cpu;
    //private Memory memory;
    private MMU mmu;

    private final int SUPERVISOR = 0;
    private final int USER = 1;
    private final int MAXVM = 4;
    private VM VMs[];
    private boolean VMsb[];
    private int workingVMs;
    public int currVM = -1;

    public RM() {
        cpu = new CPU();
        mmu = new MMU(cpu, this);
        cpu.setMmu(mmu);
        workingVMs = 0;
        VMs = new VM[4];
        VMsb = new boolean[4];
    }

    public int loadProgram(String fileName){
        if(workingVMs == MAXVM) {
            System.out.println("Visos VM sukurtos");
            return -1;
        }
        for(int i = 0; i<MAXVM; i++){
            if(!VMsb[i]) {
                currVM = i;
                VMsb[i] = true;
                break;
            }
        }
        VM virtualMachine = new VM(currVM); // new VM(memory, cpu, (workingVMs));
        mmu.newVMTable(currVM);
        workingVMs++;
        int wCount= 0;
        boolean start = false;
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            //cpu.setIC(0);
            while(fileReader.ready()){
             //   cpu.setMODE(USER);
                String currentLine = fileReader.readLine();
                if(currentLine.isEmpty()){
                    continue;
                }
                if(currentLine.equals("$STR") || start){
                    start = true;
                }
                else continue;
                if(currentLine.equals("$END")) {
                    mmu.saveCommand(wCount, currentLine);
                    break;
                }
                //System.out.println(currentLine);
                mmu.saveCommand(wCount, currentLine);
                wCount++;
                //processInterrupt();
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.println("BufferedReader exception.");
            e.printStackTrace();
        }
        //mmu.test();
        //printVMMemory();
     //   virtualMachine.excecuteCommand();
       // cpu.setMODE(SUPERVISOR);
        //printVMMemory();
        //System.out.println("VMS: " + workingVMs + " curr: " + currVM);
        return 0;
    }

    public void executeProgram(){
        System.out.println("currvm: " + currVM);
        System.out.println("Executing");
        //cpu.setMODE(USER);
        cpu.setIC(0);
        int command = mmu.loadCommand(cpu.getIC());
        //int temp = cpu.getIC();
        //System.out.println("Testing IC:" + temp);
        while(command != cpu.get$STR()){
            cpu.setIC(cpu.getIC() +1);
            command = mmu.loadCommand(cpu.getIC());
        }
        while(command != cpu.get$END()){
            cpu.interpretCmd(command);
            cpu.setIC(cpu.getIC() +1);
            command = mmu.loadCommand(cpu.getIC());
        }
    }

    public void debugProgram(){
        System.out.println("Debugging");
        //cpu.setMODE(USER);
        cpu.setIC(0);
        int command = mmu.loadCommand(cpu.getIC());
        //int temp = cpu.getIC();
        //System.out.println("Testing IC:" + temp);
        while(command != cpu.get$STR()){
            cpu.setIC(cpu.getIC() +1);
            command = mmu.loadCommand(cpu.getIC());
        }
        while(command != cpu.get$END()){
            cpu.printRegisters();
            System.out.println("cmd: " + command);
            printVMMemory();
            cpu.interpretCmd(command);
            try {
                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cpu.setIC(cpu.getIC() +1);
            command = mmu.loadCommand(cpu.getIC());
        }
    }

    public void printMemory() {
        mmu.printMemory();
    }

    public void printVMMemory() {
        mmu.printVMMemory();
    }

    public boolean VMExists(){
        return workingVMs != 0;
    }
}
