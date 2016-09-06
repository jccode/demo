package tk.jcchen.demo.protocol.utils;

/**
 * Created by jcchen on 16-9-5.
 */
public class HexConverter {

    public static final int BIG_ENDIAN = 0;
    public static final int LITTLE_ENDIAN = 1;


    /**
     * Transform bytes to hex string
     *
     * @param content byte array
     * @param endian  big-endian:0; little-endian:1
     * @return
     */
    public static String bytesToHexString(byte[] content, int endian) {
        if(content == null || content.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(content.length);
        int idx;
        for (int i = 0; i < content.length; i++) {
            if(endian == LITTLE_ENDIAN) {
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

    public static String bytesToHexString(byte[] content) {
        return bytesToHexString(content, BIG_ENDIAN);
    }

    /**
     * Transform hex string to byte array.
     *
     * @param hexString
     * @param endian big-endian:0; little-endian:1;
     * @return
     */
    public static byte[] hexStringToBytes(String hexString, int endian) {
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
            if(endian == LITTLE_ENDIAN) {
                idx = result.length - 1 - i;
            } else {
                idx = i;
            }
            result[idx] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos+1]));
        }
        return result;
    }

    public static byte[] hexStringToBytes(String hexString) {
        return hexStringToBytes(hexString, BIG_ENDIAN);
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static int bytesToInt(byte[] bytes) {
        int len = bytes.length;
        int res = 0;
        for (int i = 0; i < len; i++) {
            res = (bytes[i] & 0xFF) << ((len-1-i) * 8) | res;
        }
        return res;
    }
}
