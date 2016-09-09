package tk.jcchen.demo.protocol.utils;

import tk.jcchen.demo.protocol.Body;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by jcchen on 16-9-8.
 */
public class ClassUtil {

    public static <T> List<Class<? extends T>> getAllClassByInterface(Class<T> c) {
        List result = new ArrayList<Class>();
        if(c.isInterface()) {
            String pkgName = c.getPackage().getName();
            try {
                List<Class> allClass = getClasses(pkgName); //获取当前包以及子包下所有类
                for (Class clazz : allClass) {
                    if (c.isAssignableFrom(clazz)) {
                        if(!c.equals(clazz)) {
                            result.add(clazz);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据包名获取包下以及子包下的所有类
     * @param pkgName
     * @return
     */
    private static List<Class> getClasses(String pkgName) throws IOException, ClassNotFoundException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String path = pkgName.replace(".","/");
        Enumeration<URL> resources = loader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClass(directory, pkgName));
        }
        return classes;
    }

    private static List<Class> findClass(File directory, String pkgName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if(!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            String name = file.getName();
            if (file.isDirectory()) {
                assert !name.contains(".");
                classes.addAll(findClass(file, pkgName+"."+ name));
            } else if(name.endsWith(".class")) {
                classes.add(Class.forName(pkgName+"."+name.substring(0, name.length()-6)));
            }
        }
        return classes;
    }
}
