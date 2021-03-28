package RM;
import Memory.MMU;
import RM.RM;

import java.util.Scanner;

public class IODevice {

    private int SA; // Source address
    private int DA; // Destination address
    private int SO; // Source object
    private int DO; // Destination object
    private MMU mmu;

    Scanner reader = new Scanner(System.in);
    private int WRSize = 0;

    public IODevice(){
        this.SA = -1;
        this.DA = -1;
        this.SO = -1;
        this.DO = -1;
    }

    public int[] readKeyboard(){
        System.out.println("Ivesti " + WRSize + " skaicius:");
        int arr[] = new int[WRSize];
        int i = 0;
        while(WRSize > i){
            arr[i] = reader.nextInt();
            i++;
        }
        return arr;
    }

    public void writeMonitor(int arr[]){
        System.out.print("Atsakymas: ");
        for(int i: arr){
            System.out.print(i + " ");
            WRSize--;
            if(WRSize == 0) break;
        }
        System.out.println();
    }

    public void execute() {
        int arr[] = new int[getWRSize()];
        switch (SO){
            case 1:
                for (int i = 0; i < getWRSize(); i++) {
                    arr[i] = mmu.readFromAdd(SA + i);
                }
                break;
            case 4:
                arr = readKeyboard();
                break;
            default:
                return ; // TODO error
        }
        switch (DO){
            case 1:
                for (int i = 0; i < getWRSize(); i++) {
                    mmu.writeToAdd(DA + i, arr[i]);
                }
                break;
            case 4:
                writeMonitor(arr);
                break;
            default:
                return ; // TODO error
        }
    }

    public void getStatus(){
        System.out.println("SO: " + SO + " DO: " + DO + " SA: " + SA + " DA: " + DA);
    }

    public void setMmu(MMU mmu) {
        this.mmu = mmu;
    }

    public void setWRSize(int WRSize) {
        this.WRSize = WRSize;
    }

    public int getWRSize(){
        return WRSize;
    }

    public int getSA() {
        return SA;
    }

    public int getDA() {
        return DA;
    }

    public int getSO() {
        return SO;
    }

    public int getDO() {
        return DO;
    }

    public void setSA(int SA) {
        this.SA = SA;
    }

    public void setDA(int DA) {
        this.DA = DA;
    }

    public void setSO(int SO) {
        this.SO = SO;
    }

    public void setDO(int DO) {
        this.DO = DO;
    }
}
