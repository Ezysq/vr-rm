package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RM {
    private CPU cpu;
    //private Memory memory;
    private MMU mmu;

    private final int SUPERVISOR = 0;
    private final int USER = 1;
    private VM VMs[];
    private int workingVMs;

    public RM() {
        cpu = new CPU();
        mmu = new MMU(cpu, this);
        cpu.setMmu(mmu);
        workingVMs = 0;
    }

    public void loadProgram(String fileName){
      /*  if(workingVMs == 4)
            return;
        int currVm = 0;
        for(int i = 0; i<4; i++){
            if(VMs[i] == null){//TODO check if that makes sense
                currVm = i;
                break;
            }
        }*/ //sitoj dali kaip dar ir neriekia
        VM virtualMachine = new VM(0); // new VM(memory, cpu, (workingVMs));
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
       // mmu.test();
        //printVMMemory();
     //   virtualMachine.excecuteCommand();
       // cpu.setMODE(SUPERVISOR);
        //printVMMemory();
    }

    public void executeCommand(){
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
}
