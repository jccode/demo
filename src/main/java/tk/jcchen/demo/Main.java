package tk.jcchen.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ResourceBundle;
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

    public static void main(String[] args) {
        LOGGER.info("Hello world");
        Main instance = new Main();
        instance.hello();
        String textData = instance.readData();
        byte[] data = instance.transformToByte(textData);
        instance.parseData(data);
    }

    private byte[] transformToByte(String textData) {
        byte[] bs = new byte[100];
        
        return bs;
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
        System.out.println(0x7E);
    }

    private void parseData(byte[] data) {

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
