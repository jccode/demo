package tk.jcchen.demo.protocol.impl;

import tk.jcchen.demo.protocol.PackagingItem;
import tk.jcchen.demo.protocol.types.AbstractProtoDataType;
import tk.jcchen.demo.protocol.types.Word;
import tk.jcchen.demo.protocol.utils.StringCutter;

/**
 * Created by jcchen on 16-9-2.
 */
public class PackagingItemImpl extends AbstractProtoDataType implements PackagingItem {

    private Word count;
    private Word seqNum;

    public PackagingItemImpl() {
        this.count = new Word();
        this.seqNum = new Word();
    }

    @Override
    public Word count() {
        return count;
    }

    @Override
    public Word seqNum() {
        return seqNum;
    }

    @Override
    public int length() {
        return count.length()+seqNum.length();
    }

    @Override
    public byte[] getBytes() {
        byte[] content = new byte[this.length()];
        byte[] _c = count().getBytes();
        byte[] _s = seqNum().getBytes();
        System.arraycopy(_c, 0, content, 0, count.length());
        System.arraycopy(_s, 0, content, count.length(), seqNum.length());
        return content;
    }

    @Override
    public void fromHexString(String hexString) {
        StringCutter sc = new StringCutter(hexString);
        count.fromHexString(sc.cut(count().length() * 2));
        seqNum.fromHexString(sc.cut(seqNum().length() * 2));
    }

    @Override
    public String toString() {
        return "PackagingItemImpl{" +
                "count=" + count +
                ", seqNum=" + seqNum +
                '}';
    }
}
