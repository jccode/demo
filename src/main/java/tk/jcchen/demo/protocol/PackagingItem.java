package tk.jcchen.demo.protocol;

import tk.jcchen.demo.protocol.types.Word;

/**
 * Created by jcchen on 16-9-2.
 * 消息包封装项
 */
public interface PackagingItem {
    /**
     * 总包数
     * @return
     */
    Word count();

    /**
     * 序号
     * @return
     */
    Word seqNum();
}
