package tk.jcchen.demo.protocol.types;

import java.util.Arrays;

/**
 * Created by jcchen on 16-9-2.
 */
public class BCD extends AbstractProtoDataType {

    private byte[] content;

    public BCD() {
        content = new byte[0];
    }

    public BCD(int n) {
        content = new byte[n];
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
        this.content = hexStringToBytes(hexString);
    }

    @Override
    public String toString() {
        return "BCD{" +
                "content=" + Arrays.toString(content) +
                ", hex=" + this.toHexString() +
                '}';
    }
}
