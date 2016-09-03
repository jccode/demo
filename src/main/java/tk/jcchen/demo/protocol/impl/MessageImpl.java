package tk.jcchen.demo.protocol.impl;

import tk.jcchen.demo.protocol.Body;
import tk.jcchen.demo.protocol.Header;
import tk.jcchen.demo.protocol.Message;
import tk.jcchen.demo.protocol.ProtoException;
import tk.jcchen.demo.protocol.types.Byte;
import tk.jcchen.demo.protocol.types.ProtoDataType;

/**
 * Created by jcchen on 16-9-2.
 */
public class MessageImpl implements Message {

    public final byte delimeter = 0x7e;

    private Header header;
    private Body body;

    public MessageImpl() {
        header = new HeaderImpl();
        body = new BodyImpl();
    }

    @Override
    public Byte delimeter() {
        return new Byte(this.delimeter);
    }

    @Override
    public Byte checkcode() {
        return null;
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
    public String toHexString() {
        return null;
    }

    @Override
    public void fromHexString(String hexString) throws ProtoException {
        String delimeterStr = this.delimeter().toHexString();
        if(!hexString.startsWith(delimeterStr) || !hexString.endsWith(delimeterStr)) {
            throw new ProtoException("Invalid hex string.");
        }
        String data = hexString.substring(delimeterStr.length(), hexString.length()-delimeterStr.length());
        header.fromHexString(data);
//        System.out.println(header);
    }

    @Override
    public String toString() {
        return "MessageImpl{" +
                "delimeter=" + delimeter +
                "header=" + header() +
                "body=" + body() +
                '}';
    }
}
