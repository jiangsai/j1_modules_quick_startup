package com.playplan.fastmoudleplugin.module;


import com.android.build.gradle.AppExtension;

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
        // project.getExtensions().create("pluginsrc", FastModuleTransform.class);
        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(new FastModuleTransform());
    }
}
