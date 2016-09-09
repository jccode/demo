package tk.jcchen.demo.protocol;

import tk.jcchen.demo.protocol.impl.HeaderImpl;
import tk.jcchen.demo.protocol.impl.MessageImpl;

/**
 * Created by jcchen on 16-9-6.
 */
public class MessageBuilder {

    public static Message newMessage(int msgId, String mobile, int seqNum, Body body) {
        return newMessage(msgId, mobile, seqNum, body, "");
    }

    public static Message newMessage(int msgId, String mobile, int seqNum, Body body, String encrypt) {
        Header header = new HeaderImpl(msgId, mobile, seqNum, body.length(), encrypt);
        Message msg = new MessageImpl(header, body);
        return msg;
    }
}
