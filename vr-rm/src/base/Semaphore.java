package base;

public class Semaphore{
    private boolean lockedBlocks[];

    public Semaphore(){
        lockedBlocks = new boolean[16];
    }

    public void lockBlock(int x){
        lockedBlocks[x] = true;
    }

    public void unlockBlock(int x){
        lockedBlocks[x] = false;
    }

    public boolean isLocked(int x){
        return lockedBlocks[x];
    }

  /*  public void test(){
        for(boolean i: lockedBlocks){
            System.out.print(i + " ");
        }
        System.out.println();
    }*/

}
