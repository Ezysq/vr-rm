package RM;

import Memory.MMU;
import Exception.SemaphoreException;

public class CPU {

    private final int SUPERVISOR = 0;
    private final int USER = 1;
    private MMU mmu;
    private IODevice IO;
    private Interrupt interrupt;

    private int MODE = 1; //(0 - supervizorius, 1 - vartotojas)


    /** RM register **/
    private int PTR;
    private int SMPTR;
    private int BA;
    private int BB;
    private int IC;
    private int SF;
    private int LB;

    /** Commands **/
    private int x, y; // Command parameters
    private final int $STR = 0;
    private final int HALT = 98;
    private final int $END = 99;

    private final int ADD_ = 1;
    private final int SUB_ = 2;
    private final int CMP_ = 3;
    private final int DIV_ = 4;
    private final int MUL_ = 5;

    private final int JExy = 6;
    private final int JNxy = 7;
    private final int JBxy = 8;
    private final int JAxy = 9;
    private final int JMxy = 10;

    private final int BFxy = 11;
    private final int PDxy = 12;
    private final int GDxy = 13;

    private final int GBAx = 14;
    private final int SBAx = 15;

    private final int GAxy = 16;
    private final int GBxy = 17;

    private final int SAxy = 18;
    private final int SBxy = 19;

    private final int LOCx = 20;
    private final int UNLx = 21;

    private final int GBBx = 22;
    private final int SBBx = 23;
    private final int MOVx = 24;


    public CPU(IODevice IO, Interrupt interrupt){
        this.IO = IO;
        this.interrupt = interrupt;
        LB = -1;
        SMPTR = 1088;
        MODE = USER;
    }

    public int parseCmd(String command){
        if (MODE == USER) {
            switch (command) {
                case "$STR":
                    format0(command);
                    return $STR;
                case "HALT":
                    format0(command);
                    return HALT;
                case "$END":
                    format0(command);
                    return $END;
                case "ADD_":
                    format0(command);
                    return ADD_;
                case "SUB_":
                    format0(command);
                    return SUB_;
                case "DIV_":
                    format0(command);
                    return DIV_;
                case "MUL_":
                    format0(command);
                    return MUL_;
                case "CMP_":
                    format0(command);
                    return CMP_;
            }
            switch (command.substring(0, 3)) {
                case "GBA":
                    format1(command);
                    return GBAx;
                case "SBA":
                    format1(command);
                    return SBAx;
                case "GBB":
                    format1(command);
                    return GBBx;
                case "SBB":
                    format1(command);
                    return SBBx;
                case "LOC":
                    format1(command);
                    return LOCx;
                case "UNL":
                    format1(command);
                    return UNLx;
                case "MOV":
                    format1(command);
                    return MOVx;
            }
            switch (command.substring(0, 2)) {
                case "JE":
                    format2(command);
                    return JExy;
                case "JN":
                    format2(command);
                    return JNxy;
                case "JB":
                    format2(command);
                    return JBxy;
                case "JA":
                    format2(command);
                    return JAxy;
                case "JM":
                    format2(command);
                    return JMxy;
                case "BF":
                    format2(command);
                    return BFxy;
                case "PD":
                    format2(command);
                    return PDxy;
                case "GD":
                    format2(command);
                    return GDxy;
                case "GA":
                    format2(command);
                    return GAxy;
                case "GB":
                    format2(command);
                    return GBxy;
                case "SA":
                    format2(command);
                    return SAxy;
                case "SB":
                    format2(command);
                    return SBxy;
            }
            System.out.println("Wrong operation code");
            //interrupt.setPI(2);
        }
        return 1000;//TODO raise exception or smth to non existing cmd
    }

    public void interpretCmd(int command){

        if (MODE == USER)
        switch (command){
            case $STR:
                break;
            case HALT:
                interrupt.timer(1);
                interrupt.setSI(3);
                break;
            case $END:
                break;
            case ADD_:
                if (BA < BB){setCMPF(2); setZF(0); setOF(0);}
                else if (BA == BB){setCMPF(0); setZF(0); setOF(0);}
                else {setCMPF(1); setZF(0); setOF(0);}
                BA+=BB;
                interrupt.timer(1);
            break;
            case SUB_:
                if (BA < BB){setOF(1); setCMPF(2); setZF(0); interrupt.setPI(3); }
                else if (BA == BB) {setZF(1); setCMPF(0); setOF(0);}
                else {setCMPF(1); setZF(0); setOF(0);}
                BA-=BB;
                interrupt.timer(1);
                break;
            case DIV_:
                if (BA < BB){setOF(1); setCMPF(2); setZF(0);  interrupt.setPI(3); }
                else if (BA == BB) {setZF(1); setCMPF(0); setOF(0);}
                else {setCMPF(1); setZF(0); setOF(0);}
                BB = BA%BB; // gali dalint is 0
                BA = BA/BB; // gali dalint is 0
                interrupt.timer(1);
                break;
            case MUL_:
                if (BA < BB){setCMPF(2); setZF(0); setOF(0);}
                else if (BA == BB){setCMPF(0); setZF(0); setOF(0);}
                else {setCMPF(1); setZF(0); setOF(0);}
                BA*=BB;
                interrupt.timer(1);
                break;
            case CMP_:
                if (BA == BB) {setZF(1); setCMPF(0); setOF(0);}
                else if (BA > BB) {setCMPF(1); setZF(0); setOF(0);}
                else {setCMPF(2); setZF(0); setOF(0);}
                interrupt.timer(1);
                break;
            case GBAx:
                if(LB != -1 && mmu.isLocked(LB))
                    BA = mmu.readFromSMAdd(SMPTR + 16 * LB + x);
                interrupt.timer(1);
                break;
            case SBAx:
                if(LB != -1 && mmu.isLocked(LB))
                    mmu.writeToSMAdd(SMPTR + 16 * LB + x, BA);
                interrupt.timer(1);
                break;
            case GBBx:
                if(LB != -1 && mmu.isLocked(LB))
                    BB = mmu.readFromSMAdd(SMPTR + 16 * LB + x);
                else System.out.println("Block is not locked");
                interrupt.timer(1);
                break;
            case SBBx:
                if(LB != -1 && mmu.isLocked(LB))
                    mmu.writeToSMAdd(SMPTR + 16 * LB + x, BB);
                else System.out.println("Block is not locked");
                break;
            case LOCx:
                if(LB == -1 && !mmu.isLocked(x)) {
                    LB = x;
                    mmu.lockBlock(LB);
                }
                else {
                    try {
                        throw new SemaphoreException();
                    } catch (SemaphoreException e) {
                        e.printStackTrace();
                    }
                }
                interrupt.timer(1);
                break;
            case UNLx:
                if(x == LB){
                    mmu.unlockBlock(LB);
                    LB = -1;
                }
                else {
                    try {
                        throw new SemaphoreException();
                    } catch (SemaphoreException e) {
                        e.printStackTrace();
                    }
                }
                 //this block is not locked or not locked with this VM
                interrupt.timer(1);
                break;
            case MOVx:
                BB = x;
                interrupt.timer(1);
                break;
            case JExy:
                if (getFlag("ZF") == 1) {IC = 16*x+y-1;}
                interrupt.timer(1);
                break;
            case JNxy:
                if (getFlag("ZF") == 0) {IC = 16*x+y-1;}
                interrupt.timer(1);
                break;
            case JBxy:
                if (getFlag("CMPF") == 2) {IC = 16*x+y-1;}
                interrupt.timer(1);
                break;
            case JAxy:
                if (getFlag("CMPF") == 1) {IC = 16*x+y-1;}
                interrupt.timer(1);
                break;
            case JMxy:
                IC = 16*x+y-1;
                interrupt.timer(1);
                break;
            case BFxy:
                IO.setWRSize(x*16+y);
                interrupt.timer(1);
                break;
            case PDxy:
                IO.setSO(1);
                IO.setDO(4);
                IO.setSA(x*16+y);
                interrupt.setSI(2);
                interrupt.timer(3);
                break;
            case GDxy:
                IO.setSO(4);
                IO.setDO(1);
                IO.setDA(x*16+y);
                interrupt.setSI(1);
                interrupt.timer(3);
                break;
            case GAxy:
                BA = mmu.readFromAdd(x, y);
                //System.out.println(BA + " GA BA");
                interrupt.timer(1);
                break;
            case GBxy:
                BB = mmu.readFromAdd(x, y);
               // System.out.println(BA + " GB BB"); getInterrupt();
                interrupt.timer(1);
                break;
            case SAxy:
                mmu.writeToAdd(x, y, BA);
                //System.out.println(BA + " SA BA");
                interrupt.timer(1);
                break;
            case SBxy:
                mmu.writeToAdd(x, y, BB);
                //System.out.println(BA + " SB BB");
                interrupt.timer(1);
                break;
        }
    }

    private void format0(String command){
        if(command.length() != 4)
            System.out.println("Incorrect command: " + command);
        x = -1;
        y = -1;
    }
    private void format1(String command){
        if(command.length() != 4){
            System.out.println("Incorrect command: " + command);
            return;
        }
        x = Integer.parseInt(command.substring(3,4),16);
        if(x*16 >= 256)
            interrupt.setPI(1);
        y = -1;
    }
    private void format2(String command){
        if(command.length() != 4){
            System.out.println("Incorrect command: " + command);
            return;
        }
        x = Integer.parseInt(command.substring(2,3),16);
        y = Integer.parseInt (command.substring(3,4),16);
        if(x*16 >= 256)
            interrupt.setPI(1);
    }

    public void setMmu(MMU mmu) {
        this.mmu = mmu;
        IO.setMmu(mmu);
    }

    public int getX() {
        return x;
    }

    public void setX(int x){ this.x = x; }

    public int getY() {
        return y;
    }

    public void setY(int y){ this.y = y; }

    public int get$STR() {
        return $STR;
    }

    public int get$END() {
        return $END;
    }

    public void setIC(int i){ IC = i;}

    public int getIC(){ return IC;}

    public void setPTR(int PTR) {
        this.PTR = PTR;
    }

    public void setOF(int i){
        if(i == 1) {
            if (SF < 100)
                SF += 100;
        }
        else {
            if (SF >= 100)
                SF -= 100;
        }
    }

    public void setZF(int i){
        if(SF >= 100)
            SF = 100 + i*10 + SF % 10;
        else
            SF = i*10 + SF % 10;
    }

    public void setCMPF(int i){
        SF = SF/10 * 10 + i;
    }

    public int getFlag(String flag){
        String w = String.valueOf(SF);
        while(w.length() < 3){
            w = "0" + w;
        }
        switch (flag){
            case "OF":
                return Integer.parseInt(w.substring(0,1));
            case "ZF":
                return Integer.parseInt(w.substring(1,2));
            case "CMPF":
                return Integer.parseInt(w.substring(2,3));
            default:
                System.out.println("Error flag not found");
                return -1;
        }
    }

    public void printRegisters(){
        System.out.println("PTR: "  + PTR + " SMPTR: " + SMPTR + " BA: " + BA + " BB: " + BB + " IC: " + IC + " SF: " + SF + " LB: " + LB);
        IO.getStatus();
    }

    public void setMODE(int user) {
        MODE = user;
    }

    public int[] getRegisters() {
        int arr[] = {MODE, PTR, SMPTR, BA, BB, IC, SF, LB};
        return arr;
    }
    public void loadRegisters(int[] arr) {
        MODE = arr[0];
        PTR = arr[1];
        SMPTR = arr[2];
        BA = arr[3];
        BB = arr[4];
        IC = arr[5];
        SF = arr[6];
        LB = arr[7];
    }
}