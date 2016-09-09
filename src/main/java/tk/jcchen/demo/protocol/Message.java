package tk.jcchen.demo.protocol;

import tk.jcchen.demo.protocol.types.Byte;
import tk.jcchen.demo.protocol.types.HexConvert;
import tk.jcchen.demo.protocol.types.ProtoDataType;

/**
 * Created by jcchen on 16-9-2.
 */
public interface Message extends HexConvert {

    Byte delimeter();

    Byte checkcode();

    Header header();

    Body body();

}
