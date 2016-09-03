package tk.jcchen.demo.protocol.types;

/**
 * Created by jcchen on 16-9-2.
 */
public abstract class AbstractProtoDataType implements ProtoDataType {

    @Override
    public int endian() {
        return ProtoDataType.BIG_ENDIAN;
    }

    @Override
    public String toHexString() {
        return bytesToHexString(getBytes());
    }

    /**
     * Transform bytes to hex string
     *
     * @param content
     * @return
     */
    protected String bytesToHexString(byte[] content) {
        if(content == null || content.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(content.length);
        int idx;
        for (int i = 0; i < content.length; i++) {
            if(endian() == ProtoDataType.LITTLE_ENDIAN) {
                idx = content.length-1 - i;
            } else {
                idx = i;
            }
            String hex = Integer.toHexString(content[idx] & 0xFF);
            if(hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Transform hex string to byte array.
     *
     * @param hexString
     * @return
     */
    protected byte[] hexStringToBytes(String hexString) {
        if(hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int len = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] result = new byte[len];
        int pos, idx;
        for (int i = 0; i < result.length; i++) {
            pos = i*2;
            if(endian() == ProtoDataType.LITTLE_ENDIAN) {
                idx = result.length - 1 - i;
            } else {
                idx = i;
            }
            result[idx] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos+1]));
        }
        return result;
    }

    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    protected int bytesToInt(byte[] bytes) {
        int len = bytes.length;
        int res = 0;
        for (int i = 0; i < len; i++) {
            res = (bytes[i] & 0xFF) << ((len-1-i) * 8) | res;
        }
        return res;
    }
}
