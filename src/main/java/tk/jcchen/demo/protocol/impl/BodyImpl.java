package tk.jcchen.demo.protocol.impl;

import tk.jcchen.demo.protocol.Body;
import tk.jcchen.demo.protocol.types.AbstractProtoDataType;

/**
 * Created by jcchen on 16-9-2.
 */
public class BodyImpl extends AbstractProtoDataType implements Body {

    @Override
    public int length() {
        return 0;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void fromHexString(String hexString) {

    }
}
