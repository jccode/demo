package tk.jcchen.demo.protocol;

import tk.jcchen.demo.protocol.types.ProtoDataType;

/**
 * Created by jcchen on 16-9-2.
 */
public interface BodyAttr extends ProtoDataType {

    /**
     * 字段长度(占的字节数)
     * @return
     */
    int length();

    /**
     * 数据体的长度(length of body)
     * @return
     */
    int bodyLength();

    /**
     * "", if non encrypt.
     *
     * @return
     */
    String encrypt();

    /**
     * 是否分包处理
     * @return
     */
    boolean isMultiplePackages();

}
