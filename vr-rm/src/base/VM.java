package base;

public class VM {
    private int ID;

    private final int SUPERVISOR = 0;
    private final int USER = 1;

    public VM(int ID) {
        this.ID = ID;
    }

    /*public int saveComand(String command, int add){
        int commandKey = cpu.findCmd(command);
        int parameters = cpu.parseCmd(command, commandKey);
        int add2 = 0;
        if (add >= 16){
            add2 = add / 16;
            add = add % 16;
        }
        memory.writeToMemory(new Word().intToWord(commandKey), add2, add, USER);
        if(parameters == 1){
            add++;
            if (add >= 16){
                add2 = add2 + add / 16;
                add = add % 16;
            }
            //System.out.println("x = " + cpu.getX());
            //cpu.setIC(cpu.getIC() + 1);
            memory.writeToMemory(Word.intToWord(cpu.getX()), add2, add, USER);
            return 2;
        }
        else if(parameters == 2){
            add++;
            if (add >= 16){
                add2 = add2 + add / 16;
                add = add % 16;
            }
            //cpu.setIC(cpu.getIC() + 1);
            //System.out.println("Writting to: " + add2 + ", " + add);
            memory.writeToMemory(Word.intToWord(cpu.getX1()), add2, add, USER);
            add++;
            if (add >= 16){
                add2 = add2 + add / 16;
                add = add % 16;
            }
            //cpu.setIC(cpu.getIC() + 1);
            //System.out.println("Writting to: " + add2 + ", " + add);
            memory.writeToMemory(Word.intToWord(cpu.getX2()), add2, add, USER);
            //System.out.println("x1 = " + cpu.getX1() + " x2 = " + cpu.getX2());
            return 3;
        }
        return 1;
    }*/
}
