package base;

public class CPU {

    private final int SUPERVISOR = 0;
    private final int USER = 1;

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

    private final int BFab = 11;
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

    public void parseCmd(String command){
        String cmdName = command;//TODO = grazina tik komandos pavadinima be parametru
        switch (cmdName){//TODO
            case "$STR":
                format0(command);
                break;
            case "JE":
                format2(command);
        }
    }

    public void format0(String command){
        if(command.length() != 4)
            System.out.println("Incorrect command: " + command);
    }
    public void format1(String command){
        if(command.length() != 4){
            System.out.println("Incorrect command: " + command);
            return;
        }
        x = Integer.parseInt(command.substring(3,5),16);
    }
    public void format2(String command){
        if(command.length() != 4){
            System.out.println("Incorrect command: " + command);
            return;
        }
        x = Integer.parseInt(command.substring(2,3),16);
        y = Integer.parseInt (command.substring(3,4),16);
    }

    public void ADD_(VM vm, int x, int y){
        System.out.println("ADD funkcija");
        //TODO
    }
    //TODO write empty functions
}
