package tk.jcchen.demo.protocol;

import tk.jcchen.demo.protocol.types.HexConvert;

/**
 * Created by jcchen on 16-9-2.
 */
public interface Body extends HexConvert {

    int msgId();

    int length();

    byte[] getBytes();

}
