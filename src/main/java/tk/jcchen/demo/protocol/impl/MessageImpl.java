package tk.jcchen.demo.protocol.impl;

import tk.jcchen.demo.protocol.Body;
import tk.jcchen.demo.protocol.Header;
import tk.jcchen.demo.protocol.Message;
import tk.jcchen.demo.protocol.ProtoException;
import tk.jcchen.demo.protocol.types.AbstractProtoDataType;
import tk.jcchen.demo.protocol.types.Byte;
import tk.jcchen.demo.protocol.types.ProtoDataType;

/**
 * Created by jcchen on 16-9-2.
 */
public class MessageImpl extends AbstractProtoDataType implements Message {

    public final byte delimeter = 0x7e;

    private Header header;
    private Body body;
    private Byte checkcode;

    public MessageImpl() {
        header = new HeaderImpl();
        body = new BodyImpl();
        checkcode = new Byte(1);
    }
    

    @Override
    public Byte delimeter() {
        return new Byte(this.delimeter);
    }

    @Override
    public Byte checkcode() {
        return checkcode;
    }

    @Override
    public Header header() {
        return header;
    }

    @Override
    public Body body() {
        return body;
    }

    @Override
    public int length() {
        return header().length()+body().length()+checkcode().length();
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = new byte[length()];
        byte[] b1 = header().getBytes();
        byte[] b2 = body().getBytes();
        byte[] b3 = checkcode().getBytes();
        System.arraycopy(b1, 0, bytes, 0, b1.length);
        System.arraycopy(b2, 0, bytes, b1.length, b2.length);
        System.arraycopy(b3, 0, bytes, b1.length+b2.length, b3.length);
        return bytes;
    }

    @Override
    public String toHexString() {
        return bytesToHexString(getBytes());
    }

    @Override
    public void fromHexString(String hexString) throws ProtoException {
        // 1. Verify delimeters
        String delimeterStr = this.delimeter().toHexString();
        if(!hexString.startsWith(delimeterStr) || !hexString.endsWith(delimeterStr)) {
            throw new ProtoException("Invalid hex string.");
        }

        // 2. Remove delimeters
        String data = hexString.substring(delimeterStr.length(), hexString.length()-delimeterStr.length());
        String hexCheckcode = data.substring(data.length() - checkcode.length()*2);
        checkcode.fromHexString(hexCheckcode);

        // 3. Verify checkcode
        String hexContent = data.substring(0, data.length() - checkcode.length()*2);
        byte[] content = hexStringToBytes(hexContent);
        if(computeCheckcode(content) != checkcode().toInt()) {
            throw new ProtoException("Invalid hex string.");
        }

        // 4. Construct header
        header.fromHexString(data);

        // 5. Construct body
        String hexBody = hexContent.substring(header.length());

//        System.out.println(data);
//        System.out.println(hexContent);
//        System.out.println(hexCheckcode);
//        System.out.println(hexBody);
    }

    private int computeCheckcode(byte[] bytes) {
        if(bytes.length <= 0) {
            return 0;
        }

        int x = bytes[0] & 0xFF;
        for (int i = 1; i < bytes.length; i++) {
            x = x ^ (bytes[i] & 0xFF);
        }
        return x;
    }

    @Override
    public String toString() {
        return "MessageImpl{" +
                "delimeter=" + delimeter +
                ", header=" + header() +
                ", body=" + body() +
                ", checkcode=" + checkcode() +
                '}';
    }
}
