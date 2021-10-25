package com.playplan.fastmoudleplugin.module;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;


/**
 * author : jyt
 * time   : 2021/10/19
 * desc   :
 */
public class FastModuleInjector {
    ArrayList<String> fastModules;

    public FastModuleInjector(ArrayList<String> fastModules) {

        this.fastModules = fastModules;
    }

    public void execute() {
        File srcFile = Scan.TARGET_FILE;
        //创建一个临时jar文件，要修改注入的字节码会先写入该文件里
        File optJar = new File(srcFile.getParent(), srcFile.getName() + ".opt");
        if (optJar.exists()) {
            optJar.delete();
        }
        JarFile file = null;
        try {
            file = new JarFile(srcFile);
            Enumeration<JarEntry> enumeration = file.entries();
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJar));
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                InputStream inputStream = file.getInputStream(jarEntry);
                jarOutputStream.putNextEntry(zipEntry);

                //找到需要插入代码的class，通过ASM动态注入字节码
                if (entryName.endsWith("TestModuleProxyCreator.class")) {

                    ClassReader classReader = new ClassReader(inputStream);
                    // 构建一个ClassWriter对象，并设置让系统自动计算栈和本地变量大小
                    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    ClassVisitor classVisitor = new FastModuleClassVisitor(classWriter);
                    //开始扫描class文件
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                    byte[] bytes = classWriter.toByteArray();
                    //将注入过字节码的class，写入临时jar文件里
                    jarOutputStream.write(bytes);
                } else {
                    //不需要修改的class，原样写入临时jar文件里
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                inputStream.close();
                jarOutputStream.closeEntry();
            }

            jarOutputStream.close();
            file.close();

            //删除原来的jar文件
            if (srcFile.exists()) {
                srcFile.delete();
            }
            //重新命名临时jar文件，新的jar包里已经包含了我们注入的字节码了
            optJar.renameTo(srcFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FastModuleClassVisitor extends ClassVisitor {
        FastModuleClassVisitor(ClassVisitor classVisitor) {
            super(Opcodes.ASM7, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name,
                                         String desc, String signature,
                                         String[] exception) {
            System.out.println("开始执行FastModuleClassVisitor方法======>>>>>>>>" + name);
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exception);
            //找到 ModuleLoader里的loadModules()方法
            if ("initData".equals(name)) {
                mv = new LoadModuleMethodAdapter(mv, access, name, desc);
            }
            return mv;
        }
    }

    class LoadModuleMethodAdapter extends AdviceAdapter {

        LoadModuleMethodAdapter(MethodVisitor mv, int access, String name, String desc) {
            super(Opcodes.ASM7, mv, access, name, desc);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            System.out.println("开始执行LoadModuleMethodAdapter方法======>>>>>>>>");
            if (fastModules != null) {
                for (String clzz : fastModules) {
                    mv.visitTypeInsn(NEW, clzz);
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, clzz, "<init>", "()V", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, clzz, "intitFastNodeList", "()V", false);
                }
            }

        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);

        }
    }
}
