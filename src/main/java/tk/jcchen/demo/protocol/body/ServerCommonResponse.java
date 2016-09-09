package tk.jcchen.demo.protocol.body;

import tk.jcchen.demo.protocol.Body;
import tk.jcchen.demo.protocol.ProtoException;
import tk.jcchen.demo.protocol.types.Byte;
import tk.jcchen.demo.protocol.types.HexConvert;
import tk.jcchen.demo.protocol.types.Word;
import tk.jcchen.demo.protocol.utils.HexConverter;
import tk.jcchen.demo.protocol.utils.StringCutter;

/**
 * Created by jcchen on 16-9-7.
 */
public class ServerCommonResponse implements Body {

    private Word respSeqNum;
    private Word respId;
    private Byte result;

    public ServerCommonResponse() {
        respSeqNum = new Word();
        respId = new Word();
        result = new Byte();
    }

    public ServerCommonResponse(int seqNum, int respId, int result) {
        this();
        respSeqNum.valueOf(seqNum);
        this.respId.valueOf(respId);
        this.result.valueOf(result);
    }

    @Override
    public int msgId() {
        return 0x8001;
    }

    @Override
    public int length() {
        return respSeqNum.length() + respId.length() + result.length();
    }

    @Override
    public byte[] getBytes() {
        return HexConverter.concat(respSeqNum.getBytes(), respId.getBytes(), result.getBytes());
    }

    @Override
    public String toHexString() {
        byte[] bytes = HexConverter.concat(respSeqNum.getBytes(), respId.getBytes(), result.getBytes());
        return HexConverter.bytesToHexString(bytes);
    }

    @Override
    public void fromHexString(String hexString) throws ProtoException {
        StringCutter sc = new StringCutter(hexString);
        respSeqNum.fromHexString(sc.cut(respSeqNum.length() * 2));
        respId.fromHexString(sc.cut(respId.length() * 2));
        result.fromHexString(sc.cut(result.length() * 2));
    }

    @Override
    public String toString() {
        return "ServerCommonResponse{" +
                "respSeqNum=" + respSeqNum +
                ", respId=" + respId +
                ", result=" + result +
                ", hex=" + this.toHexString() +
                '}';
    }
}
