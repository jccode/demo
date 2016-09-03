package tk.jcchen.demo.protocol.types;

/**
 * Created by jcchen on 16-9-2.
 */
public class Byte extends AbstractProtoDataType {

    private byte[] content;

    public Byte() {
        this.content = new byte[0];
    }

    public Byte(int n) {
        this.content = new byte[n];
    }

    public Byte(byte b) {
        content = new byte[1];
        content[0] = b;
    }

    public Byte(byte[] contents) {
        this.content = contents;
    }

    @Override
    public int length() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public void fromHexString(String hexString) {
        content = hexStringToBytes(hexString);
    }

    @Override
    public int endian() {
        return ProtoDataType.LITTLE_ENDIAN;
    }
}
