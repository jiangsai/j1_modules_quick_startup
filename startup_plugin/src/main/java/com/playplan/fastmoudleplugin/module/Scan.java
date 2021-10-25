package com.playplan.fastmoudleplugin.module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * author : jyt
 * time   : 2021/10/19
 * desc   :
 */
public class Scan {

    public static File TARGET_FILE;
    static final String PROXY_CLASS_PACKAGE_NAME = "com.fastmoudle.android.generated.module";

    public static void getFiles(ArrayList<File> fileList, String path) {
        File[] allFiles = new File(path).listFiles();
        for (int i = 0; i < allFiles.length; i++) {
            File file = allFiles[i];

            if (file.isFile()) {
                fileList.add(file);
            } else {
                getFiles(fileList, file.getAbsolutePath());
            }
        }
    }

    public static boolean shouldProcessPreDexJar(String path) {
        return !path.contains("com.android.support") && !path.contains("/android/m2repository");
    }

    /**
     * 扫描jar包里的所有class文件：
     * 1.通过包名识别所有需要注入的类名
     * 2.找到注入管理类所在的jar包，后面我们会在该jar包里进行代码注入
     *
     * @param jarFile
     * @param destFile
     * @return
     */
    public static boolean scanJar(File jarFile, File destFile) {

        try {
            JarFile file = new JarFile(jarFile);
            Enumeration<JarEntry> enumeration = file.entries();

            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                String entryName = jarEntry.getName();

                if (entryName.endsWith("TestModuleProxyCreator.class")) {
                    System.out.println("jyt " + entryName);
                    //标记这个jar包 TestModuleProxyCreator
                    //扫描结束后，我们会生成注册代码到这个文件里
                    TARGET_FILE = destFile;
                    System.out.println("jyt  在文件夹中找到了 需要注入的类2");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
