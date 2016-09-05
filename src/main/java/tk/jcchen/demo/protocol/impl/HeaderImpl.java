package tk.jcchen.demo.protocol.impl;

import tk.jcchen.demo.protocol.BodyAttr;
import tk.jcchen.demo.protocol.Header;
import tk.jcchen.demo.protocol.PackagingItem;
import tk.jcchen.demo.protocol.ProtoException;
import tk.jcchen.demo.protocol.types.AbstractProtoDataType;
import tk.jcchen.demo.protocol.types.BCD;
import tk.jcchen.demo.protocol.types.Word;
import tk.jcchen.demo.protocol.utils.StringCutter;

/**
 * Created by jcchen on 16-9-2.
 */
public class HeaderImpl extends AbstractProtoDataType implements Header {

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
    public int length() {
        int len = id().length() + bodyAttr().length() + mobile().length() + serialNum().length();
        if(isPackageItemExist()) {
            return (len + packagingItem().length()) * 2;
        } else {
            return 2 * len;
        }
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = new byte[length()];
        byte[] b1 = id().getBytes();
        byte[] b2 = bodyAttr().getBytes();
        byte[] b3 = mobile().getBytes();
        byte[] b4 = serialNum().getBytes();

        System.arraycopy(b1, 0, bytes, 0, b1.length);
        System.arraycopy(b2, 0, bytes, b1.length, b2.length);
        System.arraycopy(b3, 0, bytes, b1.length+b2.length, b3.length);
        System.arraycopy(b4, 0, bytes, b1.length+b2.length+b3.length, b4.length);

        if(isPackageItemExist()) {
            byte[] b5 = packagingItem().getBytes();
            System.arraycopy(b5, 0, bytes, b1.length+b2.length+b3.length+b4.length, b5.length);
        }
        return bytes;
    }

    @Override
    public void fromHexString(String hexString) {
        try {
            StringCutter sc = new StringCutter(hexString);
            id().fromHexString(sc.cut(id().length() * 2));
            bodyAttr().fromHexString(sc.cut(bodyAttr().length() * 2));
            mobile().fromHexString(sc.cut(mobile().length() * 2));
            serialNum().fromHexString(sc.cut(serialNum().length() * 2));
            if(isPackageItemExist()) {
                packagingItem().fromHexString(sc.cut(packagingItem().length() * 2));
            }
        } catch (ProtoException e) {
            e.printStackTrace();
        }

    }

    private boolean isPackageItemExist() {
        return bodyAttr().isMultiplePackages();
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
