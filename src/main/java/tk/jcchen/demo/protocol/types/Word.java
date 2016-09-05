package tk.jcchen.demo.protocol.types;

import java.util.Arrays;

/**
 * Created by jcchen on 16-9-2.
 */
public class Word extends AbstractProtoDataType {

    private final int length = 2;
    private byte[] content = new byte[length];

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

    public int toInt() {
        return bytesToInt(content);
    }

    @Override
    public String toString() {
        return "Word{" +
                "length=" + length +
                ", content=" + Arrays.toString(content) +
                ", hex=" + this.toHexString() +
                ", int=" + this.toInt() +
                '}';
    }
}
