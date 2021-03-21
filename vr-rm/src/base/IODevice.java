package base;
import java.util.Scanner;

public class IODevice {

    Scanner reader = new Scanner(System.in);
    private int WRSize = 0;

    public IODevice(){

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

    public void setWRSize(int WRSize) {
        this.WRSize = WRSize;
    }

    public int getWRSize(){
        return WRSize;
    }
}
