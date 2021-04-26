package VM;

public class VM {
    private int ID;

    private final int SUPERVISOR = 0;
    private final int USER = 1;
    private int BA;
    private int BB;
    private int IC;

    public VM(int ID){
        this.ID = ID;
    }

    public int getBA() {
        return BA;
    }

    public void setBA(int BA) {
        this.BA = BA;
    }

    public int getBB() {
        return BB;
    }

    public void setBB(int BB) {
        this.BB = BB;
    }

    public int getIC() {
        return IC;
    }

    public void setIC(int IC) {
        this.IC = IC;
    }
}

