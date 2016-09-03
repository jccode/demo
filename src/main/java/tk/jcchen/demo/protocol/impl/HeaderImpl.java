package tk.jcchen.demo.protocol.impl;

import tk.jcchen.demo.protocol.BodyAttr;
import tk.jcchen.demo.protocol.Header;
import tk.jcchen.demo.protocol.PackagingItem;
import tk.jcchen.demo.protocol.types.BCD;
import tk.jcchen.demo.protocol.types.Word;
import tk.jcchen.demo.protocol.utils.StringCutter;

/**
 * Created by jcchen on 16-9-2.
 */
public class HeaderImpl implements Header {

    private Word id;
    private BodyAttr bodyAttr;
    private BCD mobile;
    private Word serialNum;
    private PackagingItem packagingItem;

    public HeaderImpl() {
        id = new Word();
        bodyAttr = new BodyAttrImpl();
        mobile = new BCD(6);
        serialNum = new Word();
        packagingItem = new PackagingItemImpl();
    }

    @Override
    public Word id() {
        return id;
    }

    @Override
    public BodyAttr bodyAttr() {
        return bodyAttr;
    }

    @Override
    public BCD mobile() {
        return mobile;
    }

    @Override
    public Word serialNum() {
        return serialNum;
    }

    @Override
    public PackagingItem packagingItem() {
        return packagingItem;
    }

    @Override
    public void fromHexString(String hexString) {
        StringCutter sc = new StringCutter(hexString);
        id().fromHexString(sc.cut(id().length() * 2));
        bodyAttr().fromHexString(sc.cut(bodyAttr().length() * 2));

        /*
        int len = id().length()*2;
        String idHex = hexString.substring(0, len);
        id().fromHexString(idHex);

        hexString = hexString.substring(len);
        len = bodyAttr().length()*2;
        String bodyAttrHex = hexString.substring(0, len);
        bodyAttr().fromHexString(bodyAttrHex);

        hexString = hexString.substring(len);
*/

    }

    @Override
    public String toString() {
        return "HeaderImpl{" +
                "id=" + id +
                ", bodyAttr=" + bodyAttr +
                ", mobile=" + mobile +
                ", serialNum=" + serialNum +
                ", packagingItem=" + packagingItem +
                '}';
    }
}
