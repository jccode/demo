package tk.jcchen.demo.protocol.types;

import tk.jcchen.demo.protocol.ProtoException;

/**
 * Created by jcchen on 16-9-7.
 */
public interface HexConvert {

    String toHexString();

    void fromHexString(String hexString) throws ProtoException;
}
