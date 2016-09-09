package tk.jcchen.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.jcchen.demo.protocol.*;
import tk.jcchen.demo.protocol.body.ServerCommonResponse;
import tk.jcchen.demo.protocol.impl.BodyAttrImpl;
import tk.jcchen.demo.protocol.impl.HeaderImpl;
import tk.jcchen.demo.protocol.impl.MessageImpl;
import tk.jcchen.demo.protocol.types.BCD;
import tk.jcchen.demo.protocol.types.Byte;
import tk.jcchen.demo.protocol.types.Word;
import tk.jcchen.demo.protocol.utils.ClassUtil;
import tk.jcchen.demo.protocol.utils.HexConverter;
import tk.jcchen.demo.protocol.utils.StringCutter;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by jc on 16-9-1.
 */
public class Main {

    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private File dataFile;

    public Main() {
        dataFile = new File(Main.class.getResource("/data.txt").getFile());
    }

    public static void main(String[] args) throws Exception {
        LOGGER.info("Hello world");
        Main instance = new Main();
        instance.parseData();
//        instance.createMsg();
//        instance.hello();
    }

    private void createMsg() {
        Body body = new ServerCommonResponse(1,1,1);
        Message msg = MessageBuilder.newMessage(0x8001, "18138438111", 10, body);
        System.out.println(msg);
        System.out.println(msg.toHexString());
    }

    private void parseData() throws ProtoException {
        String textData = readData();
        Message msg = new MessageImpl();
        ((MessageImpl)msg).fromHexString(textData, true);

        LOGGER.info(textData);
        LOGGER.info(msg.toString());
        LOGGER.info(msg.toHexString());
        LOGGER.info(((MessageImpl)msg).toHexString(true));
    }


    private String readData() {
        try {
            FileReader reader = new FileReader(dataFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void hello() {
//        System.out.println(32769 == 0x8001);
    }

    private void classUtil() throws IllegalAccessException, InstantiationException {
        List<Class<? extends Body>> bodyImpls = ClassUtil.getAllClassByInterface(Body.class);
        for (Class<? extends Body> c : bodyImpls) {
            Body b = c.newInstance();
            System.out.println(b.msgId());
        }
    }

    private void bcd() {
        String s = "018138438111";
        BCD b = new BCD(6);
        b.valueOf(s);
        System.out.println(b);
    }

    private void newBodyAttr() {
        // 10 bit, < 2^10 = 1024
        BodyAttr attr = new BodyAttrImpl(60, "");
        System.out.println(attr.toHexString());
    }

    private void dataType() {
        tk.jcchen.demo.protocol.types.Byte b = new Byte();
        b.valueOf(200);
        System.out.println(b);
        Word w = new Word();
        w.valueOf(20000);
        System.out.println(w);
    }

    private void stringCutterTest() {
        StringCutter sc = new StringCutter("helloWorld");
        System.out.println(sc.cut(2));
        System.out.println(sc.remain());
        System.out.println(sc.cut(3));
        System.out.println(sc.remain());
        System.out.println(sc.cut(3));
        System.out.println(sc.remain());
        System.out.println(sc.cut(10));
        System.out.println(sc.remain());
        System.out.println("------------");
    }

    private void byteArrayAndIntTransform() {
        int t1 = Integer.parseInt("10101000"+"01001000",2);
        System.out.println(t1);
        int data = 43080;
        System.out.println("data:"+data+"\t binary:"+Integer.toBinaryString(data));

        int t2 = Integer.parseInt("10101000",2);
        int t3 = Integer.parseInt("01001000",2);
        System.out.println("data:"+t2 + "\t binary:"+Integer.toBinaryString(t2));
        System.out.println("data:"+t3 + "\t\t binary:"+Integer.toBinaryString(t3));
        System.out.println(t2 << 8 | t3);

        byte[] bs = new byte[]{(byte)t2,(byte)t3};
        System.out.println(bs);
        for (int i = 0; i < bs.length; i++) {
            System.out.println(bs[i] & 0xFF);
        }
        System.out.println((bs[0] & 0xFF) << 8 | (bs[1] & 0xFF));
        System.out.println(bytesToInt(bs));
    }

    private int bytesToInt(byte[] bytes) {
        int len = bytes.length;
        int res = 0;
        for (int i = 0; i < len; i++) {
            res = (bytes[i] & 0xFF) << ((len-1-i) * 8) | res;
        }
        return res;
    }

    private void bodyAttr() {
        int t = Integer.parseInt("01001001"+"11001010",2);
        System.out.println(t);
        System.out.println(Integer.toHexString(t));

        int len = t & 0x3FF;
        System.out.println("len="+len);
        System.out.println(Integer.toBinaryString(len));

        int encrypt = (t & 0xE00) >>> 10;
        System.out.println("encrypt="+encrypt);
        System.out.println(Integer.toBinaryString(encrypt));

        int encrypt2 = (t >>> 10) & 0x7;
        System.out.println("encrypt2="+encrypt2);
        System.out.println(Integer.toBinaryString(encrypt2));

        int multiplePackage = (t >>> 13) & 0x1;
        System.out.println("multiple package="+multiplePackage);
    }



    private void withFile(File file, Consumer<DataInputStream> fn){
        FileInputStream is = null;
        DataInputStream dis = null;
        try {
            is = new FileInputStream(file);
            dis = new DataInputStream(is);
            fn.accept(dis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null)
                    is.close();
                if(dis != null)
                    dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
