package tk.jcchen.demo.protocol;

import tk.jcchen.demo.protocol.types.Byte;

/**
 * Created by jcchen on 16-9-2.
 */
public interface Message {

    Byte delimeter();

    Byte checkcode();

    Header header();

    Body body();

    String toHexString();

    void fromHexString(String hexString) throws ProtoException;
}
