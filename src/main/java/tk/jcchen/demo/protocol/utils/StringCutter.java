package tk.jcchen.demo.protocol.utils;

/**
 * 字符串切割器; <br/>
 * 支持从前往后,按长度切分.
 *
 * Created by jcchen on 16-9-3.
 */
public class StringCutter {

    private String text;
    private int idx = 0;

    public StringCutter(String s) {
        text = s;
    }

    public String cut(int length) {
        int end = idx+length;
        if (end > text.length()) {
            end = text.length();
        }
        String s = text.substring(idx, end);
        idx = end;
        return s;
    }

    public void reset() {
        idx = 0;
    }

    public String remain() {
        return text.substring(idx);
    }
}
