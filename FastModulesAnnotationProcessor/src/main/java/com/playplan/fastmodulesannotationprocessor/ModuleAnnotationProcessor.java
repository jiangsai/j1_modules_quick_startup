package com.playplan.fastmodulesannotationprocessor;

import com.google.auto.service.AutoService;
import com.playplan.fastmodules.annotation.FastMoudle;
import com.playplan.fastmodules.annotation.FastMoudleTarget;

import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * author : jyt
 * time   : 2021/10/18
 * desc   :
 * 只能对使用的modules生效
 * 当前只是为了创建一个壳，以便后续字节码插桩
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ModuleAnnotationProcessor extends AbstractProcessor {


    /**
     * 设置要解析的注解，否者不会走process
     * process为什么会走多次
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(FastMoudle.class.getCanonicalName());
        set.add(FastMoudleTarget.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("jyt===start==进了");
        System.out.println("jyt===" + roundEnv.getRootElements());
        System.out.println("jytx===" + annotations);
        System.out.println("jyt===true");

        if (!roundEnv.getRootElements().isEmpty()) {
            ModuleProxyCreator creator = new ModuleProxyCreator();
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(creator.getProxyClassFullName());
                Writer writer = jfo.openWriter();
                writer.write(creator.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
