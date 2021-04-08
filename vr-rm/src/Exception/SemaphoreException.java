package Exception;

public class SemaphoreException extends Exception {
    public SemaphoreException(){

    }

    public SemaphoreException(String errMsg){
        super(errMsg);
    }
}
