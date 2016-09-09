package tk.jcchen.demo.protocol.types;

import java.util.Arrays;

/**
 * Created by jcchen on 16-9-2.
 */
public class Byte extends AbstractProtoDataType {

    private byte[] content;

    public Byte() {
        this.content = new byte[1];
    }

    public Byte(int size) {
        this.content = new byte[size];
    }

    public Byte(byte b) {
        content = new byte[1];
        content[0] = b;
    }

    public Byte(byte[] contents) {
        this.content = contents;
    }

    @Override
    public int length() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public void fromHexString(String hexString) {
        content = hexStringToBytes(hexString);
    }

    @Override
    public int endian() {
        return ProtoDataType.LITTLE_ENDIAN;
    }

    public int intValue() {
        return bytesToInt(content);
    }

    public void valueOf(int i) {
        content = new byte[]{ (byte) (i & 0xFF) };
    }

    @Override
    public String toString() {
        return "Byte{" +
                "content=" + Arrays.toString(content) +
                ", hex=" + this.toHexString() +
                ", int=" + intValue() +
                '}';
    }
}
