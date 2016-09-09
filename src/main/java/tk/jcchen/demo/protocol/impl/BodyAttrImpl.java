package tk.jcchen.demo.protocol.impl;

import tk.jcchen.demo.protocol.BodyAttr;
import tk.jcchen.demo.protocol.types.AbstractProtoDataType;
import tk.jcchen.demo.protocol.types.ProtoDataType;

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

    public BodyAttrImpl() {
    }

    public BodyAttrImpl(int bodyLength, String encrypt) {
        this.bodyLength = bodyLength;
        this.encrypt = calcEncrypt(encrypt);
        this.multiplePackage = 0;

        content = new byte[len];
        byte b1 = (byte) (bodyLength & 0xFF);
        byte b2 = (byte) ((bodyLength >>> 8 & 0b11) | ((this.encrypt & 0b111) << 2) | ((this.multiplePackage & 0b1) << 5));
        if(this.endian() == ProtoDataType.BIG_ENDIAN) {
            content[0] = b2;
            content[1] = b1;
        } else {
            content[0] = b1;
            content[1] = b2;
        }
    }

    private int calcEncrypt(String encrypt) {
        if(encrypt == null || encrypt.equals("")) {
            return 0;
        }
        else if(encrypt.toUpperCase().equals("RSA")) {
            return 1;
        } else {
            return 0;
        }
    }

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
        bodyLength = value & 0x3FF;
        encrypt = (value >>> 10) & 0x7;
        multiplePackage = (value >>> 13) & 0x1;
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
