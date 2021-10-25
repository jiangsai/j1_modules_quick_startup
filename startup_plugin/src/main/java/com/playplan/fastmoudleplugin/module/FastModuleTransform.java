package com.playplan.fastmoudleplugin.module;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * author : jyt
 * time   : 2021/10/18
 * desc   :
 */
public class FastModuleTransform extends Transform {

    ArrayList<String> fastModules;

    @Override
    public String getName() {
        return Constant.FAST_MODULE;
    }

    /**
     * 作用文件
     *
     * @return
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * 作用范围
     *
     * @return
     */

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }


    public void setFastMoudles(ArrayList<String> fastModules) {
        this.fastModules = fastModules;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        System.out.println("transform开始 寻找 TestModuleProxyCreator");

        Collection<TransformInput> transformInputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        Iterator<TransformInput> it = transformInputs.iterator();
        for (; it.hasNext(); ) {
            TransformInput transformInput = it.next();
            //这里只能拿到两种类型的文件，文件夹和jar
            Iterator<DirectoryInput> itDirectory = transformInput.getDirectoryInputs().iterator();
            for (; itDirectory.hasNext(); ) {
                //如果是文件夹
                DirectoryInput directoryInput = itDirectory.next();
                if (directoryInput.getFile().isDirectory()) {
                    ArrayList<File> list = new ArrayList<>();
                    Scan.getFiles(list, directoryInput.getFile().getPath());
                    for (File file : list) {
                        //  System.out.println("jyt fileName=="+file.getName());
                        if (file.getName().equals("TestModuleProxyCreator")) {
                            System.out.println("jyt 在文件夹中找到了 需要注入的类");
                            Scan.TARGET_FILE = file;
                        }
                    }
                }

                File dest = outputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY);
                FileUtils.copyDirectory(directoryInput.getFile(), dest);

            }

            //这里开始扫描jar
            Iterator<JarInput> itJarInput = transformInput.getJarInputs().iterator();

            for (; itJarInput.hasNext(); ) {
                JarInput jarInput = itJarInput.next();
                String jarName = jarInput.getName();
//                System.out.println("jyt jarName=="+jarName);
//                System.out.println("jyt getAbsolutePath=="+jarInput.getFile().getAbsolutePath());
                String md5 = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4);
                }
                File dest = outputProvider.getContentLocation(jarName + md5,
                        jarInput.getContentTypes(),
                        jarInput.getScopes(), Format.JAR);
                if (jarInput.getFile().getAbsolutePath().endsWith(".jar")) {
                    //处理jar包里的代码
                    File src = jarInput.getFile();
                    //判断不是sdk的jar
                    if (Scan.shouldProcessPreDexJar(src.getAbsolutePath())) {
                        Scan.scanJar(src, dest);
                    }
                }
                FileUtils.copyFile(jarInput.getFile(), dest);

            }
        }

        if (null != Scan.TARGET_FILE) {
            new FastModuleInjector(fastModules).execute();
        }

    }


}
