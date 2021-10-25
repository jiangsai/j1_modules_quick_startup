package com.playplan.fastmoudleplugin.module;


import com.android.build.gradle.AppExtension;
import com.playplan.fastmoudleplugin.module.conf.FastModuleConf;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * author : jyt
 * time   : 2021/10/18
 * desc   :
 */
public class FastModulePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("FastModulePlugin开始");

        FastModuleConf fastmoduleconf = project.getExtensions().create("fastmoduleconf", FastModuleConf.class);

        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        FastModuleTransform fastModuleTransform = new FastModuleTransform();
        appExtension.registerTransform(fastModuleTransform);

        project.afterEvaluate(project1 -> {
            FastModuleConf fastmoduleconf1 = (FastModuleConf) project1.getExtensions().findByName("fastmoduleconf");
            fastModuleTransform.setFastMoudles(fastmoduleconf1.fastModules);
        });


        System.out.println("FastModulePlugin ==结束");


    }
}
