package base;

public class Word {
    public static final int SIZE = 4;
    private final byte[] data;

    public Word() {
        this.data = new byte[SIZE];
    }

    public Word(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public byte getByte(int i) {
        return data[i];
    }

    public void setByte(int i, byte dat) {
        data[i] = dat;
    }
}
