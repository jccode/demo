package tk.jcchen.demo.protocol.body;

import tk.jcchen.demo.protocol.Body;
import tk.jcchen.demo.protocol.ProtoException;

/**
 * Created by jcchen on 16-9-7.
 */
public class TerminalCommonResponse implements Body {

    @Override
    public int msgId() {
        return 0x0001;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public String toHexString() {
        return null;
    }

    @Override
    public void fromHexString(String hexString) throws ProtoException {

    }
}
