package base;

public class Semaphore{
    private int lockedBlocks[];

    public Semaphore(){
        lockedBlocks = new int[12];
    }

    public void lockBlock(int x){
        if(lockedBlocks[x] == 0)
            lockedBlocks[x]++;
    }

    public void unlockBlock(int x){
        lockedBlocks[x]--;
    }

    public boolean isLocked(int x){
        return lockedBlocks[x] != 0;
    }

}
