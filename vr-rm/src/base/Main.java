package base;

import RM.RM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        RM rm = new RM();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("Command list: *Load* *Select* *Run* *Debug* *Print memory* *Print VM memory* *Quit*");
            try{
                String arg = reader.readLine();
                System.out.println(arg);
                if(arg.startsWith("Load")){
                    rm.loadProgram();
                }
                else if(arg.startsWith("Select")){
                    int temp = Integer.parseInt(arg.substring(7));
                    if(rm.VMExists(temp))
                        rm.selectProgram(temp);
                }
                else if(arg.startsWith("Run")){
                    if(rm.VMsExists())
                        rm.executeProgram();
                }
                else if(arg.startsWith("Debug")){
                    if(rm.VMsExists())
                        rm.debugProgram();
                }
                else if(arg.startsWith("Print memory")){
                    if(rm.VMsExists())
                        rm.printMemory();
                }
                else if(arg.startsWith("Print VM memory")){
                    if(rm.VMsExists())
                        rm.printVMMemory();
                }
                else if (arg.startsWith("Quit")){
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

