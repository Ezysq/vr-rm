package RM;

public class Interrupt {
    private int MODE = 1; //(0 - supervizorius, 1 - vartotojas)
    private final int SUPERVISOR = 0;
    private final int USER = 1;
    private int TI;
    private int PI;
    private int SI;

    private IODevice IO;

    public Interrupt(IODevice IO){
        this.IO = IO;
        TI = 20;
        resetInterrupt();
    }

    public void setSI(int i){ SI = i; }

    public int getSI(){ return SI;}

    public void setPI(int i){ PI = i; }

    public int getPI(){ return PI;}

    public void timer(int i){
        TI-=i;
    }

    public int checkInterrupt(){
        if (TI > 0 && ((PI + SI)  <= 0)  ){
            return 0;
        }
        return getInterrupt();
    }

    public int getInterrupt(){
        MODE = SUPERVISOR;
        int rez = 0;
        switch (PI){
            case 1:
                System.out.println("Error: Wrong address");
                rez = 1;
            case 2:
                System.out.println("Error: Wrong code of the operation");
                rez = 1;
            case 3:
                System.out.println("Error: Overflow");
                System.out.println("Error. The result ir smaller than zero, negative values are not implemented");
                rez = 1;
        }
        switch (SI){
            case 1:
                IO.execute();
                System.out.println("GD command was called");
                break;
            case 2:
                IO.execute();
                System.out.println("PD command was called");
                break;
            case 3:
                System.out.println("HALT command was called");
                rez = 1;
        }
        if (TI <= 0){
            System.out.println("TI occurred");
            rez = 1;
        }
        resetInterrupt();
        MODE = USER;
        return rez;
    }

    public void resetInterrupt(){
        PI = 0;
        SI = 0;
    }
    public void resetTimer(){
        TI = 20;
    }
}
