package tk.jcchen.demo.protocol.types;

/**
 * Created by jcchen on 16-9-2.
 */
public interface ProtoDataType {

    int BIG_ENDIAN = 0;
    int LITTLE_ENDIAN = 1;

    /**
     * length of data type. (unit: byte)
     * @return
     */
    int length();

    /**
     * big-endian or little-endian
     * @return
     */
    int endian();

    byte[] getBytes();

    String toHexString();

    void fromHexString(String hexString);
}
