package base;

public class CPU {

    private final int SUPERVISOR = 0;
    private final int USER = 1;
    private MMU mmu;

    private int MODE = 1; //(0 - supervizorius, 1 - vartotojas)

    /** Timers **/
    private int TI;
    private int PI;
    private int SI;

    /** RM register **/
    private int PTR;
    private int SPTR;
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
    private final int HALT = 99;
    private final int $END = 100;

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

    public CPU(){

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
                BA+=BB;
                break;
            case SUB_:
                BA-=BB;
                break;
            case DIV_:
                BB = BA%BB;
                BA = BA/BB;
                break;
            case MUL_:
                BA*=BB;
                break;
            case CMP_:

                break;
            case GBAx:
                break;
            case SBAx:
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
                break;
            case JNxy:
                break;
            case JBxy:
                break;
            case JAxy:
                break;
            case JMxy:
                break;
            case BFxy:
                break;
            case PDxy:
                break;
            case GDxy:
                break;
            case GAxy:
                break;
            case GBxy:
                break;
            case SAxy:
                break;
            case SBxy:
                break;
        }
        IC++;
        System.out.println(command + "function");
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

    public int getY() {
        return y;
    }
}
