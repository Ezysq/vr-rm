package Memory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Word {
    public static final int SIZE = 4;
    private byte[] data;

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

    public Word clone(){
        return new Word(this.data.clone());
    }

    public static int wordToInt(Word word) {
        ByteBuffer bb = ByteBuffer.allocateDirect(SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.clear();
        for(int i = 0; i < SIZE; i++){
            bb.put(word.getByte(i));
        }
        bb.position(0);
        return bb.getInt();
    }

    public static Word intToWord(int value) {
        ByteBuffer bb = ByteBuffer.allocateDirect(SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.clear();
        bb.putInt(value);
        Word word = new Word();
        for(int i = 0; i < SIZE; i++){
            word.setByte(i, bb.get(i));
        }
        return word;
    }

}
