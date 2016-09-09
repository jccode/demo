package tk.jcchen.demo.protocol.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.jcchen.demo.protocol.Body;
import tk.jcchen.demo.protocol.Header;
import tk.jcchen.demo.protocol.Message;
import tk.jcchen.demo.protocol.ProtoException;
import tk.jcchen.demo.protocol.types.AbstractProtoDataType;
import tk.jcchen.demo.protocol.types.Byte;
import tk.jcchen.demo.protocol.utils.ClassUtil;
import tk.jcchen.demo.protocol.utils.HexConverter;
import tk.jcchen.demo.protocol.utils.StringCutter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jcchen on 16-9-2.
 */
public class MessageImpl implements Message {

    public static final Logger LOGGER = LoggerFactory.getLogger(MessageImpl.class);
    public final byte delimeter = 0x7e;

    private Header header;
    private Body body;
    private Byte checkcode;

    private static Map<Integer, Class<? extends Body>> msgBodyImpls;

    static {
        msgBodyImpls = new HashMap<>();
        List<Class<? extends Body>> bodyImpls = ClassUtil.getAllClassByInterface(Body.class);
        for (Class<? extends Body> c : bodyImpls) {
            try {
                Body b = c.newInstance();
                msgBodyImpls.put(b.msgId(), c);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    public MessageImpl() {
        header = new HeaderImpl();
        checkcode = new Byte(1);
    }

    public MessageImpl(Header header, Body body) {
        this.header = header;
        this.body = body;
        this.checkcode = new Byte();
        this.checkcode.valueOf(computeCheckcode(HexConverter.concat(header.getBytes(), body.getBytes())));
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


    public byte[] getBytes() {
        return HexConverter.concat(
                header().getBytes(),
                body().getBytes(),
                checkcode().getBytes()
        );
    }

    private static Body getBodyImpl(int msgId) {
        Class<? extends Body> c = msgBodyImpls.get(msgId);
        if(c == null) {
            LOGGER.error("Body protocol implement of 0x" + Integer.toHexString(msgId) + " is not implemented");
        }
        Body b = null;
        try {
            b = c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return b;
    }

    public String toHexString(boolean withDelimeter) {
        String d = Integer.toHexString(delimeter).toUpperCase();
        return d + this.toHexString() + d;
    }

    @Override
    public String toHexString() {
        return HexConverter.bytesToHexString(getBytes());
    }

    public void fromHexString(String hexString, boolean withDelimeter) throws ProtoException {
        if (withDelimeter) {
            // Verify delimeters
            String delimeterStr = this.delimeter().toHexString();
            if(!hexString.startsWith(delimeterStr) || !hexString.endsWith(delimeterStr)) {
                throw new ProtoException("Invalid hex string.");
            }

            // Remove delimeters
            String data = hexString.substring(delimeterStr.length(), hexString.length()-delimeterStr.length());

            constructFromHexString(data);
        } else {
            constructFromHexString(hexString);
        }
    }

    /**
     * construct message
     *
     * @param data hex string without delimeters
     */
    private void constructFromHexString(String data) throws ProtoException {
        String hexCheckcode = data.substring(data.length() - checkcode.length()*2);
        checkcode.fromHexString(hexCheckcode);

        // Verify checkcode
        String hexContent = data.substring(0, data.length() - checkcode.length()*2);
        byte[] content = HexConverter.hexStringToBytes(hexContent);
        if(computeCheckcode(content) != checkcode().intValue()) {
            throw new ProtoException("Invalid hex string.");
        }

        // Construct header
        header.fromHexString(data);

        // Construct body
        String hexBody = hexContent.substring(header.length());

        int msgId = header.id().intValue();
        Body b = getBodyImpl(msgId);
        if(b == null) {
            throw new ProtoException("Body protocol of 0x" + Integer.toHexString(msgId) + " is not implement");
        }
        b.fromHexString(hexBody);
        body = b;
    }

    @Override
    public void fromHexString(String hexString) throws ProtoException {
        fromHexString(hexString, false);
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
