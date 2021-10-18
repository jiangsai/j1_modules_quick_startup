package com.playplan.fastmodulesannotationprocessor;

import com.playplan.fastmodules.module.ModuleConstant;

/**
 * author : jyt
 * time   : 2021/10/18
 * desc   :
 */
public class ModuleProxyCreator {
    public CharSequence getProxyClassFullName() {
        return "TestModuleProxyCreator";
    }

    /**
     * 生成java代码
     */
    public String generateJavaCode() {
        StringBuilder sb = new StringBuilder();
        //设置包名
        sb.append("package ").append(ModuleConstant.PROXY_CLASS_PACKAGE_NAME).append(";\n\n");

        //设置import部分
        sb.append("import ").append("java.util.List").append(";\n\n");
        sb.append("import ").append("com.playplan.fastmodules.module.IProxyModule").append(";\n\n");
        sb.append("import ").append("com.playplan.fastmodules.annotation.FastMoudleProxy").append(";\n\n");

        sb.append("@FastMoudleProxy").append("\n");
        sb.append("public class ").append(getProxyClassFullName())
                .append(" implements IProxyModule")
                .append(" {\n\n");
        sb.append("     public void ").append("initData()").append(" {\n\n");


        sb.append("     }");
        sb.append("\n}");
        return sb.toString();
    }
}
