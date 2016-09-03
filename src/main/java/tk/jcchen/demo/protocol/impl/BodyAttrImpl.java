package tk.jcchen.demo.protocol.impl;

import tk.jcchen.demo.protocol.BodyAttr;
import tk.jcchen.demo.protocol.types.AbstractProtoDataType;

import java.util.Arrays;

/**
 * Created by jcchen on 16-9-2.
 */
public class BodyAttrImpl extends AbstractProtoDataType implements BodyAttr {

    public static final int len = 2;
    private byte[] content = new byte[len];
    private int bodyLength = 0;
    private int encrypt = 0;
    private int multiplePackage = 0;

    @Override
    public int length() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public void fromHexString(String hexString) {
        content = hexStringToBytes(hexString);
        System.out.println(hexString);
        init();
    }


    @Override
    public int bodyLength() {
        return bodyLength;
    }

    @Override
    public String encrypt() {
        if (encrypt == 0) {
            return "";
        }
        else if((encrypt & 0x1) == 1) {
            return "RSA";
        }
        else {
            return "";
        }
    }

    @Override
    public boolean isMultiplePackages() {
        return multiplePackage == 1;
    }

    private void init() {
        int value = bytesToInt(content);
        bodyLength = value & 0x1FF;
        encrypt = (value >>> 9) & 0x7;
        multiplePackage = (value >>> 12) & 0x1;
    }

    @Override
    public String toString() {
        return "BodyAttrImpl{" +
                "content=" + Arrays.toString(content) +
                ", bodyLength=" + bodyLength +
                ", encrypt=" + this.encrypt() +
                ", isMultiplePackages=" + this.isMultiplePackages() +
                ", hex=" + this.toHexString() +
                '}';
    }
}
