package tk.jcchen.demo.protocol.types;

import java.util.Arrays;

/**
 * Created by jcchen on 16-9-2.
 */
public class Word extends AbstractProtoDataType {

    private final int length = 2;
    private byte[] content = new byte[length];

    public Word() {
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public void fromHexString(String hexString) {
        this.content = hexStringToBytes(hexString);
    }

    public int intValue() {
        return bytesToInt(content);
    }

    public void valueOf(int i) {
        byte[] bs = new byte[2];
        if(endian() == ProtoDataType.BIG_ENDIAN) {
            bs[1] = (byte) (i & 0xFF);
            bs[0] = (byte) (i >>> 8 & 0xFF);
        }
        else {
            bs[0] = (byte) (i & 0xFF);
            bs[1] = (byte) (i >>> 8 & 0xFF);
        }
        content = bs;
    }

    @Override
    public String toString() {
        return "Word{" +
                "length=" + length +
                ", content=" + Arrays.toString(content) +
                ", hex=" + this.toHexString() +
                ", int=" + this.intValue() +
                '}';
    }
}
