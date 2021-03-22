package base;

public class CPU {

    private final int SUPERVISOR = 0;
    private final int USER = 1;
    private MMU mmu;
    private IODevice IO;

    private int MODE = 1; //(0 - supervizorius, 1 - vartotojas)

    /** Timers **/
    public int TI;
    public int PI;
    public int SI;

    /** RM register **/
    private int PTR;
    private int SMPTR;
    private int BA;
    private int BB;
    private int IC;
    private int SF;
    private int UM;

    /** I/O devices **/
    private int SB;
    private int DB;
    private int ST;
    private int DT;

    /** Commands **/
    private final int CMD_SIZE = 4;
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
    private final int REAx = 22;
    private final int WRIx = 23;

    private final int GBBx = 24;
    private final int SBBx = 25;


    public CPU(){
        IO = new IODevice();
    }

    public int parseCmd(String command){
        switch (command){
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
        switch (command.substring(0,3)){
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
            case "WRI":
                format1(command);
                return WRIx;
            case "REA":
                format1(command);
                return REAx;
        }
        switch (command.substring(0,2)){
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
        return 1000;//TODO raise exception or smth to non existing cmd
    }

    public void interpretCmd(int command){
        switch (command){
            case $STR:
                break;
            case HALT:
                break;
            case $END:
                break;
            case ADD_:
                if (BA < BB){setCMPF(2); setZF(0);}
                else if (BA == BB){setCMPF(0); setZF(0);}
                else {setCMPF(1); setZF(0);}
                BA+=BB;
                break;
            case SUB_:
                if (BA < BB){setOF(1); setCMPF(2); setZF(0) ; System.out.println("Error. The result ir smaller than zero, negative values are not implemented");}
                else if (BA == BB) {setZF(1); setCMPF(0);}
                else {setCMPF(1); setZF(0);}
                BA-=BB;
                break;
            case DIV_:
                if (BA < BB){setOF(1); setCMPF(2); setZF(0) ; System.out.println("Error. The result ir smaller than zero, negative values are not implemented");}
                else if (BA == BB) {setZF(1); setCMPF(0);}
                else {setCMPF(1); setZF(0);}
                BB = BA%BB;
                BA = BA/BB;
                break;
            case MUL_:
                if (BA < BB){setCMPF(2); setZF(0);}
                else if (BA == BB){setCMPF(0); setZF(0);}
                else {setCMPF(1); setZF(0);}
                BA*=BB;
                break;
            case CMP_:
                if (BA == BB) {setZF(1); setCMPF(0);}
                else if (BA > BB) {setCMPF(1); setZF(0);}
                else {setCMPF(2); setZF(0);}
                break;
            case GBAx:
                BA = mmu.readFromAdd(16*SMPTR+x);
                break;
            case SBAx:
                mmu.writeToAdd(16*SMPTR+x, BA);
                break;
            case GBBx:
                BB = mmu.readFromAdd(16*SMPTR+x);
                break;
            case SBBx:
                mmu.writeToAdd(16*SMPTR+x, BB);
                break;
            case LOCx:
                break;
            case UNLx:
                break;
            case WRIx:
                break;
            case REAx:
                break;
            case JExy:
                if (getFlag("ZF") == 1) {IC = 16*x+y-1;}
                break;
            case JNxy:
                if (getFlag("ZF") == 0) {IC = 16*x+y-1;}
                break;
            case JBxy:
                if (getFlag("CMPF") == 2) {IC = 16*x+y-1;}
                break;
            case JAxy:
                if (getFlag("CMPF") == 1) {IC = 16*x+y-1;}
                break;
            case JMxy:
                IC = 16*x+y-1;
                break;
            case BFxy:
                IO.setWRSize(x*16+y);
                break;
            case PDxy:
                int arrI[] = new int[IO.getWRSize()];
                for(int i = 0; i < IO.getWRSize(); i++){
                    arrI[i] = mmu.readFromAdd(x*16+y+i);
                }
                IO.writeMonitor(arrI);
                break;
            case GDxy:
                int[] arrO = IO.readKeyboard();
                for(int i = 0; i < IO.getWRSize(); i++){
                    mmu.writeToAdd(x*16+y+i, arrO[i]);
                }
                break;
            case GAxy:
                BA = mmu.readFromAdd(x*16+y);
                System.out.println(BA + " GA BA");
                break;
            case GBxy:
                BB = mmu.readFromAdd(x*16+y);
                System.out.println(BA + " GB BB");
                break;
            case SAxy:
                mmu.writeToAdd(16*x+y, BA);
                System.out.println(BA + " SA BA");
                break;
            case SBxy:
                mmu.writeToAdd(16*x+y, BB);
                System.out.println(BA + " SB BB");
                break;
        }
     //   System.out.println(command + "function");
    }

    public void format0(String command){
        if(command.length() != 4)
            System.out.println("Incorrect command: " + command);
        x = -1;
        y = -1;
    }
    public void format1(String command){
        if(command.length() != 4){
            System.out.println("Incorrect command: " + command);
            return;
        }
        x = Integer.parseInt(command.substring(3,5),16);
        y = -1;
    }
    public void format2(String command){
        if(command.length() != 4){
            System.out.println("Incorrect command: " + command);
            return;
        }
        x = Integer.parseInt(command.substring(2,3),16);
        y = Integer.parseInt (command.substring(3,4),16);
    }


    //TODO write empty functions
    public void setMODE(int MODE) {
        this.MODE = MODE;
    }

    public void setMmu(MMU mmu) {
        this.mmu = mmu;
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
/////////////////////////////////////////////////////////
    public int Test(){
        if (TI != 0 || ((PI + SI)  <= 0)  ){
            return 0;
        }
        else {return 1;}
    }

    public int getInterrupt(){
        MODE = SUPERVISOR;
        if (MODE != USER){
            while (SI !=0 && PI !=0 && TI == 0){
                switch (PI){
                    case 1:
                        System.out.println("Error: Wrong address");
                        break;
                    case 2:
                        System.out.println("Error: Wrong code of the operation");
                        break;
                    case 3:
                        System.out.println("Error: Wrong attribution");
                        break;
                    case 4:
                        System.out.println("Error: Overflow");
                        break;
                    default:
                        break;
                }
                switch (SI){
                    case 1:
                        System.out.println("GD command was called");
                        break;
                    case 2:
                        System.out.println("PD command was called");
                        break;
                    case 3:
                        System.out.println("HALT command was called");
                        break;
                    default:
                        break;
                }
                if (TI == 0){
                    System.out.println("TI occurred");
                    }
                }
                resetInterrupt();
        }
        else {System.out.println("Error: Interrupts can only be found in user regime");}
        MODE = USER;
        return 0;///
    }


    public void resetInterrupt(){
        PI = 0;
        SI = 0;
        if(TI <= 0)
            TI = 10;
    }



}


