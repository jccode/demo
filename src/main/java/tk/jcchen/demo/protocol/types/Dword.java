package tk.jcchen.demo.protocol.types;

/**
 * Created by jcchen on 16-9-2.
 */
public class Dword extends AbstractProtoDataType {

    private final int length = 4;
    private byte[] content = new byte[length];

    @Override
    public int length() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public void fromHexString(String hexString) {
        this.content = hexStringToBytes(hexString);
    }

}
