package tk.jcchen.demo.protocol.types;

import tk.jcchen.demo.protocol.utils.HexConverter;

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

    public void valueOf(String s) {
        if(s == null || s.equals("")) {
            return;
        }
        if(s.length() < 2) {
            content[content.length-1] = (byte) Integer.parseInt(s, 10);
            return;
        }

        char[] chars = s.toCharArray();
        for (int i = chars.length-1, pos = content.length-1; i >= 0 && pos >= 0; i-=2, pos--) {
            byte t;
            if(i-1 < 0) {
                t = HexConverter.charToByte(chars[i]);
            } else {
                t = (byte) ((HexConverter.charToByte(chars[i-1]) << 4) | HexConverter.charToByte(chars[i]));
            }
            content[pos] = t;
        }
    }

    @Override
    public String toString() {
        return "BCD{" +
                "content=" + Arrays.toString(content) +
                ", hex=" + this.toHexString() +
                '}';
    }
}
