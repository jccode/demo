package tk.jcchen.demo.protocol;

import tk.jcchen.demo.protocol.types.BCD;
import tk.jcchen.demo.protocol.types.HexConvert;
import tk.jcchen.demo.protocol.types.ProtoDataType;
import tk.jcchen.demo.protocol.types.Word;

/**
 * Created by jcchen on 16-9-2.
 */
public interface Header extends ProtoDataType {

    Word id();

    BodyAttr bodyAttr();

    BCD mobile();

    /**
     * 流水号
     * @return
     */
    Word serialNum();

    PackagingItem packagingItem();

}
